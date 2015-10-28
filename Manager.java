
import java.util.Scanner;

/**
 * a manager with admin privileges
 */
import java.util.Scanner;

public class Manager {

    private int id;
    private int pw;

    public Manager() {
    }

    public Manager(int i, int p) {
        this.id = i;
        this.pw = p; //again this is kind of silly no?
    }

    public static void managerDo() {
        Scanner managerScan = new Scanner(System.in);
        boolean done = false;
        do {
            System.out.print("Please select an option\n-->");
            System.out.print("[OPTIONS- 0:Add User, 1:Delete User, 2:Sale Statistics, -1:Logout, -2:Shutdown]");
            switch (managerScan.nextInt()) {
                case 0:
                    //add user
                    break;
                case 1:
                    //delete user, confirmation
                    break;
                case 2:
                    //sale stats not implemented yet, beta?
                    break;
                case -1:
                    //logout
                    done = true;
                    break;
                case -2:
                    System.out.println("Are you sure you wish to shut down?");
                    System.out.print("(Y/N)-->");
                    String yn = managerScan.next();
                    switch (yn.toUpperCase()) {
                        case "Y":
                            System.out.println("Shutting Down...");
                            System.exit(0);
                        case "N":
                            break;
                    }
                    break;
                default:
                    //PUT STUFF HERE!!!
                    break;
            }
        } while (!done);
    }
}
