package com.example.tictactoe.repositories;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.tictactoe.TictactoedemoApplication;
import com.example.tictactoe.constants.Status;
import com.example.tictactoe.entities.Game;
import com.example.tictactoe.entities.Move;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TictactoedemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MoveRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MoveRepository moveRepository;

    @Test
    public void givenMoveRepository_whenSaveAndRetrieveMove_thenOK() {
        Game returnedGame = gameRepository.save(buildGameObject("test1","test2", Status.READY));
        Move returnedMove = moveRepository.save(buildMoveObject("1","2", returnedGame));

        Assertions.assertNotNull(returnedMove);
        Assertions.assertNotNull(returnedMove.getId());
        Assertions.assertEquals(returnedMove.getHorizontalAxis(), "1");
        Assertions.assertEquals(returnedMove.getVerticalAxis(), "2");
    }

    

    @Test
    public void givenMoveRepository_whenSaveAndRetrieveMoveById_thenOK() {
        Game returnedGame = gameRepository.save(buildGameObject("test1","test2", Status.READY));
        Move returnedMove = moveRepository.save(buildMoveObject("1","2", returnedGame));
        Optional<Move> moveOptional = moveRepository.findById(returnedMove.getId());
        moveOptional.ifPresent(move -> {
            Assertions.assertNotNull(move);
            Assertions.assertEquals(move.getHorizontalAxis(), "1");
            Assertions.assertEquals(move.getVerticalAxis(), "2");
        });
    }

    @Test
    public void givenGameRepository_whenSaveMultiMovesAndFindAllByGameId_thenOK() {
        Game returnedGame = gameRepository.save(buildGameObject("test1","test2", Status.READY));
        moveRepository.save(buildMoveObject("1","2", returnedGame));
        moveRepository.save(buildMoveObject("1","3", returnedGame));
        Set<Move> moves = moveRepository.findAllByGameId(returnedGame.getId());
        Assertions.assertEquals(2, moves.size());
    }

    private Game buildGameObject(String player_x, String player_o, Status status) {
        Game game = new Game();
        game.setPlayer_o(player_o);
        game.setPlayer_x(player_x);
        game.setStatus(status);
        return game;
    }

    private Move buildMoveObject(String horizontalAxis, String verticalAxis, Game game) {
       Move move = new Move();
       move.setHorizontalAxis(horizontalAxis);
       move.setVerticalAxis(verticalAxis);
       move.setGame(game);
       return move;
    }
}
