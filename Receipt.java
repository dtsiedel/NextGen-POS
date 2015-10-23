
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Receipt class, prints subTotal, tax, and total
 */
public class Receipt {

    private Cart cart;
    private double tax;
    private int paymentMethod;  //0: Cash, 1: Credit Card, 2:Check
    private Date receiptDate;

    /**
     * Receipt constructor
     *
     * @param c
     * @param t
     * @param pm
     */
    public Receipt(Cart c, double t, int pm, Date d) {
        this.cart = c;
        this.tax = t;
        this.paymentMethod = pm;
        this.receiptDate = d;
    }

    /**
     * get the cart
     *
     * @return cart
     */
    public Cart getCart() {
        return this.cart;
    }

    /**
     * get tax
     *
     * @return tax
     */
    public double getTax() {
        return this.tax;
    }

    /**
     * get payment method
     *
     * @return payment method
     */
    public int getPaymentMethod() {
        return this.paymentMethod;
    }

    /**
     * get date on this receipt
     *
     * @return receipt date
     */
    public Date getReceiptDate() {
        return this.receiptDate;
    }

    /**
     * print(), prints subtotal, tax and total
     */
    public void print() {
        DecimalFormat df = new DecimalFormat("0.00");
        //System.out.println("Item (Quantity)" + "\t\t" + "Price\n"); //fix later
        System.out.println("Date of Receipt: " + this.receiptDate);
        for (Item item : cart.items) { //for each item in cart
            //System.out.println(item.getName() + "\t(" + item.getQuantity() + ")" + "\t\t$" + df.format(item.getPrice())); //fix later
            System.out.println(item.getName() + "\t\t$" + df.format(item.getPrice()));
        }
        System.out.println("\n\tOrder Subtotal:\t$" + df.format(cart.getSubtotal()));
        System.out.println("\tTotal Tax:\t$" + df.format(tax));
        System.out.println("\nOrder Total:\t$" + df.format(cart.getSubtotal() + tax));
    }

    /**
     * store(), stores receipt in database implemented later
     */
    public void store() {
        //Code To Store Receipt in receipt object database
    }
}
