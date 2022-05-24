package com.example.messagingstompwebsocket;

import java.util.concurrent.ThreadLocalRandom;

class SpawnRate {

  SpawnRate() {
    
  }

  public void spawn(Sub sub, int depth, int xpos) {
    int chance = ThreadLocalRandom.current().nextInt(1, 101);
    
    if (depth >= 1000 && depth < 6000) {
      
      if (chance < 5) {
        // anchovy (4% chance)
        startBattle(sub, 1);
      } else if (chance == 5 || chance == 6) {
        // squid (2% chance)
        startBattle(sub, 2);
      }

      if (depth >= 1000 && depth < 6000 && xpos > 3000) {
        if (chance < 8) {
          startBattle(sub, 4);
        }
      }

    } else if (depth >= 6000 && depth < 10000) {
      
        if (chance >= 1 && chance < 5) {
          // squid (4% chance)
          startBattle(sub, 2);
        } else if (chance == 5 || chance == 6) {
          // shark (2% chance)
          startBattle(sub, 3);
        }

    } else if (depth >= 12000 && depth < 30000) {
        if (chance < 11) {
          // ... (10% chance)
          startBattle(sub, 10);
        }
    }
  }

  private void startBattle(Sub sub, int enemyType) {
    sub.inBattle = true;
    sub.target = new Enemy(enemyType);
    System.out.println("A " + sub.target.getName() + " approaches!");
  }
}