
import java.util.Scanner;

/**
 * Sale class extends Register, performs Sale transaction
 */
public class Sale extends Register {

    private double total;
    private double tax;
    private boolean nextItem;
    private final int endOfCart;
    private final int removeItem;
    private int input;
    private final int cancelSale;
    Cart currentCart = new Cart();

    /**
     * Sale constructor, takes user input to fill cart with items until user
     * enters -999 to indicate no more items, user enters -1 to remove item
     * previously entered
     */
    public Sale() {
        this.nextItem = true;
        this.endOfCart = -999;
        this.removeItem = -1;
        this.cancelSale = -190;
        this.input = 0;  /*stores itemNum of currentCart.items.get(index).getItemNumber*/

    }

    /**
     * makeSale() begins the sale process of reading in items supposedly
     * presented to cashier at checkout
     */
    public void makeSale() {
        Scanner sale = new Scanner(System.in);
        while (nextItem) {
            System.out.print("Enter selection based on indexed ArrayList-"); //temp
            System.out.print("[OPTIONS: -999 for end of sale, -1 to remove an item, -190 to cancel sale]\n-->");
            if (sale.hasNextInt()) { //check input type
                input = sale.nextInt(); //get option or itemNumber
                if (input == endOfCart) { //input is -999
                    nextItem = false;
                } else if (input == removeItem) { //input is -1
                    if (currentCart.items.isEmpty()) {
                        System.out.println("NO ITEMS TO BE REMOVED");
                        continue;
                    }
                    System.out.print("Enter an item to remove\n-->");
                    currentCart.removeItem(sale.nextInt()); //read in another value to remove that item
                } else if (input == cancelSale) { //input is -190
                    cancelSale();
                } else { //input is none of the options, thus possibly a valid itemNumber to add an item to cart
                    currentCart.add(input);
                }
            } else {
                System.out.println("INVALID INPUT...Try Again");
            }
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
     * 0, assumes cancel sale means end program for now0
     */
    public void cancelSale() {
        /*this should set all elements of ArrayList items to null and set size to 0*/
        System.out.println("Sale was cancelled...CART IS NOW EMPTY!");
        currentCart.items.clear();
        currentCart.clearSubTotal();
        //System.exit(0);
    }

}
