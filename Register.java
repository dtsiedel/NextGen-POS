
import java.util.Scanner;

/**
 * Register class, calculates totalTax, and getPaymentType
 */
public class Register {
<<<<<<< HEAD

    public enum State {

=======
    
    public enum State {
>>>>>>> refs/remotes/origin/master
        ALABAMA, ALASKA, ARIZONA, ARKASAS, CALIFORNIA, COLORODO, CONNECTICUT, DELEWARE, FLORIDA, GEORGIA, HAWAII, IDAHO,
        ILLINOIS, INDIANA, IOWA, KANSAS, KENTUCKY, LOUISIANA, MAINE, MARYLAND, MASSACHUSETTS, MICHIGAN, MINNESOTA, MISSISSIPPI, MISSOURI, MONTANA,
        NEBRASKA, NEVADA, NEW_HAMPSHIRE, NEW_JERSEY, NEW_MEXICO, NEW_YORK, NORTH_CAROLINA, NORTH_DAKOTA, OHIO, OKLAHOMA, OREGON, PENNSYLVANIA,
        RHODE_ISLAND, SOUTH_CAROLINA, SOUTH_DAKOTA, TENNESSEE, TEXAS, UTAH, VERMONT, VIRGNIA, WASHINGTON, WEST_VIRGINIA, WISCONSIN, WYOMING
<<<<<<< HEAD
    }

=======
    }  
  
>>>>>>> refs/remotes/origin/master
    int paymentType;
    State currentState;
    //TaxCalculator taxCalc = new TaxCalculator();
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
            //totalTax += item.getPrice() * taxCalc.getTax();
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
     * setState() sets the state for tax purposes
     * 
     * @param cState
     */
  public void setState(State cState){
       this.currentState = cState;
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
     */
    public static void main(String args[]) {
        System.out.println("Welcome to The Pandas' POS Demo");
        Login l = Login.getInstance();
        Register reg = new Register();
<<<<<<< HEAD
        boolean done = false;
        reg.setState(State.PENNSYLVANIA);
        do {
            System.out.println("Welcome to The Panda's Next Gen POS!");
            System.out.println("************************************");
            System.out.println("************ Main Menu *************");
            System.out.println("***********************************");
            int rout = l.startLogin(); //Success?
            if (rout == -1) {
                Cashier.cashierDo();
            } else if (rout == -0) {
                Manager.managerDo();
            }
            //some while loop here to keep running cashier/manager routines until done
        } while (!done);
=======
        reg.setState(State.PENNSYLVANIA);
        Sale sale = new Sale();
        sale.makeSale();
>>>>>>> refs/remotes/origin/master
    }
}
