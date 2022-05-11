package com.example.messagingstompwebsocket;

import java.util.concurrent.ThreadLocalRandom;

class SpawnRate {

  SpawnRate() {
    
  }

  public void spawn(Sub sub, int depth) {
    int chance = 0;

    if (depth >= 1000 && depth < 6000) {
      chance = ThreadLocalRandom.current().nextInt(1, 50);
      if (chance >= 1 && chance < 3) {
        // anchovy (2/50 chance)
        startBattle(sub, 1);
      } else if (chance == 3) {
        // squid (1/50 chance)
        startBattle(sub, 2);
      }

    } else if (depth >= 6000 && depth < 10000) {
      chance = ThreadLocalRandom.current().nextInt(1, 50);
      if (chance >= 1 && chance < 3) {
        // squid (2/50 chance)
        startBattle(sub, 2);
      } else if (chance == 3) {
        // shark (1/50 chance)
        startBattle(sub, 3);
      }

    } else if (depth >= 10000 && depth < 30000) {
      startBattle(sub, 10);
    }
  }

  private void startBattle(Sub sub, int enemyType) {
    sub.inBattle = true;
    sub.target = new Enemy(enemyType);
    System.out.println("A " + sub.target.getName() + " approaches!");
  }
}