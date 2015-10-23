
import java.util.Scanner;
import java.util.Date;

/**
 * Register class, calculates totalTax, and getPaymentType
 */
public class Register {

    int paymentType;

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
        Scanner readPaymentMethod = new Scanner(System.in);
        System.out.print("Enter payment method-");
        System.out.print("[OPTIONS: 0 for Cash, 1 for Credit \n-->"); //credit  not implemented yet
        paymentType = readPaymentMethod.nextInt();
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
        //log in here
        Login l = Login.getInstance();
        l.startLogin(); //Success?
        Register reg = new Register();
        System.out.print("Please select an option\n-->");
        System.out.print("[OPTIONS- 0:Process Sale, 1:Process Rental, 2: Process Return, -1:Exit]");
        Scanner opt = new Scanner(System.in);
        switch (opt.nextInt()) {
            case 0:
                Sale sale = new Sale();
                sale.makeSale();
                break;
            case 1:
                //Rental rent = new Rental();
                //rental.makeRental();
                break;
            case 2:
                //Return return = new Return();
                //return.makeReturn();
                break;
            case -1:
                //CALL run() from java.Runtime.shutdownhook()...make method above
                break;
            default:
                //PUT STUFF HERE!!!
                break;
        }
    }
}
