
/**
 * A rental has a price, due date, associated receipt
 *
 */
import java.util.Date;

public class Rental {

    private double price;
    private Date returnDate;
    private Receipt receipt;

    public Rental() {

    }

    public Rental(double p, Date d, Receipt r) {
        this.price = p;
        this.returnDate = d;
        this.receipt = r;
    }

    /**
     * get the price of the rental
     *
     * @return price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * get due date of the rental
     *
     * @return returnDate
     */
    public Date getDate() {
        return this.returnDate;
    }

    /**
     * get receipt associated with rental interact with database probably???
     *
     * @return receipt
     */
    public Receipt getReceipt() {
        return this.receipt;
    }

    /**
     * checks the date on a receipt for a rental to ensure it is within time to
     * return otherwise charge fee of $5.00?
     *
     * @param r
     * @return goodCheck (true/false)
     */
    public boolean checkRentalDate(Rental r) {
        boolean goodCheck = false;
        if (r.getDate().after(r.getDate())) {
            goodCheck = false;
        } else if (r.getDate().before(r.getDate())) {
            goodCheck = true;
        }
        return goodCheck;
    }
}
