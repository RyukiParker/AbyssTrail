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

  // make others when needed for specific inputs
  // ie readNum, if not a number, re-ask for input
  // OR
  // add methods for checking what the user entered and what it should be
  // checkCommand, checkName, etc.
}