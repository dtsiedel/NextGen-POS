
import java.util.ArrayList;

/**
 * Cart class extends Register holds ArrayList of items
 */
public class Cart extends Register {

    private ArrayList<Item> cart;
    //protected ArrayList<Item> items;
    private double subTotal;

    /**
     * Cart default constructor
     */
    public Cart() {
        this.subTotal = 0.0;
        cart = new ArrayList<>(); //temp
        //items = new ArrayList<>();

    }

    /**
     * adds an item to the cart from the inventory
     *
     * @param item
     */
    //needs to be changed with new update
    public void add(Item item) {
        cart.add(item);
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
            cart.add(item);
        }
    }

    //get method for inventory arraylist
    public ArrayList getInventory() {
        return this.cart;
    }

    /**
     * removeItem finds last possible item in list and removes it and adjusts
     * subTotal as well else prints error notice
     *
     * @param itemNumber
     */
    public void removeItem(int itemNumber) {
        //itemNumber = getItemNum.nextInt();
        //int index - items.lastIndexOf(Inventory.findItem(itemNumber));
        int index = -1;
        if (itemNumber <= (cart.size() - 1)) {
            index = cart.lastIndexOf(cart.get(itemNumber));
            if (index != -1) {
                System.out.println(cart.get(index).getName() + " was removed from cart!");
                subTotal -= cart.get(index).getPrice();
                cart.remove(index);
            }
        } else {
            System.out.println("No such item exists in this Cart.");
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
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getIsRental()) {
                System.out.println(cart.get(i).getName() + "   $" + cart.get(i).getPrice());
            }
        }
    }

}
