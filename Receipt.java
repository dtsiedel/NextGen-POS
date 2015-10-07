private class Receipt{
	Cart cart;
	double tax;
	int payMethod;  //0: Cash, 1: Credit Card, 2:Check

	private Receipt(Cart c, double t, int pm){cart=c; tax=t; payMethod=pm;}

	void print(){
		DecimalFormat df = new DecimalFormat("0.00");
		for (Item item: cart.items){
			System.out.println(item.getName()+"\t\t$"+df.format(item.getPrice()));
		}
		System.out.println("\nOrder Total:\t$" +df.format(cart.getSubtotal()+tax));
		System.out.println("\tOrder Subtotal:\t$"+df.format(cart.getSubtotal()));
		System.out.println("\tTotal Tax:\t$"+df.format(tax));
	}

	void store(){
		//Code To Store Receipt in receipt object database
	}
}