package ee.technest.typonaut.modal;


import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface GameRepository extends CrudRepository<Game, Long> {

    @Override
    List<Game> findAll();

    List<Game> findByP1IdOrP2Id(String p1Id, String p2Id);
}
