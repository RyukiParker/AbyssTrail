class Fort {

  private String name;
  private int xPos;
  private int yPos;
  private boolean isDocked = false; // maybe unneeded?
  // Available inventory to buy

  Fort(String name, int x, int y) {
    this.name = name;
    this.xPos = x;
    this.yPos = y;
  }

  public boolean subInRange(Sub sub) {
  if (Math.abs(sub.getXPos() - this.xPos) <= 150 && Math.abs(sub.getYPos() - this.yPos) <= 150) {
      System.out.println("Sub is in range of Fort " + this.name);
      return true;
    } else {
      return false;
    }
  }
}