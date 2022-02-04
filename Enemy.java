class Enemy {

  private String name;
  private int health;
  private int attackDmg;

  Enemy(int type) {
    switch (type) {
      case 1: this.name = "Piranha";
              this.health = 20;
              this.attackDmg = 5;
              break;
      case 2: this.name = "Swordfish";
              this.health = 40;
              this.attackDmg = 15;
              break;
      case 3: this.name = "Shark";
              this.health = 90;
              this.attackDmg = 50;
              break;
    }
  }

  public void takeHit(int amt) {
    this.health -= amt;
    if (this.health <= 0) {
      // dead
    }
  }

  public void attack(Sub sub) {
    sub.takeHit(this.attackDmg);
  }

  public String getName() {
    return this.name;
  }
}