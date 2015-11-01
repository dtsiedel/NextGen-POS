
import java.text.DecimalFormat;
import java.io.*;

/**
 * Receipt class, prints subTotal, tax, and total
 */
public class Receipt {

    private Cart cart;
    private double tax;
    private int paymentMethod;  //0: Cash - just accept cash for the alpha
    private int id = -999;             //tracks the receipt's ID. ID is assigned when receipt is added to db

    /**
     * Receipt constructor if you know the id you want
     *
     * @param c
     * @param t
     * @param pm
     * @param id
     */
    public Receipt(Cart c, double t, int pm, int id) {
        this.cart = c;
        this.tax = t;
        this.paymentMethod = pm;
        this.id = id;
    }

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
        this.id = id; //oops?
    }

    //set method
    public void setId(int id) {
        this.id = id;
    }

    /**
     * print(), prints subtotal, tax and total
     *
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public void print() throws InterruptedException, IOException {
        //IMPORTANT: receipt must be stored before it is printed, in order to get its id
        DecimalFormat df = new DecimalFormat("0.00");
        //System.out.println("Item (Quantity)" + "\t\t" + "Price\n"); //fix later
        for (Item item : cart.items) { //for each item in cart
            //System.out.println(item.getName() + "\t(" + item.getQuantity() + ")" + "\t\t$" + df.format(item.getPrice())); //fix later
            System.out.println(item.getName() + "\t\t$" + df.format(item.getPrice()));
        }
        System.out.println("\n\tOrder Subtotal:\t$" + df.format(cart.getSubtotal()));
        double totalTax = cart.getSubtotal() * tax;
        System.out.println("\tTotal Tax:\t$" + df.format(totalTax));
        System.out.println("\nOrder Total:\t$" + df.format(cart.getSubtotal() + totalTax));
        System.out.println("Receipt Number: " + this.id);  //this line is why you need to store() before you print()
    }

    //get method for cart
    public Cart getCart() {
        return this.cart;
    }

    /**
     * store(), stores receipt in database implemented later
     *
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public void store() throws InterruptedException, IOException {
        //Code To Store Receipt in receipt object database
        //receipts will be stored by their id number, which will be assigned at creation by the database
        //note: do not store receipts multiple times, it will create unneccesary databse entries
        int receiptId = ReceiptManager.getInstance().storeReceipt(this);
        this.id = receiptId;
    }
}
