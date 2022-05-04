import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

class Game {

  private boolean gameEnd = false;
  UserInput ui;
  TurnOptions to;
  SpawnRate sr;
  //GUI gui;
  int hours;
  ThreadLocalRandom random = ThreadLocalRandom.current();

  Game() {
    System.out.println("Welcome to Abyss Trail!");

    ui = new UserInput();
    to = new TurnOptions();
    sr = new SpawnRate();
    //gui = new GUI(500, 500);
    
    hours = 0;
  }

  public void play() {
    // Game setup
    /*
    gui.loadImage("fish.jpg", 0, -200);
    gui.loadImage("fish.jpg", 300, -100);
    gui.loadImage("fish.jpg", 250, -50);
    gui.setBackground("fish.jpg");
    gui.showScreen();
    */
    
    int subType = ui.askSubType();
    System.out.print("Name your sub: ");
    String subName = ui.readString();

    Sub sub = new Sub(subName, subType);
    sub.showStatus();

    // Forts
    Fort shallow1 = new Fort("A", random.nextInt(-20, 20), random.nextInt(4000, 6000));

    Fort shallow2 = new Fort("B", random.nextInt(-20, 20), random.nextInt(8000, 10000));

    Fort mid1 = new Fort("C", random.nextInt(-20, 20), random.nextInt(12000, 15000));

    Fort[] forts = {shallow1, shallow2, mid1};

    boolean moveOn = false;

    // (maybe) Goal is to craft some item and plant it really deep in the ocean near the center of the planet so you can blow it up 

    // Main game loop
    while (gameEnd == false) {

      while (moveOn == false) {
        System.out.println("--------------------");
        sub.showStatus();
        System.out.println("Current time: " + this.hours);
        
        if (sub.isDocked == false && sub.inBattle == false) {
          to.showTravelingOptions();
          moveOn = ui.travelingInput(sub);
          sr.spawn(sub, sub.getYPos());
        } else if (sub.isDocked == true) {
          to.showDockedOptions();
          moveOn = ui.dockedInput(sub, sub.nearestFort);
        } else if (sub.inBattle == true) {
          System.out.println("--------------------");
          System.out.println(sub.target.getName() + "'s health: " + sub.target.getHealth());
          to.showBattleOptions();
          moveOn = ui.battleInput(sub);
        } else {
          System.out.println("BROKEN, BOTH DOCKED AND BATTLING");
        }

        for (Fort fort : forts) {
          fort.subInRange(sub);
          if (sub.canDock == true) {
            break;
          }
        }   
      }

      hours += 3;
      moveOn = false;
      gameEnd = sub.isDead;
    }
    System.out.println("\n\nGame Ended");
  }
}