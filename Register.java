
import java.io.IOException;
import java.util.*;

/**
 * Register class, calculates totalTax, and getPaymentType
 */
public class Register {

    public enum State {

        ALABAMA, ALASKA, ARIZONA, ARKASAS, CALIFORNIA, COLORODO, CONNECTICUT, DELEWARE, FLORIDA, GEORGIA, HAWAII, IDAHO,
        ILLINOIS, INDIANA, IOWA, KANSAS, KENTUCKY, LOUISIANA, MAINE, MARYLAND, MASSACHUSETTS, MICHIGAN, MINNESOTA, MISSISSIPPI, MISSOURI, MONTANA,
        NEBRASKA, NEVADA, NEW_HAMPSHIRE, NEW_JERSEY, NEW_MEXICO, NEW_YORK, NORTH_CAROLINA, NORTH_DAKOTA, OHIO, OKLAHOMA, OREGON, PENNSYLVANIA,
        RHODE_ISLAND, SOUTH_CAROLINA, SOUTH_DAKOTA, TENNESSEE, TEXAS, UTAH, VERMONT, VIRGNIA, WASHINGTON, WEST_VIRGINIA, WISCONSIN, WYOMING
    }

    private int paymentType;
    State currentState;
    //TaxCalculator taxCalc = new TaxCalculator();
    //SQLInterface invenDB = SQLInterface.getInstance(); this or just keep using SQLInterface.getInstance().methods??
    Scanner readPaymentType = new Scanner(System.in);

    /**
     * getTax() calculates totalTax of items in cart
     *
     * @param cart
     * @return
     */
    public double getTax(Cart cart) {
        double totalTax = 0.0;

        totalTax = cart.getSubtotal() * .06; //tax percentage in PA

        return totalTax;
    }

    /**
     * getPaymentType reads in user input to select payment type
     *
     * @return paymentType
     */
    public int getPaymentType() {
        boolean valid = false;

        
        System.out.print("Enter payment method-");
        System.out.print("[OPTIONS: 0 for Cash, 1 for Credit]\n-->"); 

        while(!valid)
        {
            try
            {
                this.paymentType = readPaymentType.nextInt();
                valid = true;
            }catch(InputMismatchException e)
            {
                valid = false;
                System.out.println("Invalid Input! Try Again\n-->");
            }
        }

        return this.paymentType;
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
        //SQLInterface.getInstance().removeItem(itemNumber);
    }

    /**
     * removeFromInventory(), removes quantity of specified item via itemNumber
     *
     * @param id
     * @param quantity
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public void removeFromInventory(int id, int quantity) throws InterruptedException, IOException {
        SQLInterface.getInstance().updateQuantity(id, (quantity * -1));
    }

    /**
     * setState() sets the state for tax purposes
     *
     * @param cState
     */
    public void setState(State cState) {
        this.currentState = cState;
    }

    /**
     * main for Demo purposes only
     *
     * @param args
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws InterruptedException, IOException {
        Login l = Login.getInstance();
        Register reg = new Register();
        boolean done = false;
        reg.setState(State.PENNSYLVANIA);
        do {
            System.out.println("Welcome to The Pandas' Next Gen POS!");
            System.out.println("************************************");
            System.out.println("************ Main Menu *************");
            System.out.println("***********************************");
            int rout = l.startLogin(); //returns success code -1 for cashier, 0 for manager
            if (rout == -1) {
                Cashier.cashierDo();
            } else if (rout == -0) {
                Manager.managerDo();
            }
        } while (!done);
    }
}
