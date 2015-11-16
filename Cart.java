
import java.util.ArrayList;
import java.io.*;

/**
 * Cart class extends Register holds ArrayList of items
 */
public class Cart extends Register {

    protected ArrayList<Item> inventory;

    //protected ArrayList<Item> items;
    private double subTotal;
    private double cashIn;

    /**
     * Cart default constructor
     */
    public Cart() {
        this.subTotal = 0.0;
        inventory = new ArrayList<>(); //temp
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
    }

    /**
     * adds n items to the cart
     *
     * @param item
     * @param q
     */
    public void addMultItems(Item item, int q) {
        for (int i = 0; i < q; i++) {
            inventory.add(item);
        }
        subTotal += (item.getPrice() * q);
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

    public void printRentals() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getIsRental()) {
                System.out.println(inventory.get(i).getName() + "   $" + inventory.get(i).getPrice());
            }
        }
    }

}
