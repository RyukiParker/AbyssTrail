import Creatures.EnemyCreature;
class Sub {

  private String name;
  private int health;
  private int speed;
  private int maxSpeed;
  private int direction;
  private int xPos;
  private int yPos;
  private int maxDepth;
  private int attackDmg;

  public boolean isDocked;
  public boolean inBattle; 
  // Stowage/inventory

  Sub(String name, int subType) {
    this.name = name;
    this.isDocked = false;
    this.inBattle = false;

    // Create base stats for each subtype
    if (subType == 1) {
      // Attack
      this.health = 75;
      this.maxSpeed = 100;
      this.attackDmg = 150;
    } else if (subType == 2) {
      // Health
      this.health = 250;
      this.maxSpeed = 100;
      this.attackDmg = 30;
    } else {
      // Speed
      this.health = 150;
      this.maxSpeed = 200;
      this.attackDmg = 70;
    }
    this.maxDepth = 1000;
    this.xPos = 0;
    this.yPos = 0;
  }

  public void takeHit(int dmg) {
    this.health -= dmg;
    if (this.health <= 0) {
      //dead
    }
  }

  public void attackTarget(EnemyCreature target) {
    target.takeHit(attackDmg);
  }

  public void changeDirection(int newDir) {
    this.direction = newDir;
    // convert input into a number in userinput that
    // corresponds to a direction: 1 up, 2 up right, 3 right, etc
    // so this doesn't have to deal with weird inputs
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

  // Gets

  public String getName() {
    return this.name;
  }

  public int getSpeed() {
    return this.speed;
  }

  public void showStats() {
    System.out.println("\n" + this.name +"'s Sub Stats");
    System.out.println("Health: " + this.health);
    System.out.println("Max Speed: " + this.maxSpeed);
    System.out.println("Attack Damage: " + this.attackDmg);
    System.out.println("Max Depth: " + this.maxDepth);
  }
}