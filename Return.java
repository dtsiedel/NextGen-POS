
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

    public Return() {
    }


    public void makeReturn() throws InterruptedException, IOException
    {
        //Double total = 0.0;
        Scanner sc = new Scanner(System.in);
        Cart returnCart = new Cart();
        Boolean flag = false;

        System.out.println("Enter the number of the receipt: ");
        int id = sc.nextInt();

        Receipt r = ReceiptManager.getInstance().getReceipt(id);

        if(r != null)
        {
            do 
            {
                System.out.print("Enter ID of item to be returned, or -200 to stop entering: ");
                id = sc.nextInt();

                if(id!=-200)
                {
                    System.out.print("Enter the number that you wish to return: ");
                    int retQ = sc.nextInt();

                    Item i = SQLInterface.getInstance().getItem(id);

                    ArrayList<Integer> ids = new ArrayList<Integer>(); //a list of the IDs of the items 
                    for(Item item : (ArrayList<Item>)(r.getCart().getInventory()))
                        ids.add(item.getItemNumber());

                    int existingItems = Collections.frequency(ids, i.getItemNumber());
                    //System.out.println("TEST: items: " + existingItems);

                    if(retQ <= existingItems)
                    {
                        SQLInterface.getInstance().updateQuantity(i.getItemNumber(), retQ); //update how many we have

                        for(int counter = 0; counter < retQ; counter++) //for loop for adding to return cart
                        {
                            if(!SQLInterface.getInstance().getItem(id).getIsRental()){
                                returnCart.add(i); //add the item one time
                            }
                        }


                    }
                    else
                    {
                        System.out.println("You did not buy that many of that item.");
                    }
                }   
                else
                {
                    flag = true; //indicates you are done returning items
                }
            }while(!flag);

            Double tax = .06 * returnCart.getSubtotal();
            Receipt returnReceipt = new Receipt(returnCart, tax, 0); 
            returnReceipt.store();
            returnReceipt.print();
        }
        else
        {
            System.out.println("Receipt does not exist.");
        }        
    }
}
