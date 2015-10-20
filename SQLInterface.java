import java.io.*;
import java.util.*;

//TODO: improve encapsulation by adding "getAdjustedString() method"
//TODO: add methods to change price and quantity

public class SQLInterface
{
	//given an id number, prints to stdout relevant information
	public static String getInfo(int id) throws InterruptedException, IOException
	{


		//call sqlite command to access database
		String[] command = {"sqlite3", "products", "SELECT * FROM products WHERE id = "};
		command[2] = command[2] + Integer.toString(id) + ";";
		ProcessBuilder pb = new ProcessBuilder(command);


		File outfile = new File("output.txt"); //write output to a file
		pb.redirectOutput(outfile);
		pb.redirectError(outfile);
		Process p = pb.start(); //execute the command loaded prior
		p.waitFor(); //not entirely sure how this works but is apparently necessary


		//prepare to remove the text file you created
		String[] deleteCommand = {"rm", "output.txt"};
		ProcessBuilder deleter = new ProcessBuilder(deleteCommand);


		File f = new File("output.txt"); //read file output (can't redirect with '>' in this context)
		Scanner sc = new Scanner(f);

		if(f.length() == 0) //if there are no lines in the output, item not found
		{
			System.out.println("Item not found, ensure you entered the ID properly and try again.");
			deleter.start(); ///delete output.txt
			System.exit(0);
		}

		String result = sc.nextLine();

		p = deleter.start(); ///delete output.txt
		p.waitFor(); //wait for delete process to finish before proceeding

		return result;

	}

	//returns just the price based on id
	public static Double getPrice(int id) throws InterruptedException, IOException
	{
		String detailString = getInfo(id);

		//weird workaround because Java's split method doesn't work on "|" character
		String adjustedString = detailString.replace("|", "~");

		String[] vals = adjustedString.split("~");

		return Double.parseDouble(vals[2]);

	}

	//returns the name of the product based on id
	public static String getProductName(int id) throws InterruptedException, IOException
	{
		String detailString = getInfo(id);

		String adjustedString = detailString.replace("|", "~");

		String[] vals = adjustedString.split("~");

		return vals[1];
	}

	//returns the current quantity of the item in stock
	public static int getQuantity(int id) throws InterruptedException, IOException
	{
		String detailString = getInfo(id);
		
		String adjustedString = detailString.replace("|", "~");

		String[] vals = adjustedString.split("~");

		return Integer.parseInt(vals[3]);

	}


	//allows for the insertion of a new item into the database
	//primarily used as a utility function by the database initialization function
	//it is (currently) the responsibility of the client function to ensure that an id is not occupied before executing this command
	public static void addProduct(int id, String name, Double price, int quantity) throws InterruptedException, IOException
	{
		String base = "INSERT INTO products VALUES("; //base command to insert
		//then add appropriate parameters
		String insertCommand = base + Integer.toString(id) + ",\"" + name + "\"," + Double.toString(price) + "," + quantity + ");";
		
		String[] commands = {"sqlite3", "products", insertCommand};


		ProcessBuilder pb = new ProcessBuilder(commands);
		Process p = pb.start();
		p.waitFor();


	}


	//test stub, just runs through the functions
	public static void main(String[] args) throws InterruptedException, IOException
	{
		//test basic accessing of fields
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the ID of the product you wish to learn about: ");
		int id = sc.nextInt();


		Double price = getPrice(id);
		System.out.println("Price: " + price);

		String name = getProductName(id);
		System.out.println("Name: " + name);

		int quantity = getQuantity(id);
		System.out.println("Quantity: " + quantity);


		//test adding an item
		System.out.print("Add a product? ");
		char cont = sc.next().charAt(0);

		if((cont == 'y') || (cont == 'Y'))
		{
			System.out.print("ID: ");
			id = sc.nextInt();

			System.out.print("Item name: ");
			name = sc.next();

			System.out.print("Price: ");
			price = Double.parseDouble(sc.next());

			System.out.print("Quantity: ");
			quantity = sc.nextInt();

			addProduct(id, name, price, quantity);

			System.out.println("Price of " + name + ": " + getPrice(id));

		}


	}

}
