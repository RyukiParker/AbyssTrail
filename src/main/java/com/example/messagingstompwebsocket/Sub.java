package com.example.messagingstompwebsocket;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

class Sub {

  private String name;
  private int health;
  private int maxHealth;
  private int speed;
  private int maxSpeed;
  private int direction;
  private int xPos;
  private int yPos;
  private int maxDepth;
  private int attackDmg;
  private int money;
  private HashMap<Item, Integer> inv = new HashMap<Item, Integer>();
  private Scanner sc;

  public boolean canDock;
  public boolean isDocked;
  public Fort nearestFort;

  public boolean inBattle; 
  public Enemy target;

  public boolean isDead;

  Sub(String name, int subType) {
    this.name = name;
    this.canDock = false;
    this.isDocked = false;
    this.inBattle = false;
    this.isDead = false;
    this.sc = new Scanner(System.in);

    // Create base stats for each subtype
    if (subType == 1) {
      // Attack
      this.health = 75;
      this.maxHealth = 75;
      this.maxSpeed = 100;
      this.attackDmg = 150;
    } else if (subType == 2) {
      // Health
      this.health = 250;
      this.maxHealth = 250;
      this.maxSpeed = 100;
      this.attackDmg = 30;
    } else {
      // Speed
      this.health = 150;
      this.maxHealth = 150;
      this.maxSpeed = 200;
      this.attackDmg = 70;
    }
    this.money = 2000;
    this.maxDepth = 10000;
    this.xPos = 0;
    this.yPos = 0;
  }

  public void takeHit(int dmg) {
    this.health -= dmg;
    checkIfDead();
  }

  private void checkIfDead() {
    if (this.health <= 0) {
      //dead
      this.isDead = true;
      System.out.println("you are dead");
    }
  }

  public String attack(Enemy target) {
    return target.takeHit(attackDmg, this);
  }

  public void clearTarget() {
    this.target = null;
  }

  public void addItem(Item newItem) {
    // iterates through only key names (not values)
    boolean foundItem = false;
    for (Item i : this.inv.keySet()) {
      if (i.getName() == newItem.getName()) {
        foundItem = true;
        this.inv.put(i, this.inv.get(i) + 1);
      }
    }
    // new item
    if (foundItem == false) {
      this.inv.put(newItem, 1);
    }
  }

  public void subtractItem(Item itemToRemove) {
    boolean foundItem = false;
    for (Item i : this.inv.keySet()) {
      if (i.getName() == itemToRemove.getName()) {
        foundItem = true;
        this.inv.put(i, this.inv.get(i) - 1);
      }
    }
    // never has had item
    if (foundItem == false) {
      System.out.println(itemToRemove.getName() + " not found in inventory.");
    }
  }

  public void travel() {
    double diagSpeed = Math.sqrt(2)/2 * this.speed;

    if (this.yPos + this.speed > this.maxDepth) {
      System.out.println("\u001B[41mCRUSH DEPTH\u001B[0m");
      this.health -= (this.yPos - this.maxDepth)/100;
      checkIfDead();
    }

    switch (this.direction) {
      case 0: System.out.println("Sub is stopped");
              break;//return true;
      case 1: System.out.println("Sub is moving up-left");
              if (this.yPos - diagSpeed < 0) {
                System.out.println("Can't fly above surface!!");
                break;//return false;
              } else {
                this.xPos -= diagSpeed;
                this.yPos -= diagSpeed;
                break;//return true;
              }
      case 2: System.out.println("Sub is moving up");
              if (this.yPos - this.speed < 0) {
                System.out.println("Can't fly above surface!!");
                break;//return false;
              } else {
                this.yPos -= this.speed;
                break;//return true;
              }
      case 3: System.out.println("Sub is moving up-right");
              if (this.yPos - diagSpeed < 0) {
                System.out.println("Can't fly above surface!!");
                break;//return false;
              } else {
                this.xPos += diagSpeed;
                this.yPos -= diagSpeed;
                break;//return true;
              }
      case 4: System.out.println("Sub is moving right");
              this.xPos += this.speed;
              break;//return true;
      case 5: System.out.println("Sub is moving down-right");
              this.xPos += diagSpeed;
              this.yPos += diagSpeed;
              break;//return true;
      case 6: System.out.println("Sub is moving down");
              this.yPos += this.speed;
              break;//return true;
      case 7: System.out.println("Sub is moving down-left");
              this.xPos -= diagSpeed;
              this.yPos += diagSpeed;
              break;//return true;
      case 8: System.out.println("Sub is moving left");
              this.xPos -= this.speed;
              break;//return true;
      //default: break;//return true;
    }

    
  }

  public void changeDirection(int newDir) {
    this.direction = newDir;
  }

  public void changeSpeed(int newSpeed) {
    if (newSpeed < 0) {
      this.speed = 0;
    } else if (newSpeed <= this.maxSpeed) {
      this.speed = newSpeed;
    } else {
      this.speed = maxSpeed;
    }
  }

  private void repairSub() {

  }

  public void spendMoney(int amount) {
    this.money -= amount;
  }

  // Gets

  public int getXPos() {
    return this.xPos;
  }

  public int getYPos() {
    return this.yPos;
  }

  public String getName() {
    return this.name;
  }

  public int getSpeed() {
    return this.speed;
  }

  public int getHealth() {
    return this.health;
  }

  public int getMaxHealth() {
    return this.maxHealth;
  }

  public int getDirection() {
    return this.direction;
  }

  public int getMoney() {
    return this.money;
  }

  public HashMap<String, Integer> getInv() {
    System.out.println("Inventory: ");
    for(Item item : this.inv.keySet()) {
      System.out.println("\t" + item.getName() + ": " + this.inv.get(item));
    }

    HashMap<String, Integer> newInv = new HashMap<String, Integer>();
    for (Item i : this.inv.keySet()) {
      newInv.put(i.getName(), this.inv.get(i));
    }
    return newInv;
  }

  public void showStatus() {
    System.out.println(this.name +"'s Sub Status");
    System.out.println("Health: " + this.health + " / " + this.maxHealth);
    System.out.println("Speed: " + this.speed + " / " + this.maxSpeed);
    System.out.println("Attack Damage: " + this.attackDmg);
    System.out.println("Money: $" + this.money);
    System.out.println("X Position: " + this.xPos);
    System.out.println("Depth: " + this.yPos + " / " + this.maxDepth);
  }

  public HashMap<String, Integer> getStats() {
    HashMap<String, Integer> s = new HashMap<String, Integer>();
    s.put("health", this.health);
    s.put("maxHealth", this.maxHealth);
    s.put("speed", this.speed);
    s.put("maxSpeed", this.maxSpeed);
    s.put("atkDmg", this.attackDmg);
    s.put("money", this.money);
    s.put("xpos", this.xPos);
    s.put("depth", this.yPos);
    s.put("maxDepth", this.maxDepth);
    s.put("direction", this.direction);

    int state = 0;
    if (inBattle == true) {
      state = 1;
    } else if (isDocked == true) {
      state = 2;
    }
    s.put("state", state);

    int enemyType = 0;
    if (this.target != null) {
      enemyType = this.target.getType();
    } else {
      enemyType = 0; // 0 is no enemy
    }
    s.put("enemyType", enemyType);
    
    return s;
  }
}