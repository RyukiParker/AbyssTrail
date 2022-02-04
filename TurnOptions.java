class TurnOptions {

  // This class will display what options are available for the player (dock at a fort, attack an enemy, change direction, etc.)

  TurnOptions() {

  }

  /* temporarily putting these here for reference
  BLACK	\u001B[30m	BLACK_BACKGROUND	\u001B[40m
  RED	\u001B[31m	RED_BACKGROUND	\u001B[41m
  GREEN	\u001B[32m	GREEN_BACKGROUND	\u001B[42m
  YELLOW	\u001B[33m	YELLOW_BACKGROUND	\u001B[43m
  BLUE	\u001B[34m	BLUE_BACKGROUND	\u001B[44m
  PURPLE	\u001B[35m	PURPLE_BACKGROUND	\u001B[45m
  CYAN	\u001B[36m	CYAN_BACKGROUND	\u001B[46m
  WHITE	\u001B[37m	WHITE_BACKGROUND	\u001B[47m */

  public void showTravelingOptions() {
    System.out.println("--------------------");
    System.out.println("1 | Change speed");
    System.out.println("2 | Change direction");
    System.out.println("3 | View inventory");
    System.out.println("4 | Dock at fort");
    System.out.println("5 | Repair sub <- no work yet");
    System.out.println("6 | CONTINUE traveling");
    System.out.println("7 | See ally creatures <- no work yet");
    // no need for 7?
  }

  public void showBattleOptions() {
    System.out.println("--------------------");
    System.out.println("1 | Attack");
    System.out.println("2 | Attempt to run");
    System.out.println("3 | ????");
    // attack -> CONTINUE
    // attempt to run -> CONTINUE
    // command ally creatures
  }

  public void showDockedOptions() {
    System.out.println("--------------------");
    System.out.println("1 | Buy");
    System.out.println("2 | Sell <- no work yet");
    System.out.println("3 | View inventory");
    System.out.println("4 | Undock from fort");
    System.out.println("5 | Upgrade sub <- no work yet");
    System.out.println("6 | Repair sub <- no work yet");
    // buy or sell 
    // upgrade
    // undock -> CONTINUE
  }
}