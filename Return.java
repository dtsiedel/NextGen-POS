
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
    private double rentalFee = 0.0;
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
    public void makeReturn() throws InterruptedException, IOException {
        //Double total = 0.0;
        Scanner sc = new Scanner(System.in);
        Cart returnCart = new Cart();
        Boolean flag = false;
        System.out.println("Enter the number of the receipt: ");
        int id = sc.nextInt();
        
        Receipt r = ReceiptManager.getInstance().getReceipt(id);
        
        if (r != null) {
            do {
                System.out.print("Enter ID of item to be returned, or -200 to stop entering: ");
                id = sc.nextInt();
                
                if (id != -200) {
                    System.out.print("Enter the number that you wish to return: ");
                    int retQ = sc.nextInt();
                    
                    Item i = SQLInterface.getInstance().getItem(id);
                    
                    ArrayList<Integer> ids = new ArrayList<Integer>(); //a list of the IDs of the items 
                    for (Item item : (ArrayList<Item>) (r.getCart().getInventory())) {
                        ids.add(item.getItemNumber());
                        if (item.getIsRental()) {
                            if (r.checkRentalDate(r)) {
                                this.rentDepositReturn += 5.00;
                                r.getCart().removeDate(r.getCart().getReturnDate());
                            } else {
                                this.rentalFee += item.getPrice() * .625;
                            }
                        }
                    }
                    
                    int existingItems = Collections.frequency(ids, i.getItemNumber());
                    //System.out.println("TEST: items: " + existingItems);

                    if (retQ <= existingItems) {
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
            if (this.rentalFee == 0) {
                Double tax = .06 * returnCart.getSubtotal();
                Receipt returnReceipt = new Receipt(returnCart, tax, 0);
                returnReceipt.store();
                returnReceipt.print();
                System.out.println("Deposit returned: " + this.rentDepositReturn);
                this.rentDepositReturn = r.getRentalDeposit() - this.rentDepositReturn;
                System.out.println("Rental Deposit balance remaining: " + this.rentDepositReturn);
            } else if (r.getCart().getPaymentType() == 1) {
                Double tax = .06 * returnCart.getSubtotal();
                Receipt returnReceipt = new Receipt(returnCart, tax, 1);
                returnReceipt.store();
                returnReceipt.print();
                System.out.println("Deposit returned: " + this.rentDepositReturn);
                this.rentDepositReturn = r.getRentalDeposit() - this.rentDepositReturn;
                System.out.println("Rental Deposit balance remaining: " + this.rentDepositReturn);
                System.out.println("Rental Fee Due: " + this.rentalFee);
                Scanner ccScan = new Scanner(System.in);
                do {
                    if (ccScan.hasNextInt()) {
                        int ccN = ccScan.nextInt();
                        String ccNString = new StringBuffer(ccN).toString();
                        if (CreditCheck.getInstance().creditCardCheck(ccNString)) {
                            System.out.println("Payment processed, Thank you");
                            paid = true;
                        } else {
                            System.out.println("Payment error, try again");
                        }
                    }
                } while (!paid);
            } else {
                Double tax = .06 * returnCart.getSubtotal();
                Receipt returnReceipt = new Receipt(returnCart, tax, 0);
                returnReceipt.store();
                returnReceipt.print();
                System.out.println("Deposit returned: " + this.rentDepositReturn);
                this.rentDepositReturn = r.getRentalDeposit() - this.rentDepositReturn;
                System.out.println("Rental Deposit balance remaining: " + this.rentDepositReturn);
                System.out.println("Rental Fee Due: " + this.rentalFee);
                System.out.print("Enter cash received\n-->");
                Scanner feeScan = new Scanner(System.in);
                do {
                    if (feeScan.hasNextDouble()) {
                        this.returnCash = feeScan.nextDouble();
                        if (this.returnCash >= this.rentalFee) {
                            System.out.println("Your fee has been paid, and your change is: " + (this.returnCash - this.rentalFee));
                            paid = true;
                        } else if (this.returnCash < this.rentalFee) {
                            System.out.println("Insufficient funds, try again");
                        }
                    }
                } while (!paid);
            }
        } else {
            System.out.println("Receipt does not exist.");
        }
    }
}
