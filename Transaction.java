
import java.io.IOException;
import java.util.*;

/**
 * Sale class extends Register, performs Sale transaction
 */
public class Transaction extends Register {

    private double total;
    private double tax;
    private boolean nextItem;
    private final int endOfCart;
    private final int removeItem;
    private int input;
    private boolean isRental;
    private final int cancelTransaction;
    private static final Double taxPercent = .06;
    private ArrayList<int[]> changes; //list of changes made in the program, used to undo changes
    Cart currentCart = new Cart();

    /**
     * Sale constructor, takes user input to fill cart with items until user
     * enters -999 to indicate no more items, user enters -1 to remove item
     * previously entered
     */
    public Transaction() {
        this.nextItem = true;
        this.endOfCart = -999;
        this.removeItem = -1;
        this.cancelTransaction = -190;
        this.input = 0;  /*stores itemNum of currentCart.items.get(index).getItemNumber*/

    }

    /**
     * makeSale() begins the sale process of reading in items 
     * presented to cashier at checkout
     *
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public void makeTransaction() throws InterruptedException, IOException {
        changes = new ArrayList<int[]>();
        Scanner transaction = new Scanner(System.in);
        while (nextItem) {
            try {
                System.out.print("Enter item ID"); //temp
                System.out.print("[OPTIONS: -999 for end of sale, -1 to remove an item, -190 to cancel transaction]\n-->");
                if (transaction.hasNextInt()) { //check input type
                    input = transaction.nextInt(); //get option or itemNumber
                    if (input == endOfCart) { //input is -999
                        nextItem = false;
                    } else if (input == removeItem) { //input is -1
                        if (currentCart.inventory.isEmpty()) {
                            System.out.println("NO ITEMS TO BE REMOVED");
                            continue;
                        }
                        System.out.print("Enter an item to remove\n-->");
                        if (!transaction.hasNextInt()){throw new InputMismatchException();}
                        currentCart.removeItem(transaction.nextInt()); //read in another value to remove that item
                    } else if (input == cancelTransaction) { //input is -190
                        cancelTransaction(changes);
                        Cashier.cashierDo();
                        break;
                    } else if (input >= SQLInterface.getInstance().getMaxID() || input <= 0){
                        System.out.println("Error, Item not found");
                    } else { //input is none of the options, thus possibly a valid itemNumber to add an item to cart
                        //based on input, return Item from database called item
                        System.out.print("Enter quantity of item to be purchased\n-->"); //prompt user to enter quantity of items to buy
                        if (!transaction.hasNextInt()){throw new InputMismatchException();}
                        int itemQuan = transaction.nextInt();
                        if (itemQuan < 1){throw new InputMismatchException();}
                        //if the item entered is a rental prompt them to start rental process
                        if (SQLInterface.getInstance().isRentable(input)) {
                            Scanner rentScan = new Scanner(System.in);
                            System.out.print("Enter number of days for your rental\n-->");
                            int numRentDays = 0;
                            if (rentScan.hasNextInt()) {
                                numRentDays = rentScan.nextInt();
                                currentCart.setDays(numRentDays);
                                currentCart.getReturnDate();
                            } else {
                                System.out.println("Please try again");
                                continue; //this should go back to prompting them for an item id
                            }
                        }
                        if (itemQuan > SQLInterface.getInstance().getQuantity(input)) { //check inventory
                            System.out.println("Error, not enough inventory for purchase");
                            System.out.println("Please re-enter item id and quantity");
                            continue;
                        }
                        if (itemQuan > 1 && itemQuan <= SQLInterface.getInstance().getQuantity(input)) {
                            Item item = SQLInterface.getInstance().getItem(input);

                            currentCart.addMultItems(item, itemQuan);
                            SQLInterface.getInstance().updateQuantity(input, (itemQuan * -1));
                            int[] changeVals = {input, itemQuan};
                            changes.add(changeVals);
                        } else {
                            Item item = SQLInterface.getInstance().getItem(input);

                            currentCart.add(item);
                            SQLInterface.getInstance().updateQuantity(input, -1);
                            int[] changeVals = {input, itemQuan};
                            changes.add(changeVals);
                        }
                    }
                } else {
                    System.out.println("INVALID INPUT...Try Again");
                    System.out.println();
                    transaction.nextLine();                
                }
            } catch (NumberFormatException| InputMismatchException e) {
                System.out.println("Error reading input, try again");
                System.out.println();
                transaction.nextLine();
            }
            System.out.printf("\nCurrent Cart Subtotal : %.2f", this.currentCart.getSubtotal());
            System.out.print("\n\n");

        }
        tax = getTax(currentCart);

        int pt = getPaymentType();

        if (registerPay(pt)) {
            boolean paid = false;
            while(!paid){
                if (pt == 0) { //payment type is cash, run through getting cash, print receipt etc.
                    Scanner cashIn = new Scanner(System.in);
                    System.out.printf("Cart Total: %.2f\n", (this.currentCart.getSubtotal()*1.06));
                    System.out.print("Enter cash recieved or enter -999 to pay by card\n-->"); //should put this in a loop, make another method?
                    double c = 0.0;
                    if (cashIn.hasNextDouble()) {
                        c = cashIn.nextDouble();
                        if (c == -999){
                             pt = 1;
                             System.out.println("\nPaying By Card...");
                             continue;
                        }
                        else if(c <=0){
                            System.out.println("Please Enter a valid amount...\n");
                            continue;
                        }
                        double change = makeChange(c, currentCart.getSubtotal() + tax);
                        Receipt receipt = new Receipt(currentCart, tax, pt);
                        receipt.store();
                        receipt.print();
                        System.out.printf("Your change is %.2f.\n", change);
                        paid = true;
                        continue;
                    }
                    System.out.println("Please Enter a valid amount...\n");
                    continue;
                } 
                else if (pt == 1) 
                {
                    boolean valid = false;
                    do
                    {
                        valid = false;
                        Scanner creditCardScan = new Scanner(System.in);
                        System.out.print("Enter credit card number or enter -999 to pay with cash\n-->");
                        if (creditCardScan.hasNextLong()) 
                        {
                            long ccN = creditCardScan.nextLong();
                            if (ccN == -999){
                                pt = 0;
                                System.out.println("\nPaying By Cash...");
                                break;
                            }
                            String ccNString = Long.toString(ccN);
                            boolean validate = CreditCheck.getInstance().creditCardCheck(ccNString);
                            if (validate) 
                            { //valid cc
                                System.out.println("Valid Credit Card Entered");
                                valid = true;
                                Receipt receipt = new Receipt(currentCart, tax, pt);
                                receipt.store();
                                receipt.print();
                                paid = true;
                            } 
                            else 
                            {
                                System.out.println("Invalid Credit Card Entered, Try Again."); //not 100% sure where this will end up afterwards, need to check and adjust
                            }
                        }
                        System.out.println("Invalid Credit Card Entered, Try Again.");
                    }while(!valid);
                }
        }
        }
    }

    /**
     * cancelSale(), set all elements of cart items to null and set size to 0,
     * assumes cancel sale means end program for now0
     *
     * @param changes
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public void cancelTransaction(ArrayList<int[]> changes) throws InterruptedException, IOException {

            /*this should set all elements of ArrayList items to null and set size to 0*/
            System.out.println("Transaction was cancelled...CART IS NOW EMPTY!");
            currentCart.inventory.clear();
            currentCart.clearSubTotal();

            int id;
            int quantity;

            for (int[] pair : changes) {

                id = pair[0];
                quantity = pair[1];

                SQLInterface.getInstance().updateQuantity(id, quantity);
            }
    }

    /**
     * make change from transaction
     *
     * @param cash
     * @param total
     * @return
     */
    public double makeChange(double cash, double total) {
        double ret = 0.0;
        Scanner cashIn = new Scanner(System.in);
        if (cash >= total) {
            ret = cash - total;
           
        } else if (cash < total) 
        {
            System.out.printf("Insufficient Funds!\nRemaining Amount Due: $%.2f\n", (total-cash));
            double c = 0.0;
            while (true){
                System.out.print("Enter more money to complete the sale:\n-->");
                if (cashIn.hasNextDouble()) 
                {
                    c = cashIn.nextDouble();
                    if (c > 0.0){
                        break;
                    }
                    else{
                        System.out.println("Please enter a valid sum of money...\n");
                    }
                }
                else{
                    System.out.println("Please enter a valid sum of money...\n");
                    cashIn.nextLine();
                }
            }


            cash = cash + c;
            ret = makeChange(cash, total);

        }
        return ret;
    }
}