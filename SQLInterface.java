
import java.io.*;
import java.util.*;

//TODO: make a function to perform a SQL operation and return the result (given a string of commands) to improve cohesion
public class SQLInterface {

    private static SQLInterface instance; //for implementation of singleton

    private final static String sqlite = "sqlite3"; //the version of sqlite in use
    private final static String dbName = "data";    //the name of the database being used

    //private constructor prevents outside clases from accessing
    private SQLInterface() {
        //intentionally blank
    }

    //returns the instance if it exists, or makes exactly one if 
    //one does not yet exist
    public synchronized static SQLInterface getInstance() {
        if (instance == null) {
            instance = new SQLInterface();
        }

        return instance;
    }

    //given a username, returns TRUE if the username is available to be added
    //that is, if it is NOT in the database
    public boolean isAvailable(String username) throws InterruptedException, IOException
    {
        String[] command = {sqlite, dbName, ("SELECT name FROM users WHERE name=\"" + username + "\";")};
        ProcessBuilder pb = new ProcessBuilder(command);

        File outfile = new File("output.txt"); //write output to a file
        pb.redirectOutput(outfile);
        pb.redirectError(outfile);
        Process get = pb.start(); //execute the command loaded prior
        get.waitFor(); //wait for operation to finish

        if(outfile.length() == 0) //there was no user with that name
        {
            return true;
        }

        String[] deleteCommand = {"rm", "output.txt"};
        ProcessBuilder deleter = new ProcessBuilder(deleteCommand); //to get rid of the text file you made
        Process delete = deleter.start();
        delete.waitFor(); //clean up

        return false; //there was an output of sorts, the name is not available
    }

    //given a username, returns the password associated with it (unencrypted, yay)
    //if the name is not in the db, prints an error string and returns a tilde (indicates an error)
    //recommended usage: scan in username and password from user,
    //					 then compare their input to result of getPassword(username)
    public String getPassword(String username) throws InterruptedException, IOException {
        String[] command = {sqlite, dbName, "SELECT password FROM users WHERE name="};
        command[2] = command[2] + "\"" + username + "\";";

        ProcessBuilder pb = new ProcessBuilder(command);

        File outfile = new File("output.txt"); //write output to a file
        pb.redirectOutput(outfile);
        pb.redirectError(outfile);
        Process get = pb.start(); //execute the command loaded prior
        get.waitFor(); //wait for operation to finish

        String[] deleteCommand = {"rm", "output.txt"};
        ProcessBuilder deleter = new ProcessBuilder(deleteCommand); //to get rid of the text file you made

        File f = new File("output.txt"); //read file output (can't redirect output with '>' in this context)
        Scanner sc = new Scanner(f);

        if (f.length() == 0) //if there are no lines in the output, item not found
        {
            System.out.println("User not found, ensure you did not make a typo and try again.");
            deleter.start(); ///delete output.txt
            return "~";
        }

        String result = sc.nextLine();

        Process delete = deleter.start(); ///delete output.txt
        delete.waitFor(); //wait for delete process to finish before proceeding

        return result;

    }

    //deletes a user by their username
    //it is up to the caller to ensure that the one executing this is an admin
    public void deleteUser(String username) throws InterruptedException, IOException {
        String[] command = {sqlite, dbName, "DELETE FROM users WHERE name=\""};
        command[2] = command[2] + username + "\";";

        ProcessBuilder pb = new ProcessBuilder(command);
        Process add = pb.start();
        add.waitFor(); //wait for add to finish before proceeding
    }

    //adds a user to the database, given the username and password, as well as whether they are a manager. 
    public void addUser(String username, String password, Boolean manager) throws InterruptedException, IOException {
        int man = (manager) ? 1 : 0;

        String[] command = {sqlite, dbName, "INSERT INTO users VALUES(\""};
        command[2] = command[2] + username + "\",\"" + password + "\", " + Integer.toString(man) + ");";

        ProcessBuilder pb = new ProcessBuilder(command);
        Process add = pb.start();

        add.waitFor(); //wait for add to finish before proceeding

    }

    //returns whether the given username corresponds to a manager (else is cashier)
    public Boolean isManager(String user) throws InterruptedException, IOException {
        String[] command = {sqlite, dbName, "SELECT manager FROM users WHERE name =\""};
        command[2] = command[2] + user + "\";";

        ProcessBuilder pb = new ProcessBuilder(command);
        File outfile = new File("output.txt"); //write output to a file
        pb.redirectOutput(outfile);
        pb.redirectError(outfile);
        Process get = pb.start();

        get.waitFor();

        String[] deleteCommand = {"rm", "output.txt"};
        ProcessBuilder deleter = new ProcessBuilder(deleteCommand); //to get rid of the text file you made

        File f = new File("output.txt"); //read file output (can't redirect output with '>' in this context)
        Scanner sc = new Scanner(f);

        if (f.length() == 0) //if there are no lines in the output, item not found
        {
            System.out.println("User not found, ensure you did not make a typo and try again.");
            deleter.start(); ///delete output.txt
            return false;
        }

        String mng = sc.nextLine();

        Process delete = deleter.start(); ///delete output.txt
        delete.waitFor(); //wait for delete process to finish before proceeding

        Boolean isMng = (mng.equals("1")); //more ternary op

        return isMng;
    }

    //returns whether the given int corresponds to a rentable item or not
    public Boolean isRentable(int id) throws InterruptedException, IOException {
        String detailString = this.getInfo(id);

        String adjustedString = detailString.replace("|", "~");

        String[] vals = adjustedString.split("~");

        int rent = Integer.parseInt(vals[4]); //vals[4] contains the "rentable" field

        Boolean rentable = (rent == 1) ? true : false; //more ternary op

        return rentable;
    }

    //returns an Item object associated with the given ID
    public Item getItem(int id) throws InterruptedException, IOException {
        Double price = this.getPrice(id);
        String name = this.getProductName(id);
        Boolean rentable = this.isRentable(id);
        int quantity = this.getQuantity(id); //item does not yet take this field

        return new Item(rentable, price, id, name, quantity);

    }

    //given an id number, prints to stdout relevant information
    public String getInfo(int id) throws InterruptedException, IOException {

        //call sqlite command to access database
        String[] command = {sqlite, dbName, "SELECT * FROM products WHERE id = "};
        command[2] = command[2] + Integer.toString(id) + ";";
        ProcessBuilder pb = new ProcessBuilder(command);

        File outfile = new File("output.txt"); //write output to a file
        pb.redirectOutput(outfile);
        pb.redirectError(outfile);
        Process select = pb.start(); //execute the command loaded prior
        select.waitFor(); //not entirely sure how this works but is apparently necessary

        //prepare to remove the text file you created
        String[] deleteCommand = {"rm", "output.txt"};
        ProcessBuilder deleter = new ProcessBuilder(deleteCommand);

        File f = new File("output.txt"); //read file output (can't redirect with '>' in this context)
        Scanner sc = new Scanner(f);

        if (f.length() == 0) //if there are no lines in the output, item not found
        {
            System.out.println("Item not found, ensure you entered the ID properly and try again.");
            deleter.start(); ///delete output.txt
            System.exit(0);
        }

        String result = sc.nextLine();

        Process delete = deleter.start(); ///delete output.txt
        delete.waitFor(); //wait for delete process to finish before proceeding

        return result;

    }

    //returns just the price based on id
    public Double getPrice(int id) throws InterruptedException, IOException {
        String detailString = this.getInfo(id);

        //weird workaround because Java's split method doesn't work on "|" character
        String adjustedString = detailString.replace("|", "~");

        String[] vals = adjustedString.split("~");

        return Double.parseDouble(vals[2]);

    }

    //returns the name of the product based on id
    public String getProductName(int id) throws InterruptedException, IOException {
        String detailString = this.getInfo(id);

        String adjustedString = detailString.replace("|", "~");

        String[] vals = adjustedString.split("~");

        return vals[1];
    }

    //returns the current quantity of the item in stock
    public int getQuantity(int id) throws InterruptedException, IOException {
        String detailString = this.getInfo(id);

        String adjustedString = detailString.replace("|", "~");

        String[] vals = adjustedString.split("~");

        return Integer.parseInt(vals[3]);

    }

    //updates the quantity of an item. First int is the id, the second is the 
    //amount to increment. to decrement, provide a negative number (most common use)
    //returns the updated value
    public int updateQuantity(int id, int quantityChange) throws InterruptedException, IOException {
        int currentQuant = this.getQuantity(id);
        int newQuant = currentQuant + quantityChange;

        String base = "UPDATE products SET quantity=";
        String command = base + Integer.toString(newQuant) + " WHERE id=" + Integer.toString(id);
        String[] update = {sqlite, dbName, command};

        //System.out.println(command);
        ProcessBuilder pb = new ProcessBuilder(update);

        Process updater = pb.start();
        updater.waitFor();

        return newQuant;
    }

    //allows for the insertion of a new item into the database
    //primarily used as a utility function by the database initialization function
    //it is (currently) the responsibility of the client function to ensure that an id is not occupied before executing this command
    public void addProduct(int id, String name, Double price, int quantity, Boolean rentable) throws InterruptedException, IOException {
        int rent = (rentable) ? 1 : 0; //that ternary operator (1 if rentable, 0 if not)

        String base = "INSERT INTO products VALUES("; //base command to insert
        //then add appropriate parameters
        String insertCommand = base + Integer.toString(id) + ",\"" + name + "\"," + Double.toString(price) + "," + quantity + "," + rent + ");";

        String[] commands = {sqlite, dbName, insertCommand};

        ProcessBuilder pb = new ProcessBuilder(commands);
        Process p = pb.start();
        p.waitFor();

    }

   public int getMaxID() throws InterruptedException, IOException, FileNotFoundException{
        int maxID;
        String[] idCommand = {sqlite, dbName, "SELECT max(rowid) FROM products;"};

        ProcessBuilder pb = new ProcessBuilder(idCommand);

        File outfile = new File("output.txt");
        pb.redirectOutput(outfile);
        pb.redirectError(outfile);

        Process p = pb.start();
        p.waitFor();


        Scanner sc = new Scanner(outfile);
        String result = sc.nextLine();

        maxID = Integer.parseInt(result);

        return maxID;


    }

   }