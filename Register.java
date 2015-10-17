
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
    double getTax(Cart cart) {
        double totalTax = 0.0;
        for (Item item : cart.items) {
            //totalTax += item.getPrice()*stateTax(currentState, item.getType());
            totalTax += item.getPrice() * .06; //temp until TaxCalculator is implemented
        }
        return totalTax;
    }

    /**
     * getPaymentType reads in user input to select payment type
     */
    int getPaymentType() {
        paymentType = readPaymentType.nextInt();
        return paymentType;
    }

    /**
     * registerPay() checks for payment type information to be passed
     *
     * @param paymentType
     * @return boolean
     */
    boolean registerPay(int paymentType) {
        return paymentType != -1;
    }

    /**
     * removeFromInventory(), remove specified item via itemNumber
     *
     * @param itemNumber
     */
    void removeFromInventory(int itemNumber) {
        //Call the Inventory and decrement the stock counter by 1
    }

    /**
     * removeFromInventory(), removes quantity of specified item via itemNumber
     *
     * @param itemNumber
     * @param quantity
     */
    void removeFromInventory(int itemNumber, int quantity) {
        //Call the Inventory and remove the denoted quantity from the stock counter
    }
}
