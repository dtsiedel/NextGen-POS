
/**
 * used to login to register and begin performing actions
 *
 */
import java.io.*;
import java.util.Scanner;
import java.security.GeneralSecurityException;

public class Login {

    private static Login inst;
    private String uName;
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

    /**
     * start login, searches db for user name, matches and prompts for pw then
     * if correct pw, will log user in based on manager status
     *
     * @return @throws InterruptedException
     * @throws IOException
     */
    public int startLogin() throws InterruptedException, IOException {
        Scanner id = new Scanner(System.in);
        int ret = -9999;
        do {
            System.out.print("Please enter User Name\n-->"); //get a keyboard up for GUI
            this.uName = id.next();
            System.out.print("Please enter password\n-->");
            String givenPW = id.next();
            try
            {
                if (this.uName.contains(";") || givenPW.contains(";") || givenPW.contains("\\s")){throw new GeneralSecurityException();}
                if (givenPW.equals(SQLInterface.getInstance().getPassword(this.uName))) { //checks db for matching username and password
                    if (SQLInterface.getInstance().isManager(this.uName)) {
                        System.out.println("Manager Login Success");
                        this.done = true;
                        ret = mngIn;
                    } else if (!(SQLInterface.getInstance().isManager(this.uName))) {
                        System.out.println("Cashier Login Success");
                        this.done = true;
                        ret = cashierIn;
                    }
                } else if (SQLInterface.getInstance().getPassword(this.uName).equals("~") || !(givenPW.contentEquals(SQLInterface.getInstance().getPassword(this.uName)))) {
                    attempts--;
                    if(attempts == 0){
                       System.out.println("Too many attempts, exiting");
                       System.exit(1);
                    }

                    System.out.println("Invalid User Name/Password, Remaining Attempts: " + attempts);
                    System.out.println();
                    id.nextLine();
                }
            }
            catch(FileNotFoundException| GeneralSecurityException e)
            {
                attempts--;
                if(attempts == 0){
                   System.out.println("Too many attempts, exiting");
                   System.exit(1);
                }

                System.out.println("Invalid User Name/Password, Remaining Attempts: " + attempts);
                System.out.println();
                id.nextLine();
            }
        } while (!done);
        return ret;
    }
}
