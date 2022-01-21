import java.util.Scanner;
class Game {

  private boolean gameEnd = false;
  UserInput ui;
  int hours;

  Game() {
    System.out.println("Welcome to Abyss Trail!");

    ui = new UserInput();
    hours = 0;
  }

  public void play() {

    // Game setup
    int subType = ui.askSubType();
    System.out.print("Name your sub: ");
    String subName = ui.readString();
    Sub sub = new Sub(subName, subType);

    System.out.print("The sub's name is: " + sub.getName());

    // Main game loop
    while (gameEnd == false) {
      // testing for now
      

      // Keeping track of how many hours passed
      hours += 3;
      
      gameEnd = true;
    }
    System.out.println("Game Ended");
  }
}