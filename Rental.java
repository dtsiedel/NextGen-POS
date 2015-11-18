
/**
 * A rental has a price, due date, associated receipt
 *
 */
import java.util.Date;
import java.io.*;

@SuppressWarnings("deprecation")

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
     * makes a rental
     *
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public void makeRental() throws InterruptedException, IOException {
        Return ret = new Return();
        ret.makeReturn();
    }
}
