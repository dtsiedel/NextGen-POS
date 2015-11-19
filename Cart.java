
import java.util.*;
import java.io.*;

/**
 * Cart class extends Register holds ArrayList of items
 */
public class Cart extends Register {

    protected ArrayList<Item> inventory;

    private double subTotal;
    private double cashIn;
    private static final Date startDate = Calendar.getInstance().getTime();
    private int days; //number of days customer wants to rent some item
    private ArrayList<Date> dates; //arraylist of return dates

    /**
     * Cart default constructor
     */
    public Cart() {
        this.subTotal = 0.0;
        inventory = new ArrayList<>(); //temp
        dates = new ArrayList<>();
        //items = new ArrayList<>();

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
    public void addDate() {
        dates.add(calculateReturnDate(this.days));
    }

    /**
     * when returning a rented item, this will remove that return date from the
     * list of sorted return dates
     *
     * @param d
     */
    public void removeDate(Date d) {
        dates.remove(d);
    }

    /**
     * this will calculate the return date based on a start date constant from
     * when the rental object is initially created
     *
     * @param days
     * @return return date
     */
    public static Date calculateReturnDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * sort the dates index 0 is the earliest date of all rented items therefore
     * return date is this date, when that date comes, remove it from the list
     * and update return date, remember to charge appropriate fee if date is
     * late
     *
     * @param d
     */
    public void sortDates(ArrayList<Date> d) {
        Date earlyDate;
        int j = 0;
        for (int i = 1; i < d.size() - 1; i++) {
            earlyDate = d.get(i);
            j = i;
            while (j > 0 && d.get(j - 1).after(earlyDate)) {
                d.set(j, d.get(j - 1));
                j += 1;
            }
        }
    }

    /**
     * set the days for how long a rental is, will update in transaction each
     * time
     *
     * @param d
     */
    public void setDays(int d) {
        this.days = d;
    }

    /**
     * get the return date for return purposes
     *
     * @return returndate
     */
    public Date getReturnDate() {
        sortDates(dates);
        return dates.get(0);
    }

    /**
     * get the date associated with this cart of items
     *
     * @return startdate
     */
    public Date getStartDate() {
        return Cart.startDate;
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
