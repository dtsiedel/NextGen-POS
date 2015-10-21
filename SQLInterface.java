import java.io.*;
import java.util.*;

//TODO: improve encapsulation by adding "getAdjustedString() method"
//TODO: add methods to change price and quantity

public class SQLInterface
{
	private static SQLInterface instance; //for implementation of singleton

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

	//given an id number, prints to stdout relevant information
	public String getInfo(int id) throws InterruptedException, IOException
	{

		//call sqlite command to access database
		String[] command = {"sqlite3", "products", "SELECT * FROM products WHERE id = "};
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

		if(f.length() == 0) //if there are no lines in the output, item not found
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
	public Double getPrice(int id) throws InterruptedException, IOException
	{
		String detailString = this.getInfo(id);

		//weird workaround because Java's split method doesn't work on "|" character
		String adjustedString = detailString.replace("|", "~");

		String[] vals = adjustedString.split("~");

		return Double.parseDouble(vals[2]);

	}

	//returns the name of the product based on id
	public String getProductName(int id) throws InterruptedException, IOException
	{
		String detailString = this.getInfo(id);

		String adjustedString = detailString.replace("|", "~");

		String[] vals = adjustedString.split("~");

		return vals[1];
	}

	//returns the current quantity of the item in stock
	public int getQuantity(int id) throws InterruptedException, IOException
	{
		String detailString = this.getInfo(id);
		
		String adjustedString = detailString.replace("|", "~");

		String[] vals = adjustedString.split("~");

		return Integer.parseInt(vals[3]);

	}


	//updates the quantity of an item. First int is the id, the second is the 
	//amount to increment. to decrement, provide a negative number (most common use)
	//returns the updated value
	public int updateQuantity(int id, int quantityChange) throws InterruptedException, IOException
	{
		int currentQuant = this.getQuantity(id);
		int newQuant = currentQuant+quantityChange;

		String base = "UPDATE products SET quantity=";
		String command = base + Integer.toString(newQuant) + " WHERE id=" + Integer.toString(id);
		String[] update = {"sqlite3", "products", command};

		//System.out.println(command);
		ProcessBuilder pb = new ProcessBuilder(update);

		Process updater = pb.start();
		updater.waitFor();

		return newQuant; 
	}


	//allows for the insertion of a new item into the database
	//primarily used as a utility function by the database initialization function
	//it is (currently) the responsibility of the client function to ensure that an id is not occupied before executing this command
	public void addProduct(int id, String name, Double price, int quantity) throws InterruptedException, IOException
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

		SQLInterface inter = SQLInterface.getInstance();

		//test basic accessing of fields
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the ID of the product you wish to learn about: ");
		int id = sc.nextInt();


		Double price = inter.getPrice(id);
		System.out.println("Price: " + price);

		String name = inter.getProductName(id);
		System.out.println("Name: " + name);

		int quantity = inter.getQuantity(id);
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

			inter.addProduct(id, name, price, quantity);

			System.out.println("Price of " + name + ": " + inter.getPrice(id));

		}

		System.out.print("Change the quantity of a product?");
		cont = sc.next().charAt(0);

		if((cont == 'y') || (cont == 'Y'))
		{
			System.out.print("ID: ");
			id = sc.nextInt();

			System.out.print("Quantity change: ");
			int q = sc.nextInt();

			System.out.println("Quantity before " + Integer.toString(inter.getQuantity(id)));
			inter.updateQuantity(id, q);
			System.out.println("Quantity after " + Integer.toString(inter.getQuantity(id)));
		}


	}


}
