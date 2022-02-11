import java.util.concurrent.ThreadLocalRandom;

class SpawnRate {

  SpawnRate() {
    
  }

  public void spawn(Sub sub, int depth) {
    int chance = 0;

    //int enemyType = 1; // 1 for now, change chances for harder enemies the further down you go

    if (depth >= 0 && depth < 6000) {
      chance = ThreadLocalRandom.current().nextInt(1, 4);
      if (chance >= 1 || chance < 3) {
        // common fish enemy (2/3 chance)
        startBattle(sub, 1);
      } else {
        // less common fish enemy (1/3 chance)
        startBattle(sub, 2);
      }

    } else if (depth >= 6000 && depth < 12000) {

    } else if (depth >= 12000 && depth < 30000) {

    }

    /*if (chance == 1 && sub.getSpeed() > 0) {
      System.out.println("ENEMY APPROACHES");
      sub.inBattle = true;
      sub.target = new Enemy(enemyType);
      System.out.println("A " + sub.target.getName() + " approaches!");
    }*/
  }

  private void startBattle(Sub sub, int enemyType) {
    System.out.println("ENEMY APPROACHES");
    sub.inBattle = true;
    sub.target = new Enemy(enemyType);
    System.out.println("A " + sub.target.getName() + " approaches!");
  }
}