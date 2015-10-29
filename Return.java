
import java.io.IOException;
import java.util.Scanner;

/**
 * a return will accept all dates regardless refund the customer, add item back
 * to inventory, Done
 *
 */
public class Return {

    private boolean done = false;

    public Return() {
    }

    /**
     * perform return
     *
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public void makeReturn() throws InterruptedException, IOException {
        Scanner retScan = new Scanner(System.in);
        do {
            System.out.print("Enter item ID to be returned");
            System.out.print("[OPTIONS: -111 to end Return]\n-->");
            int retID = retScan.nextInt();
            if (retID == SQLInterface.getInstance().getItem(retID).getItemNumber()) { //check if the entered ID matches the supposed item's ID in the DB
                System.out.print("Enter quantity of item to be returned\n-->");
                int retQ = retScan.nextInt();
                double amt = retQ * SQLInterface.getInstance().getItem(retID).getPrice();
                System.out.println("Your refunded amount is: " + amt);
                retQ += SQLInterface.getInstance().getItem(retID).getQuantity(); //get the current value and add it to the number of items being returned
                SQLInterface.getInstance().updateQuantity(retID, retQ); //update DB
            } else if (retID == -111) {
                done = true;
            }
        } while (!done);
    }
}
