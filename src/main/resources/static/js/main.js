Game = {}

Game.connect = (function() {
    Game.socket = new SockJS("/typonaut");

    Game.socket.onopen = function () {
        console.log('Info: WebSocket connection opened.');
        setInterval(function() {
            // Prevent server read timeout.
            var payload = {
                status: "PING"
            }
            Game.socket.send(JSON.stringify(payload));
        }, 5000);
    };

    Game.socket.onclose = function () {
        console.log('Info: WebSocket closed.');
    };

    Game.socket.onmessage = function (message) {
        console.log(message)
        var data = JSON.parse(message.data)
        switch (data.status) {
            case "LOOKING":
                showLookingDiv();
                break;
            case "COUNTDOWN":
                countDown(data.counter);
                break;
            case "PLAYING":
                setWord(data.word);
                break;
            case "STARTING":
                startGame(data.message);
                break;
            case "GAME_OVER":
                gameOver(data.message);
                break;
            case "SHOW_RESULT":
                showResult(data.id);
                break;
            case "REMATCH":
                opponentWantsRematch();
                break;
            case "QUIT":
                opponentLeft();
                break;

        }
    };

});

Game.connect()

function findOpponent() {
    var name = $('#chooseName').val()
    if (name !== "" || name !== null) {
        var payload = {
            name: name,
            status: "LOOKING"
        }
        Game.socket.send(JSON.stringify(payload))
    }
}

function showLookingDiv() {
        $('#lookingDiv').show()
	$('#nameInput').hide()
	var buzzer = $('#buzzer2')[0];
        buzzer.play();
}

function stopAll(e){
    var currentElementId=$(e.currentTarget).attr("id");
    $("audio").each(function(){
        var $this=$(this);
        var elementId=$this.attr("id");
        if(elementId!=currentElementId){
            $this[0].pause();
        }
    });
}

function countDown(count) {
    $('#currentWord').html(count)
}

function setWord(word) {
    $('#currentWord').html(word)
}

function submitCurrentWord() {
    var word = $('#currentWordIn').val()
    if (word !== "" || word !== null) {
        var payload = {
            word: word,
            status: "PLAYING"
        }
        Game.socket.send(JSON.stringify(payload))
    	if ($('#currentWordIn').val() != $('#currentWord').html()) {
	    var buzzer = $('#buzzer2')[0];
            buzzer.play();
	}
    }
}

function startGame(message) {
    $("audio").each(function(){
	 $(this).bind("play",stopAll);
    });
    $('#resultScreen').hide()
    $('#lookingDiv').hide()
	$('#nameInput').hide()
	$('#resultDiv').hide()
    $('#message').html(message)
    $('#gameField').show()
	$('#currentWord').show()
    $('#timerDiv').show()
	var buzzer = $('#buzzer4')[0];
	buzzer.play()


}

function gameOver(message) {
	$('#currentWord').hide()
    $('#resultDiv').show()
    $('#result').html(message)
	$('#currentWordIn').val("")
	if (message == "You won!") {
		var buzzer = $('#buzzer1')[0];
		console.log(buzzer)
		buzzer.play();
	} else {
		var buzzer = $('#buzzer3')[0];
		console.log(buzzer)
		buzzer.play();
	}
	$('#resultMessage').html(message)
    $('#rematchBtn').prop('disabled', false);
	$('#gameField').hide()
	$('#timerDiv').hide()
	$('#currentWord').html("")
}


function showResult(id) {
    $.get("/result/" + id, function(data) {
        console.log(data)
        $('#resultScreen').show()
        var head = "<table class='table'><thead><tr><td>" + data.yourName + " " + data.yourScore + "</td><td></td><td>" + data.opponentName + " " + data.opponentScore + "</td></tr></thead>"
        var bodyStart = "<tbody><tr><td>Your time</td><td>Word</td><td>Opponent time</td></tr>"
        head = head + bodyStart
        var row
        for (var i = 0; i < data.games.length; i++) {
            row = "<tr><td>" + data.games[i].yourTime + "</td><td>" + data.games[i].word + "</td><td>" + data.games[i].opponentTime + "</td></tr>"
            head = head + row
        }
        var closure = "</tbody></table>"
        var table = head + closure;
        $('#tableDiv').html(table)
    });
}

function reset() {
    $('#resultScreen').hide()
    $('#nameInput').show()
    Game.socket.close()
    Game.connect()
}

function tryRematch() {
    var payload = {
        status: "REMATCH"
    }
    Game.socket.send(JSON.stringify(payload))
    $('#resultMessage').html("Looking for rematch")
}

function opponentWantsRematch() {
    $('#resultMessage').html("Opponent wants a rematch")
}

function opponentLeft() {
    $('#resultMessage').html("Opponent left")
    $('#rematchBtn').prop('disabled', true);
}
