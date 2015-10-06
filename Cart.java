import java.util.ArrayList.*

private class Cart extends Register{
	ArrayList<Item> items = new ArrayList<Item>();
	double subTotal = 0.0;
	void readIn(){
		if (scanner.hasNextInt()){Item i = Inventory.findItem(scanner.nextInt()); items.add(i); subTotal += i.getPrice();}
		else{System.out.println("Invalid Item Number and/or Item Number Not Recognized");}
	}
	void removeItem(int itemNumber){
		int index = items.lastIndexOf(Inventory.findItem(itemNumber));
		if (index != -1){items.remove(index); subTotal -= index.getPrice();}
		else{System.out.println("No Such Item Exists in this Cart.");}
	}

	double getSubtotal(){return subTotal;}
	
}