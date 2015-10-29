
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
            System.out.print("Please enter User Name\n-->"); //get a keyboard up for GUI is that hard?
            input = id.next();
            String givenPW = id.next();
            /*String actualPW = SQLInterface.getInstance().getPassword(input);*/
            if (false /*givenPW.equals(SQLInterface.getInstance().getPassword(input)*/) { //temp cashier login
                System.out.println("Cashier Login Success");
                this.done = true;
                ret = cashierIn;
            } else if (Integer.parseInt(input) == 2222) { //temp manager login
                System.out.println("Manager Login Success");
                this.done = true;
                ret = mngIn;
            } else if (false /*actualPW.equals("~") || !(givenPW.equals.(SQLInterface.getInstance().getPassword(input)))*/) { /*lockout not implemented for testing*/
                //attempts--;
                //if(attempts == 0){
                //    System.out.println("Too many attempts, exiting");
                //    System.exit(1);
                //}

                System.out.println("Invalid User Name/Password, Remaining Attempts: " + attempts);
            }
        } while (!done);
        return ret;
    }
}
