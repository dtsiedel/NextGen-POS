
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
    //format of each sublist (index, ∆change)

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
     * makeSale() begins the sale process of reading in items supposedly
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
                        currentCart.removeItem(transaction.nextInt()); //read in another value to remove that item
                    } else if (input == cancelTransaction) { //input is -190
                        cancelTransaction(changes);
                        Cashier.cashierDo();
                        break;
                    } else { //input is none of the options, thus possibly a valid itemNumber to add an item to cart
                        //based on input, return Item from database called item
                        System.out.println("Enter quantity of item to be purchased"); //prompt user to enter quantity of items to buy
                        int itemQuan = transaction.nextInt();
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
                }
            } catch (NumberFormatException e) {
                System.out.println("Error reading input, try again");
            }
        }
        tax = getTax(currentCart);

        int pt = getPaymentType();

        if (registerPay(pt)) {
            Receipt receipt = new Receipt(currentCart, tax, pt);
            receipt.store();
            receipt.print();
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
        {

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
            //System.exit(0);
        }
    }
}
