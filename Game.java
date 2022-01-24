import java.util.Scanner;

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
    sub.showStats();

    // Main game loop
    while (gameEnd == false) {
      
      if (sub.isDocked == false && sub.inBattle == false) {
        to.showTravelingOptions();
        ui.travelingInput(sub);
      } else if (sub.isDocked == true) {
        to.showDockedOptions();
        ui.dockedInput(sub);
      } else if (sub.inBattle == true) {
        to.showBattleOptions();
        ui.battleInput(sub);
      } else {
        System.out.println("BROKEN, BOTH DOCKED AND BATTLING");
      }


      // Keeping track of how many hours passed
      hours += 3;
      
      gameEnd = true;
    }
    System.out.println("\n\nGame Ended");
  }
}