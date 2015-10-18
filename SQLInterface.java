import java.io.*;
import java.util.*;

public class SQLInterface
{
	//given an id number, prints to stdout relevant information
	public static String getInfo(String id) throws InterruptedException, IOException
	{

		if(id.matches("^\\d+$")) //if the input is a number
		{
			//intentionally blank
		}
		else
		{
			System.out.println("Invalid Input! Please enter a number next time.");
			System.exit(0);
		}

		//call sqlite command to access database
		String[] command = {"sqlite3", "products", "SELECT * FROM products WHERE id = "};
		command[2] = command[2] + id + ";";
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
			System.out.println("Item not found");
			deleter.start(); ///delete output.txt
			System.exit(0);
		}

		String result = sc.nextLine();

		deleter.start(); ///delete output.txt

		return result;

	}

	//returns just the price based on id
	public static Double getPrice(String id) throws InterruptedException, IOException
	{
		String detailString = getInfo(id);

		String adjustedString = detailString.replace("|", "~");

		String[] vals = adjustedString.split("~");

		return Double.parseDouble(vals[2]);

	}

	//returns the name of the product based on id
	public static String getProductName(String id) throws InterruptedException, IOException
	{
		String detailString = getInfo(id);

		String adjustedString = detailString.replace("|", "~");

		String[] vals = adjustedString.split("~");

		return vals[1];
	}


	//test stub 
	public static void main(String[] args) throws InterruptedException, IOException
	{
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the ID of the product you wish to learn about: ");
		String id = sc.next();

		Double price = getPrice(id);
		System.out.println("Price: " + price);

		String name = getProductName(id);
		System.out.println("Name: " + name);

	}


}
