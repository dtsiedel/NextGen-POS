
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cart class extends Register holds ArrayList of items
 */
public class Cart extends Register {
    
    private ArrayList<Item> inventory;  //temp
    protected ArrayList<Item> items;
    /**
     * Cart default constructor
     */
    public Cart() {
        inventory = new ArrayList<>();
        items = new ArrayList<>();
        inventory.add(new Item(2.00, 111, "cat", "Pet"));
        inventory.add(new Item(2.00, 222, "dog", "Pet"));
        inventory.add(new Item(2.00, 333, "bin", "Stuff"));
    }
    double subTotal = 0.0;
    Scanner getItemNum = new Scanner(System.in);

    /**
     * readIn() takes integer input from user to read in single item code adds
     * item to ArrayList items, calculates running subTotal
     */
    void readIn() {
        if (getItemNum.hasNextInt()) {
            //Item i = Inventory.findItem(getItemNum.nextInt());
            Item i = inventory.get(getItemNum.nextInt());
            items.add(i);
            subTotal += i.getPrice();
        } else {
            System.out.println("Invalid Item Number and/or Item Number Not Recognized");
        }
    }

    /**
     * removeItem finds last possible item in list and removes it and adjusts
     * subTotal as well else prints error notice
     *
     * @param itemNumber
     */
    void removeItem(int itemNumber) {
        itemNumber = getItemNum.nextInt();
        //int index - items.lastIndexOf(Inventory.findItem(itemNumber));
        int index = items.lastIndexOf(inventory.get(itemNumber));
        if (index != -1) {
            items.remove(index);
            subTotal -= items.get(index).getPrice();
        } else {
            System.out.println("No Such Item Exists in this Cart.");
        }
    }

    /**
     * @return subTotal
     */
    double getSubtotal() {
        return subTotal;
    }

}
