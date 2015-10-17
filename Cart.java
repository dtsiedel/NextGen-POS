
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cart class extends Register holds ArrayList of items
 */
public class Cart extends Register {

    /**
     * Cart default constructor
     */
    public Cart() {
    }

    ArrayList<Item> items = new ArrayList<Item>();
    double subTotal = 0.0;
    Scanner getItemNum = new Scanner(System.in);

    /**
     * readIn() takes integer input from user to read in single item code adds
     * item to ArrayList items, calculates running subTotal
     */
    void readIn() {
        if (getItemNum.hasNextInt()) {
            Item i = Inventory.findItem(getItemNum.nextInt());
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
        int index = items.lastIndexOf(Inventory.findItem(itemNumber));
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
