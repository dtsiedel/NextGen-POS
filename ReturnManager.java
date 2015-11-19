//manages insertion of new returns into a return database, 
//so that items returned due to a deficiency will not be
//immediately returned to the working inventory

import java.util.*;
import java.io.*;

public class ReturnManager
{
	private static ReturnManager instance = null; //unique instance

	private static String returnDB = "returns"; //name of returns database

	//private constructor for Singleton design pattern
	private ReturnManager()
	{
	}

	//getInstance method, controls access to ReturnManager creation 
	public static synchronized ReturnManager getInstance()
	{
		if(instance == null)
			instance = new ReturnManager();

		return instance;
	}

	//handles the low-level operation of making a SQL call (the proper way to handle what I did in SQLInterface, rework for beta)
    //should call getSQLOutput after to get result and clean up file you made
    private void makeSQLCall(String[] command) throws InterruptedException, IOException 
    {
        ProcessBuilder pb = new ProcessBuilder(command);

        Process run = pb.start(); //execute the command loaded prior
        run.waitFor(); 			  //wait for operation to finish before proceeding

    }


    //stores a new return entry in the database
    public void storeReturnItem(int itemID, String reason) throws InterruptedException, IOException 
    {
    	//the command to insert this value into the database
    	String[] command = {ReceiptManager.sqlite, ReceiptManager.db, ("INSERT INTO " + returnDB + " VALUES (" + itemID + "," + "\"" + reason + "\");")};

    	this.makeSQLCall(command); //make the SQL call to store the return

    }




}