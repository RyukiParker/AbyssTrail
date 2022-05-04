import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Enemy {

  private String name;
  private int health;
  private int attackDmg;
  private int type;
  private Item[] drops;
  private int dropChance;
  ThreadLocalRandom random = ThreadLocalRandom.current();

  Enemy(int type) {
    this.type = type;

    // name, buyprice, quantity
    Item scale = new Item("Scale", 1);
    Item fin = new Item("Fin", 1);
    Item tooth = new Item("Shark Tooth", 1);
    
    switch (this.type) {
      case 1: this.name = "fishh";
              this.health = 20;
              this.attackDmg = 5;
              this.drops = new Item[]{scale, fin};
              this.dropChance = 30;
              break;
      case 2: this.name = "big fish";
              this.health = 40;
              this.attackDmg = 15;
              this.drops = new Item[]{scale, fin};
              this.dropChance = 30;
              break;
      case 3: this.name = "Shark";
              this.health = 90;
              this.attackDmg = 50;
              this.drops = new Item[]{scale, fin, tooth};
              this.dropChance = 20;
              break;
      case 10: this.name = "???";
              this.health = 1000;
              this.attackDmg = 400;
    }
  }

  public void takeHit(int amt, Sub sub) {
    this.health -= amt;
    if (this.health <= 0) {
      // dead
      sub.inBattle = false;
      System.out.println(this.name + " died.");
      //drop item (chance)
      dropItem(sub, this.dropChance);
    } else {
      attack(sub);
      System.out.println(this.name + " attacked your sub!");
    }
  }

  private void dropItem(Sub sub, int dropChance) {
    if (random.nextInt(0, 101) <= dropChance) {
      // pick random number for index of possible drops
      Item droppedItem = this.drops[random.nextInt(0, this.drops.length)];
      sub.addItem(droppedItem);
      System.out.println(this.name + " dropped: " + droppedItem.getName());
    } else {
      System.out.println(this.name + " didn't drop anything");
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
}