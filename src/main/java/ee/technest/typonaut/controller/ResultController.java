package ee.technest.typonaut.controller;


import ee.technest.typonaut.GameDao;
import ee.technest.typonaut.modal.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResultController {

    private GameDao gameDao;

    public ResultController(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public List<Game> getAllGames() {
        return gameDao.findAll();
    }
}
