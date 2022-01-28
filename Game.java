import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

class Game {

  private boolean gameEnd = false;
  UserInput ui;
  TurnOptions to;
  int hours;

  Game() {
    System.out.println("Welcome to Abyss Trail!");

    ui = new UserInput();
    to = new TurnOptions();
    hours = 0;
  }

  public void play() {

    // Game setup
    int subType = ui.askSubType();
    System.out.print("Name your sub: ");
    String subName = ui.readString();

    Sub sub = new Sub(subName, subType);
    sub.showStatus();

    // Forts
    Fort knox = new Fort("Knox", ThreadLocalRandom.current().nextInt(-20, 20), ThreadLocalRandom.current().nextInt(4000, 6000));

    Fort aaaaa = new Fort("aaaaa", ThreadLocalRandom.current().nextInt(-20, 20), ThreadLocalRandom.current().nextInt(8000, 10000));

    Fort[] forts = {knox, aaaaa};

    boolean moveOn = false;

    // Main game loop
    while (gameEnd == false) {

      while (moveOn == false) {
        System.out.println("Current time: " + this.hours);
        if (sub.isDocked == false && sub.inBattle == false) {
          to.showTravelingOptions();
          moveOn = ui.travelingInput(sub);
        } else if (sub.isDocked == true) {
          to.showDockedOptions();
          moveOn = ui.dockedInput(sub);
        } else if (sub.inBattle == true) {
          to.showBattleOptions();
          moveOn = ui.battleInput(sub);
        } else {
          System.out.println("BROKEN, BOTH DOCKED AND BATTLING");
        }

        for (Fort fort : forts) {
          sub.canDock = fort.subInRange(sub); 
        }    

        //System.out.println("--------------------\nX Position: " + sub.getXPos());
        System.out.println("--------------------");
        sub.showStatus();
        //System.out.println("Depth: " + sub.getYPos());
      }


      // Keeping track of how many hours passed
      hours += 3;
      moveOn = false;
      //gameEnd = true;
    }
    System.out.println("\n\nGame Ended");
  }
}