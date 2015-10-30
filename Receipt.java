
import java.text.DecimalFormat;

/**
 * Receipt class, prints subTotal, tax, and total
 */
public class Receipt {

    private Cart cart;
    private double tax;
    private int paymentMethod;  //0: Cash - just accept cash for the alpha
    private int id;             //tracks the receipt's ID. ID is assigned when receipt is added to db

    /**
     * Receipt constructor
     *
     * @param c
     * @param t
     * @param pm
     */
    public Receipt(Cart c, double t, int pm) {
        this.cart = c;
        this.tax = t;
        this.paymentMethod = pm;
    }

    /**
     * print(), prints subtotal, tax and total
     */
    public void print() {
        //IMPORTANT: receipt must be stored before it is printed, in order to get its id
        DecimalFormat df = new DecimalFormat("0.00");
        //System.out.println("Item (Quantity)" + "\t\t" + "Price\n"); //fix later
        for (Item item : cart.items) { //for each item in cart
            //System.out.println(item.getName() + "\t(" + item.getQuantity() + ")" + "\t\t$" + df.format(item.getPrice())); //fix later
            System.out.println(item.getName() + "\t\t$" + df.format(item.getPrice()));
        }
        System.out.println("\n\tOrder Subtotal:\t$" + df.format(cart.getSubtotal()));
        System.out.println("\tTotal Tax:\t$" + df.format(tax));
        System.out.println("\nOrder Total:\t$" + df.format(cart.getSubtotal() + tax));
        //System.out.println("Receipt ID: " + this.id"); //this line is why you need to store() before you print(),
        //or store() a single time at the beginning of the print method
    }

    /**
     * store(), stores receipt in database implemented later
     */
    public void store() {
        //Code To Store Receipt in receipt object database
        //receipts will be stored by their id number, which will be assigned at creation by the database
        //int receiptId = ReceiptManager.getInstance.storeReceipt(this);
        //this.id = receiptId;
    }
}
