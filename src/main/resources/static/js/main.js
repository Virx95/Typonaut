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
}

function countDown(count) {
    $('#timer').html(count)
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
            console.log(buzzer)
            buzzer.play();
	}
    }
}

function startGame(message) {
    $('#lookingDiv').hide()
	$('#nameInput').hide()
	$('#resultDiv').hide()
    $('#message').html(message)
    $('#gameField').show()
	$('#currentWord').show()
    $('#timerDiv').show()


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
	$('#gameField').hide()
	$('#nameInput').show()
	$('#timerDiv').hide()
	$('#timerDiv').hide()
	$('#currentWord').html("")
}
