package com.example.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;

@Controller
public class AbyssController {

  Game game = new Game();
  
  // receive message from client and return back to client
  /*
  @MessageMapping("/abyss")
  @SendTo("/topic/meetings")
  public Game greeting(HelloMessage message) throws Exception {
    Thread.sleep(10); // simulated delay

    return new Game("abyss, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }*/

  @MessageMapping("/stats")
  @SendTo("/topic/stats")
  public HashMap<String, Integer> getStats() throws Exception {
    return game.getStats();
  }

  @MessageMapping("/subType")
  @SendTo("/topic/subType")
  public String chooseSubType(String type) throws Exception {
    if (Integer.parseInt(type) > 0 && Integer.parseInt(type) < 4) {
      System.out.println("valid sub type found: " + type);
      game.setSubType(Integer.parseInt(type));
      return ("chosen sub type: " + HtmlUtils.htmlEscape(type));
    } else {
      System.out.println("invalid sub type");
      return "invalid sub type, choose again";
    }
  }

  @MessageMapping("/subName")
  @SendTo("/topic/subName")
  public String chooseSubName(String name) throws Exception {
    game.setSubName(name);
    return name;
  }

  @MessageMapping("/createSub")
  @SendTo("/topic/createSub")
  public HashMap<String, Integer> createSub() {
    game.createSub();
    return game.getStats();
  }

  @MessageMapping("/startGame")
  @SendTo("/topic/startGame")
  public String startGame() {
    return game.generateForts();
  }

  @MessageMapping("/turnInput")
  @SendTo("/topic/turnInput")
  public String turnInput(int num) {
    // use num
    switch (num) {
      case 1: // speed pop up
              break;
      case 2: // direction pop up
              break;
      case 3: // inventory pop up
              break;
      default:
              break;
    }
    return game.inputNum(num);
  }

  @MessageMapping("/speed")
  @SendTo("/topic/speed")
  public HashMap<String, Integer> setNewSpeed(int newSpeed) throws Exception {
    game.setNewSpeed(newSpeed);
    turnInput(1);
    return game.getStats();
  }

  @MessageMapping("/direction")
  @SendTo("/topic/direction")
  public HashMap<String, Integer> setNewDirection(int newDirection) throws Exception {
    game.setNewDirection(newDirection);
    return game.getStats();
  }

  @MessageMapping("/travel")
  @SendTo("/topic/travel")
  public HashMap<String, Integer> travel() throws Exception {
    game.continueTravel();
    return game.getStats();
  }

  @MessageMapping("/attack")
  @SendTo("/topic/attack")
  public String attack() throws Exception {
    return game.attack();
  }

  @MessageMapping("/inventory")
  @SendTo("/topic/inventory")
  public HashMap<String, Integer> viewInventory() throws Exception {
    return game.getInv();
  }

  @MessageMapping("/dock")
  @SendTo("/topic/dock")
  public String dockAtFort() throws Exception {
    return game.dock();
  }

  @MessageMapping("/undock")
  @SendTo("/topic/undock")
  public String undock() throws Exception {
    return game.undock();
  }

  @MessageMapping("/buyItem")
  @SendTo("/topic/buyItem")
  public String buyItem(int id) throws Exception {
    return game.buyItem(id);
  }
  
}





/*

public int travelingInput(int optNum) throws Exception {
  - send to UserInput.java
  switch(optNum) {
    case 1: open speed menu
    case 2: open direction menu
  }
}
*/