package ee.technest.typonaut.controller.dto;


import ee.technest.typonaut.controller.dto.objects.Line;
import ee.technest.typonaut.controller.dto.objects.Result;
import ee.technest.typonaut.modal.Game;
import ee.technest.typonaut.modal.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DtoConverter {

    public static Result getResult(List<Game> games, String id) {
        Result result = new Result();
        if (games.get(0).getP1Id().equals(id)) {
            result.setYourName(games.get(0).getP1Name());
            result.setOpponentName(games.get(0).getP2Name());
        } else {
            result.setYourName(games.get(0).getP2Name());
            result.setOpponentName(games.get(0).getP1Name());
        }

        List<Line> lines = new ArrayList<>();
        long yourWins = 0;
        long opponentWins = 0;
        for (Game game : games) {
            if (game.getP1Id().equals(id)) {
                if (game.getP1Time() < game.getP2Time()) {
                    yourWins++;
                } else {
                    opponentWins++;
                }
                lines.add(getLine(game, game.getP1Time(), game.getP2Time()));
            } else {
                if (game.getP2Time() < game.getP1Time()) {
                    yourWins++;
                } else {
                    opponentWins++;
                }
                lines.add(getLine(game, game.getP2Time(), game.getP1Time()));
            }
        }
        result.setGames(lines);
        result.setYourScore(yourWins);
        result.setOpponentScore(opponentWins);

        return result;
    }

    public static Line getLine(Game game, long yourTime, long opponentTime) {
        Line line = new Line();
        line.setWord(game.getWord());
        line.setYourTime(millisToSeconds(yourTime));
        line.setOpponentTime(millisToSeconds(opponentTime));
        line.setStatus(yourTime < opponentTime ? Status.WON : Status.LOST);
        return line;
    }

    private static String millisToSeconds(long millis) {
        return ( (float) millis / 1000) + " sec";
    }
}
