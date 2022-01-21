import Creatures.EnemyCreature;
class Sub {

  private String name;
  private int health;
  private int speed;
  private int maxSpeed;
  private String direction;
  private int xPos;
  private int yPos;
  private int maxDepth;
  private int attackDmg;
  // Stowage/inventory

  Sub(String name, int subType) {
    this.name = name;

    // Create base stats for each subtype
    if (subType == 1) {

    } else if (subType == 2) {

    } else {
      
    }
  }

  public void takeHit(int dmg) {
    this.health -= dmg;
    if (this.health <= 0) {
      //dead
    }
  }

  private void attackTarget(EnemyCreature target) {
    target.takeHit(attackDmg);
  }

  private void changeDirection(String newDir) {
    this.direction = newDir;
    // convert input into a number in userinput that
    // corresponds to a direction: 1 up, 2 up right, 3 right, etc
    // so this doesn't have to deal with weird inputs
  }

  private void changeSpeed(int newSpeed) {
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
}