import java.util.Scanner;

class UserInput {

  UserInput() {
    
  }

  // This might work, whatever class requests it will get just the text
  public String readString() {
    Scanner sc = new Scanner(System.in);
    return sc.nextLine();
  }

  public int readInt() {
    Scanner sc = new Scanner(System.in);
    return sc.nextInt();
  }

  public int askSubType() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Choose preferred sub type \n1 - Attack: High attack, low health, medium speed\n2 - Health: Low attack, high health, medium speed\n3 - Speed: Medium attack, medium health, High speed\nType: ");
    int subType = sc.nextInt();
    while (subType < 1 || subType > 3) {
      System.out.print("Choose a valid sub type (1, 2, 3): ");
      subType = sc.nextInt();
    }
    return subType;
  }

  public boolean travelingInput(Sub sub) {
    // corresponds to TurnOption traveling options
    Scanner sc = new Scanner(System.in);
    System.out.print("Choose option: ");
    int optNum = sc.nextInt();

    switch (optNum) {
      case 1: clearConsole();
              System.out.println("\nCurrent speed: " + sub.getSpeed());
              System.out.print("Enter new speed: ");
              int newSpeed = sc.nextInt();
              sub.changeSpeed(newSpeed);
              System.out.println("Set sub speed to " + sub.getSpeed());
              return false;
      case 2: clearConsole();
              System.out.println("1 2 3\n8 0 4\n7 6 5");
              System.out.println("Current direction: " + sub.getDirection());
              System.out.print("Enter new direction: ");
              int newDir = sc.nextInt();
              sub.changeDirection(newDir);
              System.out.println("Set sub direction to " + sub.getDirection());
              return false;
      case 3: clearConsole();
              System.out.println("viewing inv...");
              return false;
      case 4: clearConsole();
              if (sub.canDock == true) {
                sub.isDocked = true;
                System.out.println("Docked!");
                return true;
              } else {
                System.out.println("Can't dock: no fort");
                return false;
              }
      case 5: clearConsole();
              if (sub.getHealth() >= sub.getMaxHealth()) {
                System.out.println("Sub is in perfect condition.");
              } else {
                System.out.println("Repairing...");
                // now actually repair it
              }
              return false;
      case 6: clearConsole();
              System.out.println("continuing on...");
              return sub.travel();
      case 7: clearConsole();
              sub.showStatus();
              return false;
    }
    return false;
  }

  public boolean battleInput(Sub sub) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Choose option: ");
    int optNum = sc.nextInt();
    switch (optNum) {
      case 1: clearConsole();
              System.out.println();
              return false;
      case 2: clearConsole();
              System.out.println();
              return false;
      case 3: clearConsole();
              System.out.println();
              return false;
    }
    return false;
  }

  public boolean dockedInput(Sub sub) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Choose option: ");
    int optNum = sc.nextInt();
    switch (optNum) {
      case 1: clearConsole();
              System.out.println();
              return false;
      case 2: clearConsole();
              System.out.println();
              return false;
      case 3: clearConsole();
              System.out.println();
              return false;
      case 4: clearConsole();
              System.out.println("Undocked!");
              sub.isDocked = false;
              return true;
    }
    return false;
  }

  // make others when needed for specific inputs
  // ie readNum, if not a number, re-ask for input
  // OR
  // add methods for checking what the user entered and what it should be
  // checkCommand, checkName, etc.

  private void clearConsole() {
    System.out.print("\033[H\033[2J");
  }
}