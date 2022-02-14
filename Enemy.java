class Enemy {

  private String name;
  private int health;
  private int attackDmg;

  Enemy(int type) {
    switch (type) {
      case 1: this.name = "fishh";
              this.health = 20;
              this.attackDmg = 5;
              break;
      case 2: this.name = "big fish";
              this.health = 40;
              this.attackDmg = 15;
              break;
      case 3: this.name = "Shark";
              this.health = 90;
              this.attackDmg = 50;
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
    } else {
      attack(sub);
      System.out.println(this.name + " attacked your sub!");
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