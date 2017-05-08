package ee.technest.typonaut.controller;


import ee.technest.typonaut.GameDao;
import ee.technest.typonaut.controller.dto.DtoConverter;
import ee.technest.typonaut.controller.dto.objects.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {

    private GameDao gameDao;

    public ResultController(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
    public Result getAllGames(@PathVariable("id") String id) {
        return DtoConverter.getResult(gameDao.findById(id), id);
    }
}
