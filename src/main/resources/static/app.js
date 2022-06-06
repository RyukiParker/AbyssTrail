var stompClient = null;
var ongoing = false;

// 0 = travel, 1 = battle, 2 = docked
var currentState = 0;
var subCanDock = false;

$("#connect").hide();
$("#options").hide();
$("#speedInput").hide();
$("#submitSpeed").hide();
$("#directionInput").hide();
$("#enemy").hide();
$("#inventory").hide();
$("#sub").hide();
$("#death-screen").hide();
$("#fortItemList").hide();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);


        // check for and use messages from server
        stompClient.subscribe('/topic/subType', function (chooseSubType) {
          console.log(chooseSubType);
        })

        stompClient.subscribe('/topic/subName', function (chooseSubName) {
          $("#statstitle").text(chooseSubName.body + "'s Stats");
        })

        stompClient.subscribe('/topic/createSub', function (createSub) {
          console.log(createSub);
          console.log(JSON.parse(createSub.body));
          JSON.parse(createSub.body, (key, value) => {
            console.log("(key, value) pair : " + key, value);
            $("#" + key).text(value);
          })
        })

        stompClient.subscribe('/topic/stats', function(getStats) {
          JSON.parse(getStats.body, (key, value) => {
            $("#" + key).text(value);
            showCorrectState(key, value);

            if (key == "health" && value <= 0) {
              $("#death-screen").show();
            // dead
            }
          })
        })

        stompClient.subscribe('/topic/inventory', function(viewInventory) {
          JSON.parse(viewInventory.body, (key, value) => {
            console.log(key, value);
            switch (key) {
              case "Scale": $("#inv-scale img").attr("src", "sprites/items/scale.png");
                        $("#scale-tt").text("Scale");
                        $("#scale-amt").text(value);
                        break;
              case "Fin": $("#inv-fin img").attr("src", "sprites/items/fin.png");
                        $("#fin-tt").text("Fin");
                        $("#fin-amt").text(value);
                        break;
              case "Shark Tooth": $("#inv-shark-tooth img").attr("src", "sprites/items/shark-tooth.png");
                        $("#shark-tooth-tt").text("Shark Tooth");
                        $("#shark-tooth-amt").text(value);
                        break;
              case "Repairkit": $("#inv-repairkit img").attr("src", "sprites/items/repairkit.png");
                        $("#repairkit-tt").text("Repairkit");
                        $("#repairkit-amt").text(value);
            }
          })
        })

        stompClient.subscribe('/topic/startGame', function (startGame) {
          displayTravelingOptions();
          console.log("Game initialized");
          console.log(startGame.body);
        })

      stompClient.subscribe('/topic/speed', function (setNewSpeed) {
          JSON.parse(setNewSpeed.body, (key, value) => {
            $("#" + key).text(value);
            showCorrectState(key, value);
          })
        })

      stompClient.subscribe('/topic/direction', function(setNewDirection) {
        JSON.parse(setNewDirection.body, (key, value) => {
          $("#" + key).text(value);
          showCorrectState(key, value);
        })
      })

      stompClient.subscribe('/topic/attack', function(attack) {
        console.log(attack.body);
        announce(attack.body);
        requestStats();
      })

      stompClient.subscribe('/topic/dock', function(dock) {
        console.log(dock.body);
        announce(dock.body);
        requestStats();
      })

      stompClient.subscribe('/topic/undock', function(undock) {
        console.log(undock.body);
        announce(undock.body);
        requestStats();
      })

      stompClient.subscribe('/topic/buyItem', function(buyItem) {
        announce(buyItem.body);
        requestStats();
      })

      stompClient.subscribe('/topic/travel', function(travel) {
        //$("#state").text(currentState);
        //console.log("currentState: " + currentState);

        // depth backgrounds
        var stats = JSON.parse(travel.body);
        if (stats.depth < 6000) {
          if (stats.xpos >= 3000) {
            console.log("shoujld be changing to BIOME4");
            $("main").removeClass().addClass("background biome4");
          } else {
            $("main").removeClass().addClass("background depth1");
          }
        } else if (stats.depth >= 6000 && stats.depth < 12000) {
          $("main").removeClass().addClass("background depth2");
        } else if (stats.depth >= 12000) {
          $("main").removeClass().addClass("background biome3");
        }
        
                              
        JSON.parse(travel.body, (key, value) => {
          $("#" + key).text(value);
          showCorrectState(key, value);

          // enemy encounters
          if (key == "enemyType") {
            $("#announce").show();
            switch (value) {
              case 1: console.log("ANCHOVY!!!!");
                      $("#enemy img").attr("src", "sprites/enemies/anchovy.png");
                      $("#enemy img").removeClass().addClass("anchovy");
                      announce("Anchovy approaches!!");
                      setTimeout(() => { $("#enemy").show(); }, 1);
                      break;
              case 2: console.log("SQUID GAME!!!!");
                      $("#enemy img").attr("src", "sprites/enemies/squid.png");
                      $("#enemy img").removeClass().addClass("squid");    
                      announce("Squid Game approaches!!");
                      setTimeout(() => { $("#enemy").show(); }, 1);
                      break;
              case 3: console.log("SHARK!!!!");
                      announce("Shark approaches!!");
                      $("#enemy img").attr("src", "sprites/enemies/shark.png");
                      $("#enemy img").removeClass().addClass("shark");
                      setTimeout(() => { $("#enemy").show(); }, 1);
                      break;
              case 4: console.log("AMOGUS!!!!");
                      announce("Amogus approaches!!");
                      $("#enemy img").attr("src", "sprites/enemies/amongus.png");
                      $("#enemy img").removeClass().addClass("amogus");
                      setTimeout(() => { $("#enemy").show(); }, 1);
                      break;
              case 10: console.log("...");
                      announce("...");
                      $("#enemy img").attr("src", "https://i1.sndcdn.com/avatars-ylM4tanwBXJZhjWb-X76mWw-t500x500.jpg");
                      $("#enemy img").removeClass().addClass("brandon");
                      setTimeout(() => { $("#enemy").show(); }, 1);
            }
          }

           // canDock
          if (key == "canDock" && value == 1) {
            subCanDock = 1;
            announce("Dockable fort nearby!")
          } else if (key == "canDock" && value == 0) {
            subCanDock = 0;
            if ($("#announce").val() == "Dockable fort nearby!") {
              announce("");
            }
          }

        })
      })
      
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

// functions

function startResponse(){
  console.log("game started");
  console.log("choose sub type");   
}

function showCorrectState(key, value) {
  if (key == "state") {
    switch (value) {
      case 0: displayTravelingOptions();
              currentState = 0;
              $("#enemy").hide();
              $("#sub img").removeClass("battle").addClass("left");
              console.log("traveling!!");
              break;
      case 1: displayBattleOptions();
              currentState = 1;
              $("#sub img").removeClass("left").addClass("battle");
              console.log("battling!!");
              break;
      case 2: displayDockedOptions();
              currentState = 2;
              $("main").removeClass().addClass("background fort");
              console.log("docked!!");
              break;
    }
  }
}

function sendSubTypeAndName() {
  stompClient.send("/app/subType", {}, $("#type").val());
  stompClient.send("/app/subName", {}, $("#name").val());
}

function createSub() {
  stompClient.send("/app/createSub", {}, true);
}

function requestStats() {
  stompClient.send("/app/stats", {}, true);
}

function viewInventory() {
  stompClient.send("/app/inventory", {}, true);
}

function dockAtFort() {
  stompClient.send("/app/dock", {}, true);
}

function undock() {
  stompClient.send("/app/undock", {}, true);
}

function buyItem(id) {
  stompClient.send("/app/buyItem", {}, id);
}

function sendNewSpeed() {
  stompClient.send("/app/speed", {}, $("#speedInput").val());
}

function sendNewDirection(direction) {
  stompClient.send("/app/direction", {}, direction);
}

function travel() {
  stompClient.send("/app/travel", {}, true);
}

function attack() {
  stompClient.send("/app/attack", {}, true);
}

function startGame() {
  stompClient.send("/app/startGame", {}, true)
}

function buttonClick(num) {
  console.log("button (" + num + ") has been clicked");
  stompClient.send("/app/turnInput", {}, num);
}

function announce(input) {
  //$("#announce").removeClass();
  $("#announce").text(input);
  //$("#announce").addClass("ann-anim");
}

function displayTravelingOptions() {
  $( "#btn1" ).text("Change Speed");
  $( "#btn2" ).text("Change Dir");
  $( "#btn3" ).text("View Inv");
  $( "#btn4" ).text("Dock");
  $( "#btn5" ).text("Repair Sub*");
  $( "#btn6" ).text("Continue");
  $( "#btn7" ).text("");
  $( "#btn8" ).text("");
}

function displayBattleOptions() {
  $( "#btn1" ).text("Attack");
  $( "#btn2" ).text("Run*");
  $( "#btn3" ).text("");
  $( "#btn4" ).text("");
  $( "#btn5" ).text("");
  $( "#btn6" ).text("");
  $( "#btn7" ).text("");
  $( "#btn8" ).text("");
}

function displayDockedOptions() {
  $( "#btn1" ).text("Buy");
  $( "#btn2" ).text("Sell*");
  $( "#btn3" ).text("View Inv");
  $( "#btn4" ).text("Undock");
  $( "#btn5" ).text("Craft Items*");
  $( "#btn6" ).text("Upgrade Sub*");
  $( "#btn7" ).text("Repair Sub*");
  $( "#btn8" ).text("");
}

// Buttons on the page calls methods above
$(function () {
  connect(); 
  $("#sub").show();
  $("#settings").show();
  
  $("form").on('submit', function (e) {
        e.preventDefault();
  });
  
  $( "#disconnect" ).click(function() { disconnect(); });
  
  $( "#send" ).click(function() { 
    sendSubTypeAndName(); 
    createSub(); 
  });
  
  $( "#sub" ).on( "click", function() {
    $("#settings").hide();
    if (ongoing == false) {
      startGame();
      $("#options").show();
      ongoing = true;
    }
  });

  $("#submitSpeed").click(function() {
    sendNewSpeed();
    $("#speedInput").hide();
    $("#submitSpeed").hide();
  });

  // 0 = travel, 1 = battle, 2 = docked
  
  $( "#btn1" ).click(function() { 
    //buttonClick(1) 
    switch (currentState) {
      case 0: console.log("changing speed");
              if ($("#speedInput").is(':visible')) {
                $("#speedInput").hide();
                $("#submitSpeed").hide();
              }
              $("#speedInput").show();
              $("#submitSpeed").show();
        
              $("#directionInput").hide();
              $("#inventory").hide();
              $("#fortItemList").hide();
              break;
      case 1: console.log("attacking!!!");
              announce("Attacking!");
              attack();
              break;
      case 2: console.log("available items: ");
              if ($("#fortItemList").is(':visible')) {
                $("#fortItemList").hide();
              } else {
                $("#fortItemList").show();
                
                $("#speedInput").hide();
                $("#submitSpeed").hide();
                $("#directionInput").hide();
                $("#inventory").hide();
              }
              break;
    }
  });
  
  $( "#btn2" ).click(function() { 
    //buttonClick(2) 
    switch (currentState) {
      case 0: if ($("#directionInput").is(':visible')) {
                $("#directionInput").hide();
              } else {
                $("#directionInput").show();
                
                $("#speedInput").hide();
                $("#submitSpeed").hide();
                $("#inventory").hide();
                $("#fortItemList").hide();
              }
              break;
      case 1:
              break;
      case 2:
              break;
    }
  });
  
  $( "#btn3" ).click(function() { 
    //buttonClick(3) 
    if ($("#inventory").is(':visible')) {
      $("#inventory").hide();
    } else {
      $("#inventory").show();
      $("#speedInput").hide();
      $("#submitSpeed").hide();
      $("#directionInput").hide();
      $("#fortItemList").hide();
      viewInventory();
    }
  });
  
  $( "#btn4" ).click(function() { 
    //buttonClick(4) 
    switch (currentState) {
      case 0: if (subCanDock == true) {
                dockAtFort();
                console.log("sub can dock so docking!")
              } else {
                announce("There is no fort nearby to dock at.")
                console.log("There is no fort nearby to dock at.")
              }
              break;
      case 1:
              break;
      case 2: undock();
              break;
    }
  });
  
  $( "#btn5" ).click(function() { buttonClick(5) });
  
  $( "#btn6" ).click(function() { 
    //buttonClick(6) 
    switch (currentState) {
      case 0: travel();
              break;
      case 1:
              break;
      case 2:
              break;
    }
  });
  
  $( "#btn7" ).click(function() { buttonClick(7) });
  
  $( "#btn8" ).click(function() { buttonClick(8) });

  // Fort Items
  $("#fort-1").on( "click", function() {
    // buy repairkit
    buyItem(1);
  });
  $("#fort-2").on( "click", function() { });
  $("#fort-3").on( "click", function() { });
  $("#fort-4").on( "click", function() { });
  $("#fort-5").on( "click", function() { });
  $("#fort-6").on( "click", function() { });
  
  // Directions

  $("#subStop").click(function() {
    sendNewDirection(0);
    $("#directionInput").hide();
  });
  $("#subUpLeft").click(function() {
    sendNewDirection(1);
    $("#directionInput").hide();
  });
  $("#subUp").click(function() {
    sendNewDirection(2);
    $("#directionInput").hide();
  });
  $("#subUpRight").click(function() {
    sendNewDirection(3);
    $("#directionInput").hide();
  });
  $("#subRight").click(function() {
    sendNewDirection(4);
    $("#directionInput").hide();
  });
  $("#subDownRight").click(function() {
    sendNewDirection(5);
    $("#directionInput").hide();
  });
  $("#subDown").click(function() {
    sendNewDirection(6);
    $("#directionInput").hide();
  });
  $("#subDownLeft").click(function() {
    sendNewDirection(7);
    $("#directionInput").hide();
  });
  $("#subLeft").click(function() {
    sendNewDirection(8);
    $("#directionInput").hide();
  });
});