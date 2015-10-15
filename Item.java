private class Item{
	double price;
	int itemNumber;
	String itemType;
	String itemName;
	private Item(double p, int num, String type, String name){itemType = type; price = p; itemNumber = num;itemName = name;}
	double getPrice(){return price;}
	int getItemNumber(){return itemNumber;}
	String getName(){return itemName;}
	String getType(){return itemType;}
}