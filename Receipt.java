
import java.text.DecimalFormat;

/**
 * Receipt class, prints subTotal, tax, and total
 */
public class Receipt {

    private Cart cart;
    private double tax;
    private int paymentMethod;  //0: Cash, 1: Credit Card, 2:Check

    /**
     * Receipt constructor
     *
     * @param c
     * @param t
     * @param pm
     */
    public Receipt(Cart c, double t, int pm) {
        cart = c;
        tax = t;
        paymentMethod = pm;
    }

    /**
     * print(), prints subtotal, tax and total
     */
    public void print() {
        DecimalFormat df = new DecimalFormat("0.00");
        for (Item item : cart.items) {
            System.out.println(item.getName() + "\t\t$" + df.format(item.getPrice()));
        }
        System.out.println("\tOrder Subtotal:\t$" + df.format(cart.getSubtotal()));
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
