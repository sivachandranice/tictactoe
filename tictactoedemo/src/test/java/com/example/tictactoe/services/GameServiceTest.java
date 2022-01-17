package com.example.tictactoe.services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tictactoe.constants.Status;
import com.example.tictactoe.dtos.GameGetDto;
import com.example.tictactoe.dtos.GamePostDto;
import com.example.tictactoe.dtos.MovePostDto;
import com.example.tictactoe.entities.Game;
import com.example.tictactoe.entities.Move;
import com.example.tictactoe.exceptions.BadRequestException;
import com.example.tictactoe.exceptions.GameNotFoundException;
import com.example.tictactoe.mappers.GameMapper;
import com.example.tictactoe.mappers.MoveMapper;
import com.example.tictactoe.repositories.GameRepository;
import com.example.tictactoe.repositories.MoveRepository;
import com.example.tictactoe.utils.GameUtils;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @Mock
    private MoveMapper moveMapper;

    @Mock
    private MoveRepository moveRepository;

    @Mock
    private GameUtils gameUtils;

    @InjectMocks
    private GameService gameService;

    @Test
    public void givenGameService_whenCreateNewGame_thenOK() {
        GamePostDto gamePostDto = new GamePostDto();
        gamePostDto.setStatus(Status.READY.name());

        when(gameRepository.save(any())).thenReturn(new Game());
        when(gameMapper.toEntity(any())).thenReturn(new Game());

        GameGetDto expectedResult = new GameGetDto();
        expectedResult.setStatus(Status.READY.name());
        when(gameMapper.fromEntity(any())).thenReturn(expectedResult);

        GameGetDto returnedResult = gameService.createGame(gamePostDto);
        verify(gameMapper, times(1)).toEntity(any(GamePostDto.class));
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        Assertions.assertEquals(returnedResult.getStatus(), expectedResult.getStatus());
    }

    @Test
    public void givenGameService_whenFindAllGames_thenOK() {
        List<Game> gameList = new ArrayList<>();
        gameList.add(new Game());
        when(gameRepository.findAll()).thenReturn(gameList);

        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);

        List<GameGetDto> returnedGameList = gameService.listAll();
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        verify(gameRepository, times(1)).findAll();
        GameGetDto returnedGameGetDto = returnedGameList.get(0);
        Assertions.assertEquals(returnedGameGetDto.getStatus(), mockGameGetDto.getStatus());
    }



    @Test
    public void givenGameService_whenFindOneById_thenOK() {
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game()));
        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);
        GameGetDto returnedGameGetDto = gameService.findGameById(UUID.randomUUID());
        Assertions.assertEquals(returnedGameGetDto.getStatus(), mockGameGetDto.getStatus());
    }

    @Test
    public void givenGameService_whenNoGameFoundById_thenThrowException() {
        when(gameRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(GameNotFoundException.class, ()-> {
            gameService.findGameById(UUID.randomUUID());
        });
    }

    @Test
    public void givenGameService_whenMovePayloadIsInvalid_thenThrowException() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(false);
        Assertions.assertThrows(BadRequestException.class, ()-> {
            gameService.performMove(UUID.randomUUID(), new MovePostDto());
        });
    }

    @Test
    public void givenGameService_whenMovePayloadIsValidAndPlayerInvalid_thenThrowException() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(true);
        when(gameUtils.isPlayerValid(any(),any())).thenReturn(false);
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game()));

        Assertions.assertThrows(BadRequestException.class, ()-> {
            gameService.performMove(UUID.randomUUID(), new MovePostDto());
        });
    }

    @Test
    public void givenGameService_whenMovePayloadAndPlayerAreBothValidAndDuplicatedAxis_thenThrowException() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(true);
        when(gameUtils.isPlayerValid(any(),any())).thenReturn(true);
        when(gameUtils.isDuplicatedAxis(any(),any())).thenReturn(true);
        when(gameRepository.findById(any())).thenReturn(Optional.of(new Game()));

        Assertions.assertThrows(BadRequestException.class, ()-> {
            gameService.performMove(UUID.randomUUID(), new MovePostDto());
        });
    }

    @Test void givenGameService_whenMovePayloadAndPlayerValidAndNoDuplicatedAxisAndGameStarted_thenOK() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(true);
        when(gameUtils.isPlayerValid(any(),any())).thenReturn(true);
        when(gameUtils.isDuplicatedAxis(any(),any())).thenReturn(false);
        when(gameUtils.isWinner(any(),any())).thenReturn(false);
        Game game = new Game();
        Set<Move> moves = new HashSet<>();
        game.setMoves(moves);
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenReturn(new Game());
        when(moveMapper.toEntity(any())).thenReturn(new Move());
        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);

        gameService.performMove(UUID.randomUUID(), new MovePostDto());
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test void givenGameService_whenMovePayloadAndPlayerValidAndNoDuplicatedAxisAndGameFinished_thenOK() {
        when(gameUtils.isMovePayloadValid(any())).thenReturn(true);
        when(gameUtils.isPlayerValid(any(),any())).thenReturn(true);
        when(gameUtils.isDuplicatedAxis(any(),any())).thenReturn(false);
        when(gameUtils.isWinner(any(),any())).thenReturn(true);
        Game game = new Game();
        Set<Move> moves = new HashSet<>();
        game.setMoves(moves);
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        when(gameRepository.save(any())).thenReturn(new Game());
        when(moveMapper.toEntity(any())).thenReturn(new Move());
        GameGetDto mockGameGetDto = new GameGetDto();
        mockGameGetDto.setStatus("game_status");
        when(gameMapper.fromEntity(any())).thenReturn(mockGameGetDto);

        gameService.performMove(UUID.randomUUID(), new MovePostDto());
        verify(gameMapper, times(1)).fromEntity(any(Game.class));
        verify(gameRepository, times(1)).save(any(Game.class));



    }




}
