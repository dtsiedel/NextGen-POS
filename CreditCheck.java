
public class CreditCheck {

    private static CreditCheck instance;

    private CreditCheck() {

    }

    public static synchronized CreditCheck getInstance() {
        if (instance == null) {
            instance = new CreditCheck();
        }
        return instance;
    }

    /**
     * creditCard check method to validate a credit card
     *
     * @param num
     * @return boolean true/false if cc is valid or not
     */
    public boolean creditCardCheck(String num) {
        int sum1 = 0;
        int sum2 = 0;
       
        if(num.length() < 16)
            return false;

        String tVal = new StringBuffer(num).reverse().toString();
        for (int i = 0; i < tVal.length(); i++) {
            int digit = Character.digit(tVal.charAt(i), 10);
            if (i % 2 == 0) { //check odd digits, index 0, 2, 4 etc.
                sum1 += digit;
            } else { //check even digits 1, 3, 5 etc.
                int temp = digit * 2;
                if (temp > 9) {
                    int d1 = temp / 10;
                    int d2 = temp % 10;
                    sum2 += d1 + d2;
                } else {
                    sum2 += temp;
                }
            }
        }
        return ((sum1 + sum2) % 10 == 0);
    }
}
