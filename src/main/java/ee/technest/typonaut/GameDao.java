package ee.technest.typonaut;

import ee.technest.typonaut.modal.Game;
import ee.technest.typonaut.modal.GameRepository;
import ee.technest.typonaut.modal.Player;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GameDao {

    private GameRepository gameRepository;

    public GameDao(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void save(Player playerOne, Player playerTwo) {
        Game game = new Game();
        game.setP1Id(playerOne.getId());
        game.setP1Name(playerOne.getName());
        game.setP1Time(playerOne.getEndTime() - playerOne.getStartTime());

        game.setP2Id(playerTwo.getId());
        game.setP2Name(playerTwo.getName());
        game.setP2Time(playerTwo.getEndTime() - playerTwo.getStartTime());

        game.setWord(playerOne.getWord());
        gameRepository.save(game);
    }

    public List<Game> findById(String id) {
        return gameRepository.findByP1IdOrP2Id(id, id);
    }
}
