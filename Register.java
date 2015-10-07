private class Register implements TaxCalculator{
	int paymentMethod;
	double State currentState;
	double getTax(Cart cart){
		double totalTax = 0.0;
		for(Item item: cart.items){
			totalTax += item.getPrice()*stateTax(currentState, item.getType());
		}
		return totalTax;
	}

	void getPaymentMethod(){paymentMethod = scanner.nextInt();}

	boolean registerPay(int paymentType){
		if(paymentType != 2){return true;}
		return false;
	}
}