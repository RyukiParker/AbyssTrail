class Fort {

  private String name;
  private int xPos;
  private int yPos;

  Item item1 = new Item("metal");
  private Item[] inventory = {item1};

  Fort(String name, int x, int y) {
    this.name = name;
    this.xPos = x;
    this.yPos = y;
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
    for (int i = 0; i < inventory.length; i++) {
      System.out.println((i+1) + " | " + inventory[i].getName());
    }
  }

  public void buyItem(Sub sub, int id) {
    // add costs and stuff
    sub.addItem(this.inventory[id-1]);
    System.out.println("Bought " + this.inventory[id-1] + "!");
  }

  public String getName() {
    return this.name;
  }
}