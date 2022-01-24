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

  // experimenting, this might be horrible

  public void travelingInput(Sub sub) {
    // corresponds to TurnOption traveling options
    Scanner sc = new Scanner(System.in);
    System.out.print("Choose option: ");
    int optNum = sc.nextInt();

    switch (optNum) {
      case 1: System.out.println("\nCurrent speed: " + sub.getSpeed());
              System.out.print("Enter new speed: ");
              int newSpeed = sc.nextInt();
              sub.changeSpeed(newSpeed);
              System.out.println("Set sub speed to " + sub.getSpeed());
              break;
      case 2: System.out.println("changing dir...");
              break;
      case 3: System.out.println("viewing inv...");
              break;
      case 4: System.out.println("docking at fort...");
              break;
      case 5: System.out.println("repairing sub...");
              break;
      case 6: System.out.println("continuing on...");
              break;
      case 7: sub.showStats();
              break;
    }
  }

  public void battleInput(Sub sub) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Choose option: ");
    int optNum = sc.nextInt();
    switch (optNum) {
      case 1: System.out.println();
              break;
      case 2: System.out.println();
              break;
      case 3: System.out.println();
              break;
    }
  }

  public void dockedInput(Sub sub) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Choose option: ");
    int optNum = sc.nextInt();
    switch (optNum) {
      case 1: System.out.println();
              break;
      case 2: System.out.println();
              break;
      case 3: System.out.println();
              break;
    }
  }

  // make others when needed for specific inputs
  // ie readNum, if not a number, re-ask for input
  // OR
  // add methods for checking what the user entered and what it should be
  // checkCommand, checkName, etc.
}