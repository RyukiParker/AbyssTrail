class Fort {

  private String name;
  private int xPos;
  private int yPos;

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

  public void getName() {
    return this.name;
  }
}