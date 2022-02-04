import java.util.ArrayList;
import java.util.Scanner;

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
  private ArrayList<Item> inventory = new ArrayList<Item>();

  public boolean canDock;
  public boolean isDocked;
  public Fort nearestFort;

  public boolean inBattle; 
  public Enemy target;

  Sub(String name, int subType) {
    this.name = name;
    this.canDock = false;
    this.isDocked = false;
    this.inBattle = false;

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
    if (this.health <= 0) {
      //dead
      System.out.println("you are dead");
    }
  }

  public void attack(Enemy target) {
    target.takeHit(attackDmg, this);
  }

  public boolean travel() {
    double diagSpeed = Math.sqrt(2)/2 * this.speed;

    if (this.yPos + this.speed > this.maxDepth) {
      System.out.println("\u001B[41mCRUSH DEPTH\u001B[0m");
      this.health -= (this.yPos - this.maxDepth)/100;
      checkIfDead();
    }

    switch (this.direction) {
      case 0: System.out.println("Sub is stopped");
              return true;
      case 1: System.out.println("Sub is moving up-left");
              if (this.yPos - diagSpeed < 0) {
                System.out.println("Can't fly above surface!!");
                return false;
              } else {
                this.xPos -= diagSpeed;
                this.yPos -= diagSpeed;
                return true;
              }
      case 2: System.out.println("Sub is moving up");
              if (this.yPos - this.speed < 0) {
                System.out.println("Can't fly above surface!!");
                return false;
              } else {
                this.yPos -= this.speed;
                return true;
              }
      case 3: System.out.println("Sub is moving up-right");
              if (this.yPos - diagSpeed < 0) {
                System.out.println("Can't fly above surface!!");
                return false;
              } else {
                this.xPos += diagSpeed;
                this.yPos -= diagSpeed;
                return true;
              }
      case 4: System.out.println("Sub is moving right");
              this.xPos += this.speed;
              return true;
      case 5: System.out.println("Sub is moving down-right");
              this.xPos += diagSpeed;
              this.yPos += diagSpeed;
              return true;
      case 6: System.out.println("Sub is moving down");
              this.yPos += this.speed;
              return true;
      case 7: System.out.println("Sub is moving down-left");
              this.xPos -= diagSpeed;
              this.yPos += diagSpeed;
              return true;
      case 8: System.out.println("Sub is moving left");
              this.xPos -= this.speed;
              return true;
      default: return true;
    }
  }

  private void checkIfDead() {
    if (this.health <= 0) {
      //dead
      System.out.println("you are dead");
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

  public void getInv() {
    System.out.println("Inventory: ");
    for(Item item : this.inventory) {
      System.out.println("\t" + item.getName());
    }
  }

  public void addItem(Item item) {
    this.inventory.add(item);
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
}