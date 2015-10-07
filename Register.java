private class Register implements TaxCalculator{
	int paymentMethod;
	State currentState;
	double getTax(Cart cart){
		double totalTax = 0.0;
		for(Item item: cart.items){
			totalTax += item.getPrice()*stateTax(currentState, item.getType());
		}
		return totalTax;
	}

	void getPaymentMethod(){paymentMethod = scanner.nextInt();}

	boolean registerPay(int paymentType){
		if(paymentType != -1){return true;}
		return false;
	}

	void removeFromInventory(int itemNumber){
		//Call the Inventory and decrement the stock counter by 1
	}

	void removeFromInventory(int itemNumber, int quantity){
		//Call the Inventory and remove the denoted quantity from the stock counter
	}
}