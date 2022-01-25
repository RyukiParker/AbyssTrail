class TurnOptions {

  // This class will display what options are available for the player (dock at a fort, attack an enemy, change direction, etc.)

  TurnOptions() {

  }

  public void showTravelingOptions() {
    System.out.println("\n--------------------");
    System.out.println("1 | Change speed");
    System.out.println("2 | Change direction");
    System.out.println("3 | View inventory");
    System.out.println("4 | Dock at fort");
    System.out.println("5 | Repair sub");
    System.out.println("6 | CONTINUE traveling");
    System.out.println("7 | See sub status");
  }

  public void showBattleOptions() {
    System.out.println();
    System.out.println();
    System.out.println();
    // attack -> CONTINUE
    // attempt to run -> CONTINUE
    // command ally creatures
  }

  public void showDockedOptions() {
    System.out.println();
    System.out.println();
    System.out.println();
    // buy or sell 
    // upgrade
    // undock -> CONTINUE
  }
}