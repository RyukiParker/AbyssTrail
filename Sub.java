class Sub {

  private String name;
  private int health;
  private int speed;
  private int maxSpeed;
  private String destination;
  private int xPos;
  private int yPos;
  private int maxDepth;
  private int attackDmg;
  // Stowage/inventory

  Sub() {
    
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

  private void changeDestination(String newDest) {
    this.destination = newDest;
  }

  private void changeSpeed(int newSpeed) {
    if (newSpeed <= this.maxSpeed) {
      this.speed = newSpeed;
    } else if (newSpeed < 0) {
      this.speed = 0;
    } else {
      this.speed = maxSpeed;
    }
  }

  private void repairSub() {

  }
}