import java.util.concurrent.ThreadLocalRandom;

class SpawnRate {

  SpawnRate() {
    
  }

  public void spawn(Sub sub, int depth) {
    int chance = 0;

    if (depth >= 0 && depth < 6000) {
      chance = ThreadLocalRandom.current().nextInt(1, 50);
      if (chance >= 1 && chance < 3) {
        // fishh (2/50 chance)
        startBattle(sub, 1);
      } else if (chance == 3) {
        // big fish (1/50 chance)
        startBattle(sub, 2);
      }

    } else if (depth >= 6000 && depth < 12000) {
      chance = ThreadLocalRandom.current().nextInt(1, 50);
      if (chance >= 1 && chance < 3) {
        // big fish (2/50 chance)
        startBattle(sub, 2);
      } else if (chance == 3) {
        // shark (1/50 chance)
        startBattle(sub, 3);
      }

    } else if (depth >= 12000 && depth < 30000) {

    }
  }

  private void startBattle(Sub sub, int enemyType) {
    sub.inBattle = true;
    sub.target = new Enemy(enemyType);
    System.out.println("A " + sub.target.getName() + " approaches!");
  }
}