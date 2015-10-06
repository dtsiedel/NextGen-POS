private class Receipt{
	Cart cart;
	int tax;
	int payMethod;  //

	private Receipt(Cart c, int t, int pm){cart=c; tax=t; payMethod=pm;}
}