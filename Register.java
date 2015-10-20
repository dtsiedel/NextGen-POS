
import java.util.Scanner;

/**
 * Register class, calculates totalTax, and getPaymentType
 */
public class Register {

    int paymentType;
    //State currentState;
    Scanner readPaymentType = new Scanner(System.in);

    /**
     * getTax() calculates totalTax of items in cart
     *
     * @param cart
     * @return
     */
    public double getTax(Cart cart) {
        double totalTax = 0.0;
        for (Item item : cart.items) {
            //totalTax += item.getPrice()*stateTax(currentState, item.getType());
            totalTax += item.getPrice() * .06; //temp until TaxCalculator is implemented
        }
        return totalTax;
    }

    /**
     * getPaymentType reads in user input to select payment type
     *
     * @return paymentType
     */
    public int getPaymentType() {
        System.out.print("Enter payment method-");
        System.out.print("[OPTIONS: 0 for Cash, 1 for Credit \n-->"); //credit  not implemented yet
        paymentType = readPaymentType.nextInt();
        return paymentType;
    }

    /**
     * registerPay() checks for payment type information to be passed
     *
     * @param paymentType
     * @return boolean
     */
    public boolean registerPay(int paymentType) {
        return paymentType != -1;
    }

    /**
     * removeFromInventory(), remove specified item via itemNumber
     *
     * @param itemNumber
     */
    public void removeFromInventory(int itemNumber) {
        //Call the Inventory and decrement the stock counter by 1
    }

    /**
     * removeFromInventory(), removes quantity of specified item via itemNumber
     *
     * @param itemNumber
     * @param quantity
     */
    public void removeFromInventory(int itemNumber, int quantity) {
        //Call the Inventory and remove the denoted quantity from the stock counter
    }

    /**
     * main for Demo purposes only
     *
     * @param args
     */
    public static void main(String args[]) {
        System.out.println("Welcome to The Pandas' POS Demo");
        Register reg = new Register();
        Sale sale = new Sale();
        sale.makeSale();
    }
}
