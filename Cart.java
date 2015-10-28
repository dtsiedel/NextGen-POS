import java.util.ArrayList;

/**
 * Cart class extends Register holds ArrayList of items
 */
public class Cart extends Register {

    private ArrayList<Item> inventory;  //temp
    protected ArrayList<Item> items;
    private double subTotal;

    /**
     * Cart default constructor
     */
    public Cart() {
        this.subTotal = 0.0;
        inventory = new ArrayList<>(); //temp
        items = new ArrayList<>();
        inventory.add(new Item(false, 1.00, 0, "Pet", "Cat", 10)); //temp
        inventory.add(new Item(false, 1.00, 1, "Pet", "Dog", 10)); //temp
        inventory.add(new Item(true, 1.00, 2, "Stuff", "Bin", 10)); //temp
    }

    /**
     * add(int itemNumber) adds an item to the cart from the inventory
     *
     * @param itemNumber
     */
    public void add(int itemNumber) {
        if (itemNumber > (inventory.size() - 1) || itemNumber < 0) { //check itemNumber is a valid index in temp inv ArrayList
            System.out.println("INVALID INDEX...Try Again");
        } else {
            items.add(inventory.get(itemNumber)); //add the valid item to cart ArrayList
            //items.get(itemNumber).setQuantity(); //keep track of quantity, do later
            System.out.println(inventory.get(itemNumber).getName() + " was added to cart!");
            subTotal += items.get(items.size() - 1).getPrice(); //calculate running subTotal of cart per item
        }
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
        if (itemNumber <= (items.size() - 1)) {
            index = items.lastIndexOf(items.get(itemNumber));
            if (index != -1) {
                System.out.println(items.get(index).getName() + " was removed from cart!");
                subTotal -= items.get(index).getPrice();
                items.remove(index);
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
    public void printRentals(){
        for(int i=0; i<items.size(); i++)
            if(items.get(i).getIsRental())
                System.out.println(items.get(i).getName()+"   $"+items.get(i).getPrice());
    }
    

    public void printRentals() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getIsRental()) {
                System.out.println(items.get(i).getName() + "   $" + items.get(i).getPrice());
            }
        }
    }

}
