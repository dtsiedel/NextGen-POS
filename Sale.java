protected class Sale extends Register{
	double total;
	Cart currentCart = new Cart();
	double tax;
	boolean nextItem = true;
	int EndOfCart = -999;
	int RemoveItem = -1;
	while (nextItem){
		currentCart.readIn();
		if (itemNumber.equals(EndOfCart)){break;}
		else if (itemNumber.equals(RemoveItem)){removeItem(itemNumber);}
	}
	tax = getTax(cart);

	getPaymentMethod();
	total = currentCart.getSubtotal() + tax;

	if(registerPay()){
		Receipt receipt = new Receipt(cart, tax, paymentMethod)
		receipt.print();
		receipt.store();
	}
}