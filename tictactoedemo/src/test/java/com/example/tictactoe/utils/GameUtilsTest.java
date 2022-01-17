package com.example.tictactoe.utils;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tictactoe.configurations.CommonConfig;
import com.example.tictactoe.constants.Player;
import com.example.tictactoe.dtos.MovePostDto;
import com.example.tictactoe.entities.Game;
import com.example.tictactoe.entities.Move;

@ExtendWith(MockitoExtension.class)
public class GameUtilsTest {

    List<String[]> criteriaList = new CommonConfig().getCriteriaList();

    @InjectMocks
    GameUtils gameUtils = new GameUtils(criteriaList);

    @Test
    public void givenGameUtils_whenHorizontalAxisIsLessThan3AndVerticalAxisIsLessThan3_thenReturnTrue() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setHorizontalAxis(2);
        movePostDto.setVerticalAxis(2);
        Assertions.assertTrue(gameUtils.isMovePayloadValid(movePostDto));
    }

    @Test
    public void givenGameUtils_whenHorizontalAxisIsLargerThan3AndVerticalAxisIsLessThan3_thenReturnFalse() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setHorizontalAxis(4);
        movePostDto.setVerticalAxis(2);
        Assertions.assertFalse(gameUtils.isMovePayloadValid(movePostDto));
    }

    @Test
    public void givenGameUtils_whenHorizontalAxisIsLessThan3AndVerticalAxisIsLargerThan3_thenReturnFalse() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setHorizontalAxis(2);
        movePostDto.setVerticalAxis(4);
        Assertions.assertFalse(gameUtils.isMovePayloadValid(movePostDto));
    }

    @Test
    public void givenGameUtils_whenPlayerSameAsLastPlayer_thenPlayerIsInvalid() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setPlayer(Player.PLAYER_X);
        ;
        Game game = new Game();
        game.setLastPlayer(Player.PLAYER_X);
        Assertions.assertFalse(gameUtils.isPlayerValid(movePostDto, game));
    }

    @Test
    public void givenGameUtils_whenPlayerDifferentFromLastPlayer_thenPlayerIsValid() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setPlayer(Player.PLAYER_X);
        ;
        Game game = new Game();
        game.setLastPlayer(Player.PLAYER_O);
        Assertions.assertTrue(gameUtils.isPlayerValid(movePostDto, game));
    }

    @Test
    public void givenGameUtils_whenAxisExistInMovesListOfGame_thenDuplicatedAxis() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setHorizontalAxis(2);
        movePostDto.setVerticalAxis(2);
        Set<Move> moves = new HashSet<>();
        Move move = new Move();
        move.setHorizontalAxis("2");
        move.setVerticalAxis("2");
        moves.add(move);
        Game game = new Game();
        game.setMoves(moves);
        Assertions.assertTrue(gameUtils.isDuplicatedAxis(movePostDto, game));
    }

    @Test
    public void givenGameUtils_whenAxisExistInMovesListOfGame_thenNotDuplicatedAxis() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setHorizontalAxis(1);
        movePostDto.setVerticalAxis(2);
        Set<Move> moves = new HashSet<>();
        Move move = new Move();
        move.setHorizontalAxis("2");
        move.setVerticalAxis("2");
        moves.add(move);
        Game game = new Game();
        game.setMoves(moves);
        Assertions.assertFalse(gameUtils.isDuplicatedAxis(movePostDto, game));
    }

    @Test
    public void givenGameUtils_whenMovesMeetCriteriaList_thenWinnerComes() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setHorizontalAxis(1);
        movePostDto.setVerticalAxis(2);
        movePostDto.setPlayer(Player.PLAYER_X);

        Set<Move> moves = new HashSet<>();
        Move move1 = new Move();
        move1.setHorizontalAxis("2");
        move1.setVerticalAxis("2");
        move1.setPlayer(Player.PLAYER_X);
        moves.add(move1);
        Move move2 = new Move();
        move2.setHorizontalAxis("3");
        move2.setVerticalAxis("2");
        move2.setPlayer(Player.PLAYER_X);
        moves.add(move2);

        Game game = new Game();
        game.setMoves(moves);

        Assertions.assertTrue(gameUtils.isWinner(movePostDto, game));
    }

    @Test
    public void givenGameUtils_whenMovesMeetCriteriaList_thenNoWinner() {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setHorizontalAxis(1);
        movePostDto.setVerticalAxis(3);
        movePostDto.setPlayer(Player.PLAYER_X);

        Set<Move> moves = new HashSet<>();
        Move move1 = new Move();
        move1.setHorizontalAxis("2");
        move1.setVerticalAxis("2");
        move1.setPlayer(Player.PLAYER_X);
        moves.add(move1);
        Move move2 = new Move();
        move2.setHorizontalAxis("3");
        move2.setVerticalAxis("2");
        move2.setPlayer(Player.PLAYER_X);
        moves.add(move2);

        Game game = new Game();
        game.setMoves(moves);

        Assertions.assertFalse(gameUtils.isWinner(movePostDto, game));
    }
}
