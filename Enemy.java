import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Enemy {

  private String name;
  private int health;
  private int attackDmg;
  private int type;
  private Item[] drops;
  //private ArrayList<Item> drops = new ArrayList<Item>();

  Enemy(int type) {
    this.type = type;

    Item scale = new Item("Scale");
    Item fin = new Item("Fin");
    Item tooth = new Item("Shark Tooth");
    
    switch (this.type) {
      case 1: this.name = "fishh";
              this.health = 20;
              this.attackDmg = 5;
              this.drops = new Item[]{scale, fin};
              break;
      case 2: this.name = "big fish";
              this.health = 40;
              this.attackDmg = 15;
              this.drops = new Item[]{scale, fin};
              break;
      case 3: this.name = "Shark";
              this.health = 90;
              this.attackDmg = 50;
              this.drops = new Item[]{scale, fin, tooth};
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
      dropItem(sub);
    } else {
      attack(sub);
      System.out.println(this.name + " attacked your sub!");
    }
  }

  // items should not drop every time
  private void dropItem(Sub sub) {
    Item droppedItem = this.drops[ThreadLocalRandom.current().nextInt(0, this.drops.length)];
      sub.addItem(droppedItem);
      System.out.println(this.name + " dropped: " + droppedItem.getName());
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