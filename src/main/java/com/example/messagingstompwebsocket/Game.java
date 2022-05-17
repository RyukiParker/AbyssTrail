package com.example.messagingstompwebsocket;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.ArrayList;

class Game {

  private boolean gameEnd = false;
  UserInput ui;
  TurnOptions to;
  SpawnRate sr;
  //GUI gui;
  int hours;
  ThreadLocalRandom random = ThreadLocalRandom.current();

  // sgsnhoisnhgosdnhogsdg
  Sub sub; // maybe make this a list of subs if adding multiplayer
  int subType = 0;
  String subName;
  //Fort[] forts;
  ArrayList<Fort> forts;

  Game() {
    System.out.println("Welcome to Abyss Trail!");

    ui = new UserInput();
    to = new TurnOptions();
    sr = new SpawnRate();

    forts = new ArrayList<Fort>();
    //gui = new GUI(500, 500);
    
    hours = 0;
  }

   public void setSubType(int type) {
    this.subType = type;
  }

  public void setSubName(String name) {
    this.subName = name;
  }

  public void createSub() {
    sub = new Sub(this.subName, this.subType);
    // for mp, this would just add to the list of subs
    // the player would be assigned a number (that corresponds to list)
  }

  public String generateForts() {
    Fort shallow1 = new Fort("A", random.nextInt(-20, 20), random.nextInt(4000, 6000));
    Fort shallow2 = new Fort("B", random.nextInt(-20, 20), random.nextInt(8000, 10000));
    Fort mid1 = new Fort("C", random.nextInt(-20, 20), random.nextInt(12000, 15000));

    forts.add(shallow1);
    forts.add(shallow2);
    forts.add(mid1);
    
    return ("Generated Forts " + forts.get(0).getName() + ", " + forts.get(1).getName() + ", " + forts.get(2).getName());
  }

  public HashMap<String, Integer> getStats() {
    return sub.getStats();
  }

  public String inputNum(int num) {
    return ui.travelingInput(sub, num);
    //sr.spawn(sub, sub.getYPos());
  }

  public void continueTravel() {
    sub.travel();

    boolean foundFort = false;
    for (Fort fort : forts) {
      foundFort = fort.subInRange(sub);
      if (foundFort == true) {break;}
    } 
    sr.spawn(sub, sub.getYPos());
  }

  public void setNewSpeed(int newSpeed) {
    sub.changeSpeed(newSpeed);
  }

  public void setNewDirection(int newDirection) {
    sub.changeDirection(newDirection);
  }

  public String attack() {
    return sub.attack(sub.target);
  }

  public HashMap<String, Integer> getInv() {
    return sub.getInv();
  }

  public String dock() {
    sub.isDocked = true;
    return "Docked at " + sub.nearestFort.getName();
  }










  // do not run for now
  public void play() {
    System.out.println("game.play() ran");
    // Game setup

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
          //moveOn = ui.travelingInput(sub, 0);
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