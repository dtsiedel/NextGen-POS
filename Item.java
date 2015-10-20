
/**
 * Item class, an item with price, itemNumber, itemType, and itemName
 */
public class Item {

    private double price;
    private int itemNumber;
    private String itemType;
    private String itemName;
    private int quantity;

    /**
     * Item constructor
     *
     * @param p
     * @param num
     * @param type
     * @param name
     */
    public Item(double p, int num, String type, String name) {
        this.price = p;
        this.itemNumber = num;
        this.itemType = type;
        this.itemName = name;
        this.quantity = 0;
    }

    /**
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return itemNumber
     */
    public int getItemNumber() {
        return itemNumber;
    }

    /**
     * @return itemName
     */
    public String getName() {
        return itemName;
    }

    /**
     * @return itemType
     */
    public String getType() {
        return itemType;
    }

    /**
     * setQuantity assigns number of items
     *
     */
    public void setQuantity() {
        this.quantity += 1;
    }

    /**
     *
     * @return quantity
     */
    public int getQuantity() {
        return this.quantity;
    }
}
