
import java.util.Scanner;

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
     */
    public static void managerDo() {
        Scanner managerScan = new Scanner(System.in);
        boolean done = false;
        do {
            System.out.print("Please select an option\n-->");
            System.out.print("[OPTIONS- 0:Add User, 1:Delete User, 2:Sale Statistics, -1:Logout, -2:Shutdown]");
            OUTER:
            switch (managerScan.nextInt()) {
                case 0:
                    //add user
                    /*SQLInterface.getInstance().addUser(new User());*/
                    break;
                case 1:
                    System.out.print("Enter the id of the user you wish to delete\n-->");
                    int delID = managerScan.nextInt();
                    System.out.print("Are you sure you wish to delete " + /*SQLInterface.getInstance().getUser(delID).getUserName*/ "" + "\n-->");
                    System.out.println("Y/N?");
                    String yn1 = managerScan.next();
                    switch (yn1.toUpperCase()) {
                        case "Y":
                            /*SQLInterface.getInstance().deleteUser(delID)*/
                            break;
                        case "N":
                            System.out.println("No action was taken.");
                            break;
                    }
                    break;
                case 2:
                    //sale stats not implemented yet, beta?
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
        } while (!done);
    }
}
