package com.example.messagingstompwebsocket;

public class HelloMessage {

  private String name;

  public HelloMessage() {}

  public HelloMessage(String name) {
    this.name = name;
  }

  public String getName() {
    return name + " poooop";
  }

  public void setName(String name) {
    this.name = name;
  }
}