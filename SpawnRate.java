import java.util.concurrent.ThreadLocalRandom;

class SpawnRate {

  SpawnRate() {
    
  }

  public void spawn(Sub sub, int depth) {
    int chance = ThreadLocalRandom.current().nextInt(1, 10);

    int enemyType = 1; // 1 for now, change chances for harder enemies the further down you go

    if (chance == 1 && sub.getSpeed() > 0) {
      System.out.println("ENEMY APPROACHES");
      sub.inBattle = true;
      sub.target = new Enemy(enemyType);
      System.out.println("A " + sub.target.getName() + " approaches!");
    }
  }
}