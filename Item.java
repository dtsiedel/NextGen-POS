
/**
 * Item class, an item with price, itemNumber, itemType, and itemName
 */
public class Item {

    private double price;
    private int itemNumber;
    private String itemName;
    private int quantity;
    private boolean isRental;

    /**
     * Item constructor
     *
     * @param r
     * @param p
     * @param num
     * @param q
     * @param name
     */
    public Item(boolean r, double p, int num, String name, int q) {
        this.isRental = r;
        this.price = p;
        this.itemNumber = num;
        this.itemName = name;
        this.quantity = q;
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
     * setQuantity assigns number of items
     *
     * @param q
     */
    public void setQuantity(int q) {
        this.quantity = q;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    /*
     * @return isRental
     *
     */
    public boolean getIsRental() {
        return this.isRental;
    }

    /**
     *
     * @return quantity
     */
    public int getQuantity() {
        return this.quantity;
    }

    public boolean equals(Item other)
    {
        return (this.getItemNumber() == other.getItemNumber());
    }
}
