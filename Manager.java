
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
/**
 * a manager with admin privileges
 */
import java.util.Scanner;

public class Manager {

    private String uName;
    private int id;
    private int pw;

    public Manager() {
    }

    public Manager(String un, int i, int p) {
        this.uName = un;
        this.id = i;
        this.pw = p;
    }

    /**
     *
     * @return username
     */
    public String getUserName() {
        return this.uName;
    }

    /**
     * runs a manager routine until -1 to logout or -2 to shutdown
     *
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public static void managerDo() throws InterruptedException, IOException {
        Scanner managerScan = new Scanner(System.in);
        boolean done = false;
        do {
            System.out.print("Please select an option\n-->");
            System.out.print("[OPTIONS- 0:Add User, 1:Delete User, 2:Sale Statistics, 3:Process Transaction, 4:Process Return, 5:Return Rental\n-1:Logout, -2:Shutdown]-->");
            try {
                switch (managerScan.nextInt()) {
                    case 0:
                        System.out.println("Please enter new username");
                        String uN = managerScan.next();
                        System.out.println("Please enter new user's password");
                        String uPW = managerScan.next();
                        System.out.println("Please enter new user's manager status (true, false)");
                        boolean mng = managerScan.nextBoolean();
                        SQLInterface.getInstance().addUser(uN, uPW, mng);
                        break;
                    case 1:
                        System.out.print("Enter the id of the user you wish to delete\n-->");
                        String delID = managerScan.next();
                        System.out.print("Are you sure you wish to delete " + delID + "\n-->");
                        System.out.print("Y/N?");
                        String yn1 = managerScan.next();
                        switch (yn1.toUpperCase()) {
                            case "Y":
                                SQLInterface.getInstance().deleteUser(delID);
                                break;
                            case "N":
                                System.out.println("No action was taken.");
                                break;
                        }
                        break;
                    case 2:
                        //sale stats not implemented yet, beta?
                        break;
                    case 3:
                        Transaction trans = new Transaction();
                        trans.makeTransaction();
                        break;
                    case 4:
                        Return ret = new Return();
                        ret.makeReturn();
                        break;
                    case 5:
                        Rental rental = new Rental();
                        rental.makeRental();
                        break;
                    case -1:
                        //logout
                        System.out.println("Logging out!");
                        done = true;
                        break;
                    case -2:
                        System.out.println("Are you sure you wish to shut down?");
                        System.out.print("(Y/N)-->");
                        String yn2 = managerScan.next();
                        switch (yn2.toUpperCase()) {
                            case "Y":
                                System.out.println("Shutting Down...");
                                System.exit(0);
                            case "N":
                                break;
                        }
                        break;
                    default:
                        System.out.println("Invalid input, please try again!");
                        break;
                }
            } catch (NumberFormatException|InputMismatchException e) {
                System.out.println("Error reading input, please try again!");
                System.out.println();
                managerScan.nextLine();
            }
        } while (!done);
    }
}
