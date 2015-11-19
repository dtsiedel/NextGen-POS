//manages storage and retrieval of receipt objects

import java.util.*;
import java.io.*;

@SuppressWarnings({"unchecked", "deprecation"}) //compiler complains that our "inventory" arraylist could contain anything, when we claim it is full of only Items
//however, I feel it is safe to suppress this, as long as we are careful to only ever store items in the inventory



public class ReceiptManager {

    private static ReceiptManager instance = null;

    public static String sqlite = "sqlite3";       //sqlite version string
    public static String db = "data";              //overall database name
    private static String receiptDB = "receipts";   //receipt db name
    private static String itemsDB = "receiptitems"; //itemsdb name

    //IMPORTANT: Keep the proper index in the database synchronized with these variables at all times
    private static int receiptCount;       //the current number of receipts in the db, one more than this is the next number to insert at
    private static int receiptItemCount;   //current number of items listed in receiptItems db, one more than this is next location to store in

    //private for Singleton pattern
    private ReceiptManager() throws InterruptedException, IOException 
    {

        //NOTE: receipts db index 0 has start to represent receiptCount, run to represent receiptItemCount
        //initialize receiptCount based on values stored in Receipt db index 0
        String[] command = {sqlite, db, ("SELECT start FROM " + receiptDB + " WHERE id=0;")};
        this.makeSQLCall(command);  //put receiptCounter in outfile
        String receiptPointer = this.getSQLOutput(); //get receiptcounter as a string, and delete outfile
        ReceiptManager.receiptCount = Integer.parseInt(receiptPointer); //set value from db

        //repeat for receiptItemsCounter
        String[] commands = {sqlite, db, ("SELECT run FROM " + receiptDB + " WHERE id=0;")};
        this.makeSQLCall(commands); //put receiptItemCounter in outfile
        receiptPointer = this.getSQLOutput(); //get value and delete file
        ReceiptManager.receiptItemCount = Integer.parseInt(receiptPointer);
    }

    //handles the low-level operation of making a SQL call (the proper way to handle what I did in SQLInterface, rework for beta)
    //should call getSQLOutput after to get result and clean up file you made
    private void makeSQLCall(String[] command) throws InterruptedException, IOException 
    {
        ProcessBuilder pb = new ProcessBuilder(command);

        File outfile = new File("output.txt"); //write output to a file
        pb.redirectOutput(outfile);
        pb.redirectError(outfile);

        Process run = pb.start(); //execute the command loaded prior
        run.waitFor(); 			  //wait for operation to finish before proceeding

        // System.out.println("\nMaking call: ");
        // for(String s: command)
        // 	System.out.print(s + " ");
    }

    //gets the output from the file 'output.txt' in the same directory as the program
    private String getSQLOutput() throws InterruptedException, IOException 
    {
        File inFile = new File("output.txt");
        Scanner sc = new Scanner(inFile);

        String[] deleteCommand = {"rm", "output.txt"};

        if (inFile.length() == 0) //the output file does not exist, give error message
        {
            System.out.println("There was an error fetching receipt data.");
            this.makeSQLCall(deleteCommand);
            return "~";
        }

        String result = sc.nextLine();   //output should be a singular line in outputfile
        this.makeSQLCall(deleteCommand); //clean up the file you made

        return result;

    }

    //Singleton design pattern, used to initialize unique instance or get it
    //also sets values dependant on persistent data: START corresponds to receiptCount, RUN to receipItemCount
    public static synchronized ReceiptManager getInstance() throws InterruptedException, IOException 
    {

        if (instance == null) {
            instance = new ReceiptManager();
        }

        return instance;
    }

    //stores the receipt in the database 
    public int storeReceipt(Receipt r) throws InterruptedException, IOException 
    {
		//layout of Receipt DB: id, string for date, two ints for item indices
        //id(int)         date(string)        start (int)      run (int)

        //Process:
        //look at length of cart to determine how many elements to fill in ReceiptItem db ('run')
        //look at receiptItemCount to see where to start adding items ('start')
        //look at receiptCount to see where to insert receipt, make new entry at that id with values
        //update receiptCount, and the relevant field in database
        //then, starting at start index, insert items "run" times into second db, going through cart arraylist
        //update the receiptItemCount with each insertion, and relevant field in database
        //use the receipt's setId method to give it the value it has in the db
        Cart cart = r.getCart();
        ArrayList<Item> inventory = cart.getInventory();

        int run = inventory.size();         //number of items in the cart = run of items in 2nd db
        int start = ReceiptManager.receiptItemCount; //starting place of receiptItems in 2nd db

        int index = ReceiptManager.receiptCount;       //location to insert this receipt
        String[] command = {sqlite, db, ("INSERT INTO " + receiptDB + " VALUES(" + index + ",date('now')," + start + "," + run + ");")};

        this.makeSQLCall(command);
        this.incrementReceiptCount(); //make sure indices are correct 

        int end = start + run; //this is one more than the last index to fill

        //the loop to add items to receiptItemDB
        for (int i = start; i < end; i++) {
            int counter = i - start; //a secondary counter, for the index in the inventory arraylist
            Item item = inventory.get(counter); //the item we are inserting

            int rentable = (item.getIsRental()) ? 1 : 0;
            String[] commands = {sqlite, db, ("INSERT INTO " + itemsDB + " VALUES(" + i + ",\"" + item.getName() + "\"," + item.getPrice() + "," + item.getQuantity() + "," + rentable + ");")};

            this.makeSQLCall(commands); //actually add the items

            //increment receiptItem and database count each time
            this.incrementReceiptItemCount();

        }

        //return value is the ID that the receipt is stored in Receipts db
        return index;
    }

    //updates ReceiptManager.receiptCount AND the database (start), this is the only way this should be done
    //in order to ensure they remain synchronized
    private void incrementReceiptCount() throws InterruptedException, IOException 
    {
        ReceiptManager.receiptCount++; //increment this program's counter

        //then increment the database counter (at index 0) by the appropriate amount
        //remember that the start field in the db corresponds to receiptCount
        String[] command = {sqlite, db, ("UPDATE " + receiptDB + " SET start=" + ReceiptManager.receiptCount + " WHERE id=0;")};

        this.makeSQLCall(command); //update counter in db
    }

    //updates ReceiptManager.receiptItemCount AND the database(run), this is the only way this should be done
    //in order to ensure they remain synchronized
    private void incrementReceiptItemCount() throws InterruptedException, IOException 
    {
        ReceiptManager.receiptItemCount++; //increment this program's counter

        //then increment the database counter (at index 0) by the appropriate amount
        //remember that the start field in the db corresponds to receiptCount
        String[] command = {sqlite, db, ("UPDATE " + receiptDB + " SET run=" + ReceiptManager.receiptItemCount + " WHERE id=0;")};

        this.makeSQLCall(command); //update counter in db
    }

    //gets the data associated with the receipt at the given id
    public Date getDate(int receiptID) throws InterruptedException, IOException 
    {
        String[] command = {sqlite, db, ("SELECT date FROM " + receiptDB + " WHERE id=" + receiptID + ";")};
        String dateString = this.getSQLOutput();

        Date date = new Date(Date.parse(dateString));

        System.out.println(date);

        return date;
    }

    //gets a receipt in the database based on ID
    public Receipt getReceipt(int receiptID) throws InterruptedException, IOException 
    {
		Receipt r;
        //id(int)         name(string)        price(real)      quantity(int)      rental(0-1int, aka makeshift bool)

        //get the two ints from receipt db at given id
        //make a new Cart
        //starting at start, and continuing for "run" iterations:
        //grab relevant info from index, and make an item of it
        //add each item to the cart
        //from the cart and the ID (which we already have), construct a receipt, and return it
        
        if(receiptID < ReceiptManager.receiptCount)
        {
            Cart cart = new Cart();

            String[] commands1 = {sqlite, db, ("SELECT start FROM " + receiptDB + " WHERE id=" + receiptID + ";")};
            this.makeSQLCall(commands1);
            String result = this.getSQLOutput();
            int start = Integer.parseInt(result);

            String[] commands2 = {sqlite, db, ("SELECT run FROM " + receiptDB + " WHERE id=" + receiptID + ";")};
            this.makeSQLCall(commands2);
            result = this.getSQLOutput();
            int run = Integer.parseInt(result);

            int end = start + run; //one more than the last item belonging to this receipt

            for (int i = start; i < end; i++) {
                String[] nameCommands = {sqlite, db, ("SELECT name from " + itemsDB + " WHERE id=" + i + ";")};
                this.makeSQLCall(nameCommands);
                String name = this.getSQLOutput();

                String[] rentCommands = {sqlite, db, ("SELECT rental from " + itemsDB + " WHERE id=" + i + ";")};
                this.makeSQLCall(rentCommands);
                String rentalString = this.getSQLOutput();
                Boolean rentable = (Integer.parseInt(rentalString) == 1) ? true : false; //get rentable bool from int

                String[] priceCommands = {sqlite, db, ("SELECT price from " + itemsDB + " WHERE id=" + i + ";")};
                this.makeSQLCall(priceCommands);
                String priceString = this.getSQLOutput();
                Double price = Double.parseDouble(priceString); //get price double from price string

                String[] idCommands = {sqlite, db, ("SELECT id from products WHERE name=\"" + name + "\";")};
                this.makeSQLCall(idCommands);
                String idString = this.getSQLOutput();
                int id;
                try {
                    id = Integer.parseInt(idString);
                } catch (NumberFormatException e) {
                    System.out.println("Could not find item " + name + " in products database.");
                    id = 9999999;
                }

                String[] quantityCommands = {sqlite, db, ("SELECT quantity FROM " + itemsDB + " WHERE id=" + i + ";")};
                this.makeSQLCall(quantityCommands);
                String quantString = this.getSQLOutput();
                int quantity;
                try {
                    quantity = Integer.parseInt(quantString);
                } catch (NumberFormatException e) {
                    System.out.println("Could not find item " + name + " in products database.");
                    quantity = 9999999;
                }

                Item item = new Item(rentable, price, id, name, quantity);

                // System.out.println("\n\n\nGot name: " + item.getName());
                // System.out.println("Got rentable: " + item.getIsRental());
                // System.out.println("Got price: " + item.getPrice());
                // System.out.println("Got id: " + item.getItemNumber());
                // System.out.println("Got quantity: " + item.getQuantity());
                cart.add(item);

            }

            r = new Receipt(cart, .06, 0, receiptID);
        }
        else
        {
            return null;
        }

        return r;
    }

    //deletes all receipts from database, and all items from receiptitems
    //also sets the start and run values in 0 from the receipts db
    //it goes without saying to be careful using this, and not to use in production
    private void purgeDatabases() throws InterruptedException, IOException 
    {
        String[] deleteReceipts = {sqlite, db, ("DELETE FROM " + receiptDB + " WHERE id!=0;")};
        this.makeSQLCall(deleteReceipts);

        String[] deleteItems = {sqlite, db, ("DELETE FROM " + itemsDB + ";")};
        this.makeSQLCall(deleteItems);

        //change values of start and run to reflect deleting all items
        String[] changeStart = {sqlite, db, ("UPDATE " + receiptDB + " SET start = 1 WHERE id=0;")};
        String[] changeRun = {sqlite, db, ("UPDATE " + receiptDB + " SET run = 1 WHERE id=0;")};
        this.makeSQLCall(changeStart);
        this.makeSQLCall(changeRun);

        //set these back to what they should be
        ReceiptManager.receiptCount = 1;
        ReceiptManager.receiptItemCount = 1;
    }

    // //test stub
    // public static void main(String[] args) throws InterruptedException, IOException {
    //     ReceiptManager manager = ReceiptManager.getInstance();

    //     manager.purgeDatabases(); //primarily for testing use

    //     System.out.println("ReceiptCount: " + ReceiptManager.receiptCount);
    //     System.out.println("receiptItemCount: " + ReceiptManager.receiptItemCount);

    //     Cart c = new Cart();
    //     c.add(new Item(false, 1.00, 0, "lettuce", 10));
    //     c.add(new Item(false, 1.00, 1, "burgers", 10));
    //     c.add(new Item(true, 1.00, 2, "cheese", 10));

    //     Receipt r = new Receipt(c, 0.0, 0);
    //     r.store();

    //     System.out.println("New receiptCount: " + ReceiptManager.receiptCount);
    //     System.out.println("New receiptItemCount: " + ReceiptManager.receiptItemCount);

    //     Receipt receipt = manager.getReceipt(1);

    //     receipt.print();

    // }
}
