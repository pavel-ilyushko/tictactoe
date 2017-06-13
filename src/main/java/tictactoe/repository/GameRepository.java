package tictactoe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tictactoe.entity.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
}
