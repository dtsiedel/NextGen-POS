
import java.io.*;
import java.util.*;

//TODO: make a function to perform a SQL operation and return the result (given a string of commands) to improve cohesion
public class SQLInterface {

<<<<<<< HEAD
    private static SQLInterface instance; //for implementation of singleton
=======
public class SQLInterface
{
	private static SQLInterface instance; //for implementation of singleton

	private final static String sqlite = "sqlite3"; //the version of sqlite in use
	private final static String dbName = "data";    //the name of the database being used

	//private constructor prevents outside clases from accessing
	private SQLInterface()  
	{
		//intentionally blank
	}

	//returns the instance if it exists, or makes exactly one if 
	//one does not yet exist
	public synchronized static SQLInterface getInstance()
	{
		if(instance == null)
		{
			instance = new SQLInterface();
		}

		return instance;
	}


	//given a username, returns the password associated with it (unencrypted, yay)
	//if the name is not in the db, prints an error string and returns a tilde (indicates an error)
	//recommended usage: scan in username and password from user,
	//					 then compare their input to result of getPassword(username)
	public String getPassword(String username) throws InterruptedException, IOException
	{
		String[] command = {sqlite, dbName, "SELECT password FROM users WHERE name="};
		command[2] = command[2] + "\"" + username + "\";";

		// for(String s : command)
		// 	System.out.println(s);

		ProcessBuilder pb = new ProcessBuilder(command);


		File outfile = new File("output.txt"); //write output to a file
		pb.redirectOutput(outfile);
		pb.redirectError(outfile);
		Process get = pb.start(); //execute the command loaded prior
		get.waitFor(); //wait for operation to finish


		String[] deleteCommand = {"rm", "output.txt"};
		ProcessBuilder deleter = new ProcessBuilder(deleteCommand); //to get rid of the text file you made


		File f = new File("output.txt"); //read file output (can't redirect output with '>' in this context)
		Scanner sc = new Scanner(f);

		if(f.length() == 0) //if there are no lines in the output, item not found
		{
			System.out.println("User not found, ensure you did not make a typo and try again.");
			deleter.start(); ///delete output.txt
			return "~"; 
		}
>>>>>>> refs/remotes/origin/master

    private final static String sqlite = ".\\sqlite3"; //the version of sqlite in use
    private final static String dbName = "data";    //the name of the database being used

    //private constructor prevents outside clases from accessing
    private SQLInterface() {
        //intentionally blank
    }

    //returns the instance if it exists, or makes exactly one if 
    //one does not yet exist
    public synchronized static SQLInterface getInstance() {
        if (instance == null) {
            instance = new SQLInterface();
        }

        return instance;
    }

<<<<<<< HEAD
    //given a username, returns the password associated with it (unencrypted, yay)
    //if the name is not in the db, prints an error string and returns a tilde (indicates an error)
    //recommended usage: scan in username and password from user,
    //					 then compare their input to result of getPassword(username)
    public String getPassword(String username) throws InterruptedException, IOException {
        String[] command = {sqlite, dbName, "SELECT password FROM users WHERE name="};
        command[2] = command[2] + "\"" + username + "\";";
=======
	//adds a user to the database, given the username and password. 
	public void addUser(String username, String password) throws InterruptedException, IOException
	{
		String[] command = {sqlite, dbName, "INSERT INTO users VALUES(\""};
		command[2] = command[2] + username + "\",\"" + password + "\");";

		ProcessBuilder pb = new ProcessBuilder(command);
		Process add = pb.start();

		add.waitFor(); //wait for add to finish before proceeding

	}

	public Boolean isRentable(int id) throws InterruptedException, IOException
	{
		String detailString = this.getInfo(id);
>>>>>>> refs/remotes/origin/master

        // for(String s : command)
        // 	System.out.println(s);
        ProcessBuilder pb = new ProcessBuilder(command);

        File outfile = new File("output.txt"); //write output to a file
        pb.redirectOutput(outfile);
        pb.redirectError(outfile);
        Process get = pb.start(); //execute the command loaded prior
        get.waitFor(); //wait for operation to finish

<<<<<<< HEAD
        String[] deleteCommand = {"rm", "output.txt"};
        ProcessBuilder deleter = new ProcessBuilder(deleteCommand); //to get rid of the text file you made

        File f = new File("output.txt"); //read file output (can't redirect output with '>' in this context)
        Scanner sc = new Scanner(f);
=======
		int rent = Integer.parseInt(vals[4]); //vals[4] contains the "rentable" field
>>>>>>> refs/remotes/origin/master

        if (f.length() == 0) //if there are no lines in the output, item not found
        {
            System.out.println("User not found, ensure you did not make a typo and try again.");
            deleter.start(); ///delete output.txt
            return "~";
        }

        String result = sc.nextLine();

        Process delete = deleter.start(); ///delete output.txt
        delete.waitFor(); //wait for delete process to finish before proceeding

        return result;

    }

    //adds a user to the database, given the username and password. 
    public void addUser(String username, String password) throws InterruptedException, IOException {
        String[] command = {sqlite, dbName, "INSERT INTO users VALUES(\""};
        command[2] = command[2] + username + "\",\"" + password + "\");";

        ProcessBuilder pb = new ProcessBuilder(command);
        Process add = pb.start();

        add.waitFor(); //wait for add to finish before proceeding

<<<<<<< HEAD
    }
=======
		//call sqlite command to access database
		String[] command = {sqlite, dbName, "SELECT * FROM products WHERE id = "};
		command[2] = command[2] + Integer.toString(id) + ";";
		ProcessBuilder pb = new ProcessBuilder(command);
>>>>>>> refs/remotes/origin/master

    public Boolean isRentable(int id) throws InterruptedException, IOException {
        String detailString = this.getInfo(id);

        String adjustedString = detailString.replace("|", "~");

        String[] vals = adjustedString.split("~");

        int rent = Integer.parseInt(vals[4]); //vals[4] contains the "rentable" field

        Boolean rentable = (rent == 1) ? true : false; //more ternary op

        return rentable;
    }

    //returns an Item object associated with the given ID
    public Item getItem(int id) throws InterruptedException, IOException {
        Double price = this.getPrice(id);
        String name = this.getProductName(id);
        Boolean rentable = this.isRentable(id);
        int quantity = this.getQuantity(id); //item does not yet take this field

        return new Item(rentable, price, id, name, quantity);

    }

    //given an id number, prints to stdout relevant information
    public String getInfo(int id) throws InterruptedException, IOException {

        //call sqlite command to access database
        String[] command = {sqlite, dbName, "SELECT * FROM products WHERE id = "};
        command[2] = command[2] + Integer.toString(id) + ";";
        ProcessBuilder pb = new ProcessBuilder(command);

        File outfile = new File("output.txt"); //write output to a file
        pb.redirectOutput(outfile);
        pb.redirectError(outfile);
        Process select = pb.start(); //execute the command loaded prior
        select.waitFor(); //not entirely sure how this works but is apparently necessary

        //prepare to remove the text file you created
        String[] deleteCommand = {"rm", "output.txt"};
        ProcessBuilder deleter = new ProcessBuilder(deleteCommand);

        File f = new File("output.txt"); //read file output (can't redirect with '>' in this context)
        Scanner sc = new Scanner(f);

        if (f.length() == 0) //if there are no lines in the output, item not found
        {
            System.out.println("Item not found, ensure you entered the ID properly and try again.");
            deleter.start(); ///delete output.txt
            System.exit(0);
        }

        String result = sc.nextLine();

        Process delete = deleter.start(); ///delete output.txt
        delete.waitFor(); //wait for delete process to finish before proceeding

        return result;

    }

    //returns just the price based on id
    public Double getPrice(int id) throws InterruptedException, IOException {
        String detailString = this.getInfo(id);

        //weird workaround because Java's split method doesn't work on "|" character
        String adjustedString = detailString.replace("|", "~");

        String[] vals = adjustedString.split("~");

        return Double.parseDouble(vals[2]);

    }

    //returns the name of the product based on id
    public String getProductName(int id) throws InterruptedException, IOException {
        String detailString = this.getInfo(id);

        String adjustedString = detailString.replace("|", "~");

<<<<<<< HEAD
        String[] vals = adjustedString.split("~");
=======
		String base = "UPDATE products SET quantity=";
		String command = base + Integer.toString(newQuant) + " WHERE id=" + Integer.toString(id);
		String[] update = {sqlite, dbName, command};
>>>>>>> refs/remotes/origin/master

        return vals[1];
    }

    //returns the current quantity of the item in stock
    public int getQuantity(int id) throws InterruptedException, IOException {
        String detailString = this.getInfo(id);

        String adjustedString = detailString.replace("|", "~");

        String[] vals = adjustedString.split("~");

        return Integer.parseInt(vals[3]);

    }

<<<<<<< HEAD
    //updates the quantity of an item. First int is the id, the second is the 
    //amount to increment. to decrement, provide a negative number (most common use)
    //returns the updated value
    public int updateQuantity(int id, int quantityChange) throws InterruptedException, IOException {
        int currentQuant = this.getQuantity(id);
        int newQuant = currentQuant + quantityChange;
=======
		String base = "INSERT INTO products VALUES("; //base command to insert
		//then add appropriate parameters
		String insertCommand = base + Integer.toString(id) + ",\"" + name + "\"," + Double.toString(price) + "," + quantity + "," + rent + ");";
		
		String[] commands = {sqlite, dbName, insertCommand};
>>>>>>> refs/remotes/origin/master

        String base = "UPDATE products SET quantity=";
        String command = base + Integer.toString(newQuant) + " WHERE id=" + Integer.toString(id);
        String[] update = {sqlite, dbName, command};

        //System.out.println(command);
        ProcessBuilder pb = new ProcessBuilder(update);

        Process updater = pb.start();
        updater.waitFor();

        return newQuant;
    }

    //allows for the insertion of a new item into the database
    //primarily used as a utility function by the database initialization function
    //it is (currently) the responsibility of the client function to ensure that an id is not occupied before executing this command
    public void addProduct(int id, String name, Double price, int quantity, Boolean rentable) throws InterruptedException, IOException {
        int rent = (rentable) ? 1 : 0; //that ternary operator (1 if rentable, 0 if not)

        String base = "INSERT INTO products VALUES("; //base command to insert
        //then add appropriate parameters
        String insertCommand = base + Integer.toString(id) + ",\"" + name + "\"," + Double.toString(price) + "," + quantity + "," + rent + ");";

        String[] commands = {sqlite, dbName, insertCommand};

        ProcessBuilder pb = new ProcessBuilder(commands);
        Process p = pb.start();
        p.waitFor();

    }

    //test stub, just runs through the functions
    public static void main(String[] args) throws InterruptedException, IOException {

        SQLInterface inter = SQLInterface.getInstance();

        Scanner sc = new Scanner(System.in);

        //testing users database
        System.out.println("Testing user database: \n");

<<<<<<< HEAD
        System.out.print("Enter Username: (users: test, demo): ");
        String username = sc.next();
        System.out.print("Enter your password: ");
        String givenPassword = sc.next();
=======
		System.out.print("Enter a new user? (y/n): ");
		char cont = sc.next().charAt(0); 
		if((cont == 'y') || (cont == 'Y'))
		{
			System.out.print("Enter username of new user: ");
			String newUser = sc.next();
			System.out.print("Enter the password of the new user: ");
			String newPass = sc.next();

			inter.addUser(newUser, newPass);
		}


		//test basic accessing of fields
		System.out.print("Enter the ID of the product you wish to learn about: ");
		int id = sc.nextInt();
>>>>>>> refs/remotes/origin/master

        String actualPassword = inter.getPassword(username); //the actual password in the db

        if (actualPassword.equals("~")) //indicating username is not in db
        {
            System.exit(0);
        }

        if (givenPassword.equals(inter.getPassword(username))) //indicating the password matches
        {
            System.out.println("\nWelcome, " + username);
        } else {
            System.out.println("Password does not match.");
            System.exit(0);
        }

        System.out.print("Enter a new user? (y/n): ");
        char cont = sc.next().charAt(0);
        if ((cont == 'y') || (cont == 'Y')) {
            System.out.print("Enter username of new user: ");
            String newUser = sc.next();
            System.out.print("Enter the password of the new user: ");
            String newPass = sc.next();

            inter.addUser(newUser, newPass);
        }

        //test basic accessing of fields
        System.out.print("Enter the ID of the product you wish to learn about: ");
        int id = sc.nextInt();

<<<<<<< HEAD
        Double price = inter.getPrice(id);
        System.out.println("Price: " + price);
=======
		//test adding an item
		System.out.print("Add a product? (y/n): ");
		cont = sc.next().charAt(0);
>>>>>>> refs/remotes/origin/master

        String name = inter.getProductName(id);
        System.out.println("Name: " + name);

        int quantity = inter.getQuantity(id);
        System.out.println("Quantity: " + quantity);

        Boolean rent = inter.isRentable(id);
        System.out.println("Rentable: " + rent);

        //test adding an item
        System.out.print("Add a product? (y/n): ");
        cont = sc.next().charAt(0);

        if ((cont == 'y') || (cont == 'Y')) {
            System.out.print("ID: ");
            id = sc.nextInt();

            System.out.print("Item name: ");
            name = sc.next();

            System.out.print("Price: ");
            price = Double.parseDouble(sc.next());

            System.out.print("Quantity: ");
            quantity = sc.nextInt();

            System.out.print("Rentable? (Y/N): ");
            rent = false;
            char rentable = sc.next().charAt(0);

            if ((rentable == 'y') || (rentable == 'Y')) {
                rent = true;
            }

            inter.addProduct(id, name, price, quantity, rent);

            System.out.println("Price of " + name + ": " + inter.getPrice(id));

        }

        System.out.print("Change the quantity of a product? (y/n): ");
        cont = sc.next().charAt(0);

        if ((cont == 'y') || (cont == 'Y')) {
            System.out.print("ID: ");
            id = sc.nextInt();

            System.out.print("Quantity change: ");
            int q = sc.nextInt();

            System.out.println("Quantity before " + Integer.toString(inter.getQuantity(id)));
            inter.updateQuantity(id, q);
            System.out.println("Quantity after " + Integer.toString(inter.getQuantity(id)));
        }

        //testing getItem() function (not just individual methods)
        System.out.print("Get total item info for item(provide index): ");
        id = sc.nextInt();

        Item newItem = inter.getItem(id);

        System.out.println("Price: " + newItem.getPrice());
        System.out.println("ID:" + newItem.getItemNumber());
        System.out.println("Name: " + newItem.getName());
        System.out.println("Rentable? " + newItem.getIsRental());
        System.out.println("Quantity remaining: " + newItem.getQuantity());

    }

}
