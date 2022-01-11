class EnemyCreature {

  private String species;
  private int health;
  private int attackDmg;
  // Buffs, debuffs

  EnemyCreature(String species, int health, int attackDmg) {
    this.species = species;
    this.health = health;
    this.attackDmg = attackDmg;
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
}