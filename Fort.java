import java.util.ArrayList;

class Fort {

  private String name;
  private int xPos;
  private int yPos;

  // Base items at every fort
  Item itemSteel = new Item("Steel", 200);
  Item itemMedkit = new Item("Medkit", 500);
  Item itemToolkit = new Item("Toolkit", 1250);
  private ArrayList<Item> inventory = new ArrayList<Item>();

  Fort(String name, int x, int y) {
    this.name = name;
    this.xPos = x;
    this.yPos = y;

    this.inventory.add(itemSteel);
    this.inventory.add(itemMedkit);
    this.inventory.add(itemToolkit);
  }

  public void subInRange(Sub sub) {
    if (Math.abs(sub.getXPos() - this.xPos) <= 150 && Math.abs(sub.getYPos() - this.yPos) <= 150) {
      System.out.println("Sub is in range of Fort " + this.name + "\nFort is at (" + this.xPos + ", " + this.yPos + ")");
      sub.nearestFort = this;
      sub.canDock = true;
    } else {
      sub.nearestFort = null;
      sub.canDock = false;
    }
  } 

  public void getBuyList() {
    System.out.println("Goods to buy: \n0 | Go back");
    for (int i = 0; i < inventory.size(); i++) {
      System.out.println((i+1) + " | " + this.inventory.get(i).getName() + ", $" + this.inventory.get(i).getBuyPrice());
    }
  }

  public void buyItem(Sub sub, int id) {
    // check if player can buy and subtract money
    Item item = this.inventory.get(id-1);
    if (sub.getMoney() >= item.getBuyPrice()) {
      sub.addItem(item);
      sub.spendMoney(item.getBuyPrice());
      System.out.println("Bought " + item.getName() + "!");
    } else {
      System.out.println("Not enough money.");
    } 
  }

  public String getName() {
    return this.name;
  }
}