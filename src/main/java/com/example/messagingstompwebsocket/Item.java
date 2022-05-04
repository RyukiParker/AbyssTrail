package com.example.messagingstompwebsocket;

class Item {

  private String name;
  private int buyPrice;
  private int sellPrice;

  Item(String name, int buyPrice, int sellPrice) {
    this.name = name;
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
  }

  public String getName() {
    return this.name;
  }

  public int getBuyPrice() {
    return this.buyPrice;
  }

  public int getSellPrice() {
    return this.sellPrice;
  }
}