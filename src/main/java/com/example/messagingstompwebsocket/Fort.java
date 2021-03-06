package com.example.messagingstompwebsocket;

import java.util.ArrayList;

class Fort {

  private String name;
  private int xPos;
  private int yPos;

  private ArrayList<Item> inventory = new ArrayList<Item>();

  Fort(String name, int x, int y) {
    this.name = name;
    this.xPos = x;
    this.yPos = y;

    // quantity = 1 but it doesn't matter here because for now, forts will have infinite of each item available to buy
    /*
    this.inventory.add(new Item("Battery", 125, 125));
    this.inventory.add(new Item("Steel", 200, 200));
    this.inventory.add(new Item("Medkit", 500, 500));
    this.inventory.add(new Item("Toolkit", 1250, 1250));
    */
    this.inventory.add(new Item("Repairkit", 500, 500));
  }

  public boolean subInRange(Sub sub) {
    if (Math.abs(sub.getXPos() - this.xPos) <= 150 && Math.abs(sub.getYPos() - this.yPos) <= 150) {
      System.out.println("Sub is in range of Fort " + this.name + "\nFort is at (" + this.xPos + ", " + this.yPos + ")");
      sub.nearestFort = this;
      sub.canDock = true;
      System.out.println("sub.canDock = " + sub.canDock);
      return true;
    } else {
      sub.nearestFort = null;
      sub.canDock = false;
      return false;
    }
  } 

  public void getBuyList() {
    System.out.println("Goods to buy: \n0 | Go back");
    for (int i = 0; i < inventory.size(); i++) {
      System.out.println((i+1) + " | " + this.inventory.get(i).getName() + ", $" + this.inventory.get(i).getBuyPrice());
    }
  }

  public String buyItem(Sub sub, int id) {
    // check if player can buy and subtract money
    Item item = this.inventory.get(id-1);
    if (sub.getMoney() >= item.getBuyPrice()) {
      sub.addItem(item);
      sub.spendMoney(item.getBuyPrice());
      System.out.println("Bought " + item.getName() + "!");
      return "Bought " + item.getName() + "!";
    } else {
      System.out.println("Not enough money.");
      return "Not enough money!";
    } 
  }

  public String getName() {
    return this.name;
  }
}