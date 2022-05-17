var stompClient = null;
var ongoing = false;

// 0 = travel, 1 = battle, 2 = docked
var currentState = 0;

$("#options").hide();
$("#speedInput").hide();
$("#submitSpeed").hide();
$("#directionInput").hide();
$("#enemy").hide();
$("#inventory").hide();
$("#sub").hide();
$("#death-screen").hide();

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
        $("#announce").text(attack.body);
        requestStats();
      })

      stompClient.subscribe('/topic/travel', function(travel) {
        //$("#state").text(currentState);
        //console.log("currentState: " + currentState);
                              
        JSON.parse(travel.body, (key, value) => {
          $("#" + key).text(value);
          showCorrectState(key, value);

          // canDock
          if (key == "canDock" && value == 1) {
            $("#announce").text("Dockable fort nearby!")
          }

          // depth backgrounds
          if (key == "depth" && value < 6000) {
            $("main").removeClass().addClass("background depth1");
          } else if (key == "depth" && value >= 6000) {
            $("main").removeClass().addClass("background depth2");
          }

          // enemy encounters
          if (key == "enemyType") {
            switch (value) {
              case 1: console.log("ANCHOVY!!!!");
                      $("#enemy img").attr("src", "sprites/anchovy.png");
                      $("#enemy img").removeClass().addClass("anchovy");
                      $("#announce").text("Anchovy approaches!!");
                      setTimeout(() => { $("#enemy").show(); }, 1);
                      break;
              case 2: console.log("SQUID GAME!!!!");
                      $("#enemy img").attr("src", "sprites/squid.png");
                      $("#enemy img").removeClass().addClass("squid");    
                      $("#announce").text("Squid Game approaches!!");
                      setTimeout(() => { $("#enemy").show(); }, 1);
                      break;
              case 3: console.log("SHARK!!!!");
                      $("#announce").text("Shark approaches!!");
                      $("#enemy img").attr("src", "sprites/shark.png");
                      $("#enemy img").removeClass().addClass("shark");
                      setTimeout(() => { $("#enemy").show(); }, 1);
                      break;
              case 10: console.log("...");
                      $("#announce").text("...");
                      $("#enemy img").attr("src", "https://i1.sndcdn.com/avatars-ylM4tanwBXJZhjWb-X76mWw-t500x500.jpg");
                      $("#enemy img").removeClass().addClass("brandon");
                      setTimeout(() => { $("#enemy").show(); }, 1);
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
  $("form").on('submit', function (e) {
        e.preventDefault();
  });
  
  $( "#connect" ).click(function() { 
    connect(); 
    $("#sub").show();
    $("#settings").show();
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
              $("#speedInput").show();
              $("#submitSpeed").show();
              break;
      case 1: console.log("attacking!!!");
              $("announce").text("Attacking!")
              attack();
              break;
      case 2:
              break;
    }
  });
  
  $( "#btn2" ).click(function() { 
    //buttonClick(2) 
    switch (currentState) {
      case 0: $("#directionInput").show();
              break;
      case 1:
              break;
      case 2:
              break;
    }
  });
  
  $( "#btn3" ).click(function() { 
    //buttonClick(3) 
    switch (currentState) {
      case 0: if ($("#inventory").is(':visible')) {
                $("#inventory").hide();
              } else {
                $("#inventory").show();
                viewInventory();
              }
              break;
      case 1:
              break;
      case 2:
              break;
    }
  });
  
  $( "#btn4" ).click(function() { buttonClick(4) });
  
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