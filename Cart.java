
import java.util.*;
import java.io.*;

/**
 * Cart class extends Register holds ArrayList of items
 */
public class Cart extends Register {

    protected ArrayList<Item> inventory;

    private double subTotal;
    private double cashIn;
    private int currentDay;
    private int days; //number of days customer wants to rent some item

    /**
     * Cart default constructor
     */
    public Cart() {
        this.subTotal = 0.0;
        inventory = new ArrayList<>(); 
        this.setCurrentDay();
        days = 0;
    }

    //special constructor for use only by ReceiptManager
    //does not make the date right now
    public Cart(boolean flag)
    {
        this.subTotal = 0.0;
        inventory = new ArrayList<>(); 
        days = 0;
    }

    /**
     * adds an item to the cart from the inventory
     *
     * @param item
     */
    //needs to be changed with new update
    public void add(Item item) {
        inventory.add(item);
        this.subTotal += item.getPrice();
        
        if(item.getIsRental())
        {
            Item deposit = new Item(false, 5.0, -150, "Deposit", 1); //the rental deposit
            inventory.add(deposit);
            this.subTotal += deposit.getPrice();
        }
    }

    /**
     * add a return date to the date arraylist
     *
     */
    //public void addDate() {
    //    dates.add(calculateReturnDate(days));
        //System.out.println(dates.get(0));
    //}

    /**
     * when returning a rented item, this will remove that return date from the
     * list of sorted return dates
     *
     * @param d
     */
    //public void removeDate(Integer d) {
    //    dates.remove(d);
    //}

    /**
     * this will calculate the return date based on a start date constant from
     * when the rental object is initially created
     *
     * @param days
     * @return return date
     */
    public int calculateReturnDate(int days) {
        int returnDate = this.currentDay + days;
        return returnDate;
    }

    /**
     * sort the dates index 0 is the earliest date of all rented items therefore
     * return date is this date, when that date comes, remove it from the list
     * and update return date, remember to charge appropriate fee if date is
     * late
     *
     * @param d
     */
    //public ArrayList<Integer> sortDates(ArrayList<Integer> d) {
    //    Integer earlyDate;
     //   int j = 0;
    //    Collections.sort(d);

//        for(int z = 0; z < dates.size(); z++){
 //           System.out.println(dates.get(z));
  //      }
   //     return d;
    //}

    /**
     * set the days for how long a rental is, will update in transaction each
     * time
     *
     * @param d
     */
    public void setDays(int d) {
        this.days = d;
    }

    //returns number of days being rented
    public int getDays()
    {
        return this.days;
    }

    /**
     * get the return date for return purposes
     *
     * @return returndate
     */
    public int getReturnDate() {
        return calculateReturnDate(this.days);
    }
    public void setCurrentDay(){
        this.currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);    
    }
    /**
     * get the date associated with this cart of items
     *
     * @return startdate
     */
    public Integer getStartDate() {
        return this.currentDay;
    }

    /**
     * adds n items to the cart
     *
     * @param item
     * @param q
     */
    public void addMultItems(Item item, int q) {
        for (int i = 0; i < q; i++) {
            this.add(item);
        }
    }

    //get method for inventory arraylist
    /**
     *
     * @return inventory
     */
    public ArrayList getInventory() {
        return this.inventory;
    }

    /**
     * removeItem removes one copy of the item with the specified index from the
     * cart and updates the database
     *
     * @param itemNumber
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     *
     */
    public void removeItem(int itemNumber) throws InterruptedException, IOException {
        //itemNumber = getItemNum.nextInt();
        //int index - items.lastIndexOf(Inventory.findItem(itemNumber));
        //int index = -1;
        for (int i = 0; i < inventory.size(); i++) {
            if (itemNumber == inventory.get(i).getItemNumber()) {

                System.out.println(inventory.get(i).getName() + " was removed from cart!");
                subTotal -= inventory.get(i).getPrice();
                inventory.remove(i);
                break;
            } else {
                if (i == inventory.size()-1)
                    System.out.println("No such item exists in this Cart.");
            }
        }

    }

    /**
     * clears running subtotal for when cancelSale() is called
     */
    public void clearSubTotal() {
        subTotal = 0;
    }

    /**
     * @return subTotal
     */
    public double getSubtotal() {
        return this.subTotal;
    }

    /**
     * print the rentals
     */
    public void printRentals() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getIsRental()) {
                System.out.println(inventory.get(i).getName() + "   $" + inventory.get(i).getPrice());
            }
        }
    }

    //checks if there are any rentals in this cart
    public boolean containsRentals()
    {
        for(Item i : this.inventory)
        {
            if(i.getIsRental())
                return true;
        }

        return false; 
    }

}
