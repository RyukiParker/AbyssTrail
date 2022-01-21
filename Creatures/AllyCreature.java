package Creatures;
public class AllyCreature {

  private String species;
  private String name;
  private int health;
  private int attackDmg;
  // Buffs, debuffs

  AllyCreature(String species, int health, int attackDmg) {
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

  private void setName(String name) {
    this.name = name;
  }
}