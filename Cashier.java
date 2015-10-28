
/**
 * cashier to do sale, rental, return
 */
import java.util.Scanner;

public class Cashier {

    private int id;
    private int pw;

    public Cashier() {
    }

    public Cashier(int i, int p) {
        this.id = i;
        this.pw = p; //this seems kind of silly should make this better??
    }

    /**
     * runs cashier routine, until user terminates
     */
    public static void cashierDo() {
        Scanner cashierScan = new Scanner(System.in);
        boolean done = false;
        do {
            System.out.print("Please select an option\n-->");
            System.out.print("[OPTIONS- 0:Process Sale, 1:Process Rental, 2: Process Return, -1:Logout, -2:Exit]");
            switch (cashierScan.nextInt()) {
                case 0:
                    Transaction trans = new Transaction();
                    trans.makeTransaction();
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
                    //logout
                    System.out.println("Logging Out!");
                    done = true;
                    break;
                case -2:
                    //CALL run() from java.Runtime.shutdownhook()...make method above
                    break;
                default:
                    //PUT STUFF HERE!!!
                    break;
            }
        } while (!done);
    }
}
