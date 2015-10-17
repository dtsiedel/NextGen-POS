/**
 * Item class, an item with price, itemNumber, itemType, and itemName
 */
public class Item {

    private double price;
    private int itemNumber;
    private String itemType;
    private String itemName;
    
    /**
     * Item constructor
     * @param p
     * @param num
     * @param type
     * @param name 
     */
    public Item(double p, int num, String type, String name) {
        itemType = type;
        price = p;
        itemNumber = num;
        itemName = name;
    }
    /**
     * @return price
     */
    double getPrice() {
        return price;
    }
    /**
     * @return itemNumber
     */
    int getItemNumber() {
        return itemNumber;
    }
    /**
     * @return itemName
     */
    String getName() {
        return itemName;
    }
    /**
     * @return itemType
     */
    String getType() {
        return itemType;
    }
}