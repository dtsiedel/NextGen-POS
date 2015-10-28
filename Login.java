
/**
 * used to login to register and begin performing actions
 *
 */
import java.util.Scanner;

public class Login {

    private static Login inst;
    private String input;
    private int attempts = 5;
    private boolean done = false;
    private final int cashierIn = -1;
    private final int mngIn = -0;

    private Login() {
    }

    public static synchronized Login getInstance() {
        if (inst == null) {
            inst = new Login();
        }
        return inst;
    }

    public int startLogin() {
        Scanner id = new Scanner(System.in);
        int ret = -9999;
        do {
            System.out.print("Please enter Login\n-->");
            //would need to check database, default 1111 - cashier, 2222- manager
            input = id.next();
            if (Integer.parseInt(input) == 1111) { //temp cashier login
                System.out.println("Cashier Login Success");
                this.done = true;
                ret = cashierIn;
            } else if (Integer.parseInt(input) == 2222) { //temp manager login
                System.out.println("Manager Login Success");
                this.done = true;
                ret = mngIn;
            } else if (Integer.parseInt(input) != 1111 && Integer.parseInt(input) != 2222) { //temp 1111 and temp 2222
                //attempts--;
                //if(attempts == 0){
                //    System.out.println("Too many attempts, exiting");
                //    System.exit(1);
                //}
                System.out.println("Invalid ID, Remaining Attempts: " + attempts);
            }
        } while (!done);
        return ret;
    }
}
