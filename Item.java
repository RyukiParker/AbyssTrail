class Item {

  private String name;
  private int buyPrice;

  Item(String name, int buyPrice) {
    this.name = name;
    this.buyPrice = buyPrice;
  }

  // for enemy drops
  /*
  Item(String name) {
    this.name = name;
  }
  */

  public String getName() {
    return this.name;
  }

  public int getBuyPrice() {
    return this.buyPrice;
  }
}