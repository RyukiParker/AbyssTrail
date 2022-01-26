class Fort {

  private int xPos;
  private int yPos;
  private boolean isDocked = false; // maybe unneeded?
  // Available inventory to buy

  Fort(int x, int y) {
    this.xPos = x;
    this.yPos = y;
  }

  public boolean subInRange(Sub sub) {
  if (sub.getXPos() - this.xPos <= 100 && sub.getYPos() - this.yPos <= 100) {
      return true;
    } else {
      return false;
    }
  }
}