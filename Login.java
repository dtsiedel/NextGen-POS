
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

    private Login() {
    }

    public static synchronized Login getInstance() {
        if (inst == null) {
            inst = new Login();
        }
        return inst;
    }

    public void startLogin() {
        Scanner id = new Scanner(System.in);
        while (!done) {
            System.out.print("Please enter Cashier Login\n-->");
            //would need to check database, default 1111
            input = id.next();
            if (Integer.parseInt(input) == 1111) { //temp
                System.out.println("Success");
                this.done = true;
            } else if (Integer.parseInt(input) != 1111) { //temp 1111
                //attempts--;
                //if(attempts == 0){
                //    System.out.println("Too many attempts, exiting");
                //    System.exit(1);
                //}
                System.out.println("Invalid ID, Remaining Attempts: " + attempts);
            }
        }
    }
}
