
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unchecked") //we confirmed our cast is safe
/**
 * a return will accept all dates regardless refund the customer, add item back
 * to inventory, Done
 *
 */
public class Return {
    
    private boolean done = false;
    private double rentDepositReturn = 0.0; //how much money customer gets back of their deposit
    private static double rentalFee = 200.0;
    private boolean paid = false;
    private double returnCash = 0.0;
    
    public Return() {
    }

    /**
     * return routine - prompts user for a receipt number looks up those items
     * in receipt db and checks number of items entered to be returned is
     * possible
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public void makeReturn() throws InterruptedException, IOException 
    {
        //Double total = 0.0;
        Scanner sc = new Scanner(System.in);
        Cart returnCart = new Cart();
        Boolean flag = false;
        System.out.print("Enter the number of the receipt:\n-->");
        int id = sc.nextInt();
        int retQ = 0;
        Receipt r = ReceiptManager.getInstance().getReceipt(id);
        int startRentalDate = ReceiptManager.getInstance().getDate(id);
        
        if (r != null) {
            int number = 0;
            int totalReturnQuant = 0;
            do {
                System.out.print("Enter ID of item to be returned, or -200 to stop entering:\n-->");
                id = sc.nextInt();
                
                if (id != -200) {
                    System.out.print("Enter the number that you wish to return:\n-->");
                    retQ = sc.nextInt();

                    totalReturnQuant += retQ; 

                    Item i = SQLInterface.getInstance().getItem(id);

                    ArrayList<Integer> ids = new ArrayList<Integer>(); //a list of the IDs of the items 
                    for (Item item : (ArrayList<Item>) (r.getCart().getInventory())) 
                    {
                        ids.add(item.getItemNumber());
                    }
                    
                    
                    int existingItems = Collections.frequency(ids, i.getItemNumber());

                    if (retQ <= existingItems) 
                    {
                        SQLInterface.getInstance().updateQuantity(i.getItemNumber(), retQ); //update how many we have

                        for (int counter = 0; counter < retQ; counter++) //for loop for adding to return cart
                        {
                            if (!SQLInterface.getInstance().getItem(id).getIsRental()) {
                                returnCart.add(i); //add the item one time
                            }
                        }
                        
                    } else {
                        System.out.println("You did not buy that many of that item.");
                    }
                } else {
                    flag = true; //indicates you are done returning items
                }
            } while (!flag);

            //done entering items
            for(Item item : (ArrayList<Item>)(r.getCart().getInventory()))
            {
                if (item.getIsRental()) 
                {
                    if (r.checkRentalDate(r)) 
                    {
                        if(number < totalReturnQuant)
                            number++;
                    }   
                }
            }


            if(number > 0)
            {
                this.rentDepositReturn += 5.00 * number;
            }


            if(!r.getCart().containsRentals()) //a transaction with only sales, no rentals
            {
                Scanner pay = new Scanner(System.in);

                Cart salesCart = new Cart(); //a cart for items that are not rentals

                //then put all the items back into inventory
                for(Item i : (ArrayList<Item>)returnCart.getInventory())
                {
                    System.out.print("Enter why you returned " + i.getName() + "\n-->");
                    String reason = pay.nextLine();
                    ReturnManager.getInstance().storeReturnItem(i.getItemNumber(), reason); //store the returned item in the returns db
                    
                    salesCart.add(i);
                }

                r = new Receipt(salesCart, .06, 0);
                r.store();
                r.print();
            }
            else if (r.getCart().getStartDate() < returnCart.getReturnDate()) //if it is not late
            {
               
                Scanner pay = new Scanner(System.in);

                Cart salesCart = new Cart(); //a cart for items that are not rentals
                System.out.println(r.getRentalDeposit());
                System.out.println("Deposit returned: " + this.rentDepositReturn);
                double temp = r.getRentalDeposit() - this.rentDepositReturn;
                System.out.println("Rental Deposit balance remaining: " + temp);

                //then put all the items back into inventory
                for(Item i : (ArrayList<Item>)returnCart.getInventory())
                {
                    if(i.getIsRental())
                        SQLInterface.getInstance().updateQuantity(i.getItemNumber(), 1); //add item back into inventory once the rental comes back in
                    else //is a sold item, not a rental
                    {
                        System.out.print("Enter why you returned " + i.getName() + "\n-->");
                        String reason = pay.nextLine();
                        ReturnManager.getInstance().storeReturnItem(i.getItemNumber(), reason); //store the returned item in the returns db
                        
                        salesCart.add(i);
                    }
                }

                r = new Receipt(salesCart, .06, 0);
                r.store();
                r.print();

            }
            else //rental that is late
            {
                Scanner pay = new Scanner(System.in);
                System.out.println("Payment owed for late fee: $200.00");
                System.out.print("Enter payment type (0: Cash, 1: Credit)\n-->");

                if(pay.hasNextInt())
                {
                    boolean valid = false;

                    int payType = 0;

                    while(!valid)
                    {
                        payType = pay.nextInt();

                        if((payType == 1) || (payType == 0))
                            valid = true;
                        else
                            System.out.println("Not a valid input. Try again.\n-->");

                    }

                    double payAmount = 0.0;

                    if(payType == 0) //credit
                    {
                        System.out.println("Enter cash tendered\n-->");
                        if(pay.hasNextDouble())
                        {
                            payAmount = pay.nextDouble();
                        }
                        else
                        {
                            valid = false;
                            while(!valid)
                            {
                                if(pay.hasNextDouble())
                                {
                                    System.out.println("Invalid Input, Try Again.");
                                    payAmount = pay.nextDouble();
                                }
                            }
                        }
                        Double change = makeChange(payAmount, 200.0);

                        System.out.printf("Your change: %6.2f\n", change);

                    }
                    else if(payType == 1) //credit
                    {
                        valid = false;
                        do
                        {
                            valid = false;
                            Scanner creditCardScan = new Scanner(System.in);
                            System.out.print("Enter credit card number\n-->");
                            if (creditCardScan.hasNextLong()) 
                            {
                                long ccN = creditCardScan.nextLong();
                                String ccNString = Long.toString(ccN);
                                boolean validate = CreditCheck.getInstance().creditCardCheck(ccNString);
                                if (validate) 
                                { //valid cc
                                    System.out.println("Valid Credit Card Entered");
                                    valid = true;
                                } 
                                else 
                                {
                                    System.out.print("Invalid Credit Card Entered, Try Again\n-->"); 
                                }
                            }
                        }while(!valid);
                    }


                    Cart salesCart = new Cart();

                    //then put all the items back into inventory
                    for(Item i : (ArrayList<Item>)returnCart.getInventory())
                    {
                        if(i.getIsRental())
                            SQLInterface.getInstance().updateQuantity(i.getItemNumber(), 1); //add item back into inventory once the rental comes back in
                        else //is a sold item, not a rental
                        {
                            System.out.print("Enter why you returned " + i.getName() + "\n-->");
                            String reason = pay.nextLine();
                            ReturnManager.getInstance().storeReturnItem(i.getItemNumber(), reason); //store the returned item in the returns db
                        
                            salesCart.add(i); //a cart for items that are not rentals
                        }
                    }

                    r = new Receipt(salesCart, .06, 0);
                    r.store();
                    r.print();
                }

            }
        } else {
            System.out.println("Receipt does not exist.");
        }
    }

    public double makeChange(double cash, double total) 
    {
        double ret = 0.0;
        Scanner cashIn = new Scanner(System.in);

        if (cash >= total) {
            ret = cash - total;
            //System.out.println("cash: " + cash + "total: " + total);
        } else if (cash < total) 
        {
            System.out.println("Insufficient Funds!");
            System.out.print("Enter more money to complete the sale:\n-->");

            double c = 0.0;
            if (cashIn.hasNextDouble()) 
            {
                c = cashIn.nextDouble();
            }

            cash = cash + c;
            ret = makeChange(cash, total);

        }
        return ret;
    }
}
