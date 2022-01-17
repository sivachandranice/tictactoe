package com.example.tictactoe.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tictactoe.constants.Player;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;
    private final GameMapper gameMapper;
    private final MoveMapper moveMapper;
    private final GameUtils gameUtils;

    public GameGetDto createGame(GamePostDto gamePostDto) {
        Game game = gameRepository.save(gameMapper.toEntity(gamePostDto));
        return gameMapper.fromEntity(game);
    }

    /**
     * List all the Games which each game contains details such as game name, status etc.
     *
     * @return a list of game objects
     */
    public List<GameGetDto> listAll() {
        List<Game> gameList = gameRepository.findAll();
        return gameList.stream().map(game -> gameMapper.fromEntity(game)).collect(Collectors.toList());
    }

    

    /**
     * Find a game by game Id.
     *
     * @param id the primary key of the game
     * @return GameGetDto object containing game details
     */
    public GameGetDto findGameById(UUID id) {
        return gameMapper.fromEntity(findGameEntity(id));
    }

    /**
     * Perform a move in a game and do the following validation:
     *  1. Horizontal axis and vertical axis should be not be large than 3.
     *  2. Game Id should be valid.
     *  3. Players should play game in tern.
     *  4. move can't be duplicated.
     *
     *  The initial status of the game is READY. then it will be STARTED if there is no winner. Otherwise
     *  it will be FINISH.
     *
     *  The attribute lastPlayer of the game records current player which is performing this move. It can
     *  be either PLAYER_O or PLAYER_X.
     *  If game is finished but still there is no winner, the game ended in a draw. the LastPlayer would be
     *  DRAW.
     *
     * @param gameId the primary key of the game
     * @param movePostDto the MovePostDto passed from client.
     * @return the updated GameGetDto object contains details such as status last player etc.
     */
    @Transactional
    public GameGetDto performMove(UUID gameId, MovePostDto movePostDto) {
        if (!gameUtils.isMovePayloadValid(movePostDto)) {
            throw new BadRequestException("Invalid move payload, axis should be within 3");
        }

        Game game = findGameEntity(gameId);
        if (!gameUtils.isPlayerValid(movePostDto, game)) {
            throw new BadRequestException("opponent's turn to play");
        }

        if (gameUtils.isDuplicatedAxis(movePostDto, game)) {
            throw new BadRequestException("This axis already exists");
        }
        updateStatusAndLastPlayerInGame(movePostDto, game);

        Move move = moveMapper.toEntity(movePostDto);
        move.setGame(game);
        moveRepository.save(move);
        return gameMapper.fromEntity(gameRepository.save(game));
    }

    private void updateStatusAndLastPlayerInGame(MovePostDto movePostDto, Game game) {
        game.setLastPlayer(movePostDto.getPlayer());
        
        if(null != movePostDto.getPlayer() && movePostDto.getPlayer().equals(Player.PLAYER_X)) {
        	game.setNextPlayer(Player.PLAYER_O);
        }
        else {
        	game.setNextPlayer(Player.PLAYER_X);
        }
        
        if (gameUtils.isWinner(movePostDto, game)) {
            game.setStatus(Status.FINISH);
        } else {
            // The game ended in a draw when the size of moves is 9 and no winner.
            if (game.getMoves().size() == 9) {
                game.setLastPlayer(Player.DRAW);
            }
            game.setStatus(Status.STARTED);
        }
    }

    private Game findGameEntity(UUID id) {
        return gameRepository.findById(id).<GameNotFoundException>orElseThrow(() -> {
            throw new GameNotFoundException("Game not found");
        });
    }

}
