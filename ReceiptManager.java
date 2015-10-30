//manages storage and retrieval of receipt objects
public class ReceiptManager
{
	private static ReceiptManager instance = null;

	//IMPORTANT: Keep these the proper index in the database synchronized with these variables at all time
	private static int receiptCount;       //the current number of receipts in the db, one more than this is the next number to insert at
	private static int receiptItemCount;   //current number of items listed in receiptItems db, one more than this is next location to store in

	//private for Singleton pattern
	private ReceiptManager()
	{
	}

	//Singleton design pattern
	public static synchronized ReceiptManager getInstance()
	{
		//initialize receiptCount and receiptItemCount based on values stored in Receipt db index 0
		//must be persistent when program shuts down



		if(instance == null)
		{
			instance = new ReceiptManager();
		}

		return instance;
	}

	//stores the receipt in the database 
	public int storeReceipt(Receipt r)
	{
		//required fields for Receipt DB: id, string for items, string for date

		//look at length of cart to determine how many elements to fill in ReceiptItem db
		//look at receiptItemCount to see where to start adding items
		//create a string of the indices to store items in, "4,5,6", etc
		//look at receiptCount to see where to insert receipt, make new entry at that id with
		//update receiptCount, and the relevant field in database
		//time, and the string created above
		//then, insert one item at each of the indices in the string (one at 4, one at 5, one at 6)
		//update the receuptItemCount with each insertion, and relevant field in database

		//return value is the ID that the receipt is stored in Receipts db
	}

	//gets a receipt in the database based on ID
	public Receipt getReceipt(int receiptID)
	{
		//get the description string from the receipt at the given id
		//split the string on ',' to get a list of IDs to go to
		//make a new Cart
		//for each ID, visit that ID, grab the relevant information and make an item
		//add each item to the cart
		//from the cart and the ID (which I already have), construct a receipt, and return itre
	}
}
