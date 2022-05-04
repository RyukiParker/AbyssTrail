class Item {

  private String name;
  private int buyPrice;

  Item(String name, int buyPrice) {
    this.name = name;
    this.buyPrice = buyPrice;
  }

  public String getName() {
    return this.name;
  }

  public int getBuyPrice() {
    return this.buyPrice;
  }
}