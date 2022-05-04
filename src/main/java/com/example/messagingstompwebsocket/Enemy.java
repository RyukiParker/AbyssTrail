package com.example.messagingstompwebsocket;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Enemy {

  private String name;
  private int health;
  private int attackDmg;
  private int type; //id
  private Item[] drops;
  private int dropChance;
  ThreadLocalRandom random = ThreadLocalRandom.current();

  Enemy(int type) {
    this.type = type;

    // name, buyprice, quantity
    Item scale = new Item("Scale", 1, 30);
    Item fin = new Item("Fin", 1, 30);
    Item tooth = new Item("Shark Tooth", 1, 100);
    
    switch (this.type) {
      case 1: this.name = "Anchovy";
              this.health = 20;
              this.attackDmg = 5;
              this.drops = new Item[]{scale, fin};
              this.dropChance = 30;
              break;
      case 2: this.name = "Squid";
              this.health = 40;
              this.attackDmg = 15;
              this.drops = new Item[]{fin};
              this.dropChance = 30;
              break;
      case 3: this.name = "Shark";
              this.health = 90;
              this.attackDmg = 50;
              this.drops = new Item[]{scale, fin, tooth};
              this.dropChance = 90;
              break;
      case 10: this.name = "Brandon";
              this.health = 1000;
              this.attackDmg = 400;
    }
  }

  public String takeHit(int amt, Sub sub) {
    this.health -= amt;
    if (this.health <= 0) {
      // dead
      sub.inBattle = false;
      System.out.println(this.name + " died.");
      sub.clearTarget();
      //dropItem(sub, this.dropChance);
      return this.name + " died. " + dropItem(sub, this.dropChance);
    } else {
      attack(sub);
      System.out.println(this.name + " attacked your sub!");
      return this.name + " attacked your sub!";
    }
  }

  private String dropItem(Sub sub, int dropChance) {
    if (random.nextInt(0, 101) <= dropChance) {
      // pick random number for index of possible drops
      Item droppedItem = this.drops[random.nextInt(0, this.drops.length)];
      sub.addItem(droppedItem);
      System.out.println(this.name + " dropped: " + droppedItem.getName());
      return "It dropped a " + droppedItem.getName() + "!";
    } else {
      System.out.println(this.name + " didn't drop anything");
      return "It didn't drop anything.";
    }
  }

  private void attack(Sub sub) {
    sub.takeHit(this.attackDmg);
  }

  public String getName() {
    return this.name;
  }

  public int getHealth() {
    return this.health;
  }

  public int getType() {
    return this.type;
  }
}