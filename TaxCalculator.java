public class TaxCalculator extends Register{

    double taxPercentage;

    public TaxCalculator (State s){
        this.currentState = s;
        this.taxPercentage = stateTax(currentState) / 100;        
    }

    double stateTax(State state) {
        //Case and Switch with sales tax percentages
        switch (state){
            case ALABAMA: return 4.0;
            case ALASKA: return 0.0;
            case ARIZONA: return 5.6;
            case ARKASAS: return 6.5;
            case CALIFORNIA: return 7.5;
            case COLORODO: return 2.9;
            case CONNECTICUT: return 6.35;
            case DELEWARE: return 0.0;
            case FLORIDA: return 6.0;
            case GEORGIA: return 4.0;
            case HAWAII: return 4.0;
            case IDAHO: return 6.0;
            case ILLINOIS: return 6.25;
            case INDIANA: return 7.0;
            case IOWA: return 6.0;
            case KANSAS: return 6.15;
            case KENTUCKY: return 6.0;
            case LOUISIANA: return 4.0;
            case MAINE: return 5.5;
            case MARYLAND: return 6.0;
            case MASSACHUSETTS: return 6.25;
            case MICHIGAN: return 6.0;
            case MINNESOTA: return 6.875;
            case MISSISSIPPI: return 7.0;
            case MISSOURI: return 4.225;
            case MONTANA: return 0.0;
            case NEBRASKA: return 5.5;
            case NEVADA: return 6.85;
            case NEW_HAMPSHIRE: return 0.0;
            case NEW_JERSEY: return 7.0;
            case NEW_MEXICO: return 5.125;
            case NEW_YORK: return 4.0;
            case NORTH_CAROLINA: return 4.75;
            case NORTH_DAKOTA: return 5.0;
            case OHIO: return 5.75;
            case OKLAHOMA: return 4.5;
            case OREGON: return 0.0;
            case PENNSYLVANIA: return 6.0;
            case RHODE_ISLAND: return 7.0;
            case SOUTH_CAROLINA: return 6.0;
            case SOUTH_DAKOTA: return 4.0;
            case TENNESSEE: return 7.0;
            case TEXAS: return 6.25;
            case UTAH: return 5.95;
            case VERMONT: return 6.0;
            case VIRGNIA: return 5.3;
            case WASHINGTON: return 6.5;
            case WEST_VIRGINIA: return 6.0;
            case WISCONSIN: return 5.0;
            case WYOMING: return 4.0;
            default: System.out.println("Error");
        }
        return 0.0;
    }

    double getTax() {return taxPercentage;}
}
