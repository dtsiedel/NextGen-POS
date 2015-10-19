
/**
 * Sale class extends Register, performs Sale transaction
 */
public class Sale extends Register {

    private double total;
    private double tax;
    private boolean nextItem;
    private final int endOfCart;
    private final int removeItem;
    Cart currentCart = new Cart();

    /**
     * Sale constructor, takes user input to fill cart with items until user
     * enters -999 to indicate no more items, user enters -1 to remove item
     * previously entered
     */
    public Sale() {
        nextItem = true;
        endOfCart = -999;
        removeItem = -1;
        int index = 0;  //indexing for ArrayList of items
        int itemNum;  /*stores itemNum of currentCart.items.get(index).getItemNumber*/

        while (nextItem) {
            currentCart.readIn();  //fills currentCart with user input values
            itemNum = currentCart.items.get(index).getItemNumber();
            if (itemNum == endOfCart) {
                nextItem = false;
            } else if (itemNum == removeItem) {
                currentCart.removeItem(itemNum);
            }
            index++;
        }
        tax = getTax(currentCart);

        int pt = getPaymentType();
        total = currentCart.getSubtotal() + tax;

        if (registerPay(pt)) {
            Receipt receipt = new Receipt(currentCart, tax, pt);
            receipt.print();
            receipt.store();
        }
    }

    /**
     * cancelSale(), set all elements of ArrayList items to null and set size to
     * 0
     */
    void cancelSale() {
        /*this should set all elements of ArrayList items to null and set size to 0*/
        currentCart.items.clear();
    }

}
