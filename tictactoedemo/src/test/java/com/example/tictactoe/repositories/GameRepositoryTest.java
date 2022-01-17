package com.example.tictactoe.repositories;


import java.util.List;
import java.util.Optional;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TictactoedemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void givenGameRepository_whenSaveAndRetrieveGame_thenOK() {
        Game returnedGame = gameRepository.save(buildGameObject("Test1", "Test2" , Status.READY));
        Assertions.assertNotNull(returnedGame);
        Assertions.assertNotNull(returnedGame.getId());
        Assertions.assertEquals(returnedGame.getStatus(), Status.READY);
    }

    

    @Test
    public void givenGameRepository_whenSaveAndRetrieveGameById_thenOK() {
        Game returnedGame = gameRepository.save(buildGameObject("Test1", "Test2" , Status.READY));
        Optional<Game> gameOptional = gameRepository.findById(returnedGame.getId());
        gameOptional.ifPresent(game -> {
            Assertions.assertNotNull(game);
            Assertions.assertEquals(game.getStatus(), Status.READY);
        });
    }

    

    

    @Test
    public void givenGameRepository_whenSaveMultiGamesAndFindAll_thenOK() {
        gameRepository.save(buildGameObject("Test1", "Test2" , Status.READY));
        gameRepository.save(buildGameObject("Test3", "Test4" , Status.READY));
        List<Game> games = gameRepository.findAll();
        Assertions.assertEquals(2, games.size());
    }

    private Game buildGameObject(String player_o, String player_x, Status status) {
        Game game = new Game();
        game.setPlayer_o(player_o);
        game.setPlayer_x(player_x);
        game.setStatus(status);
        return game;
    }
}
