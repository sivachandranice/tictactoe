package com.example.tictactoe.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.tictactoe.constants.Player;
import com.example.tictactoe.dtos.MovePostDto;
import com.example.tictactoe.entities.Game;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GameUtils {

    private final List<String[]> criteriaList;

    public boolean isMovePayloadValid(MovePostDto movePostDto) {
        return movePostDto.getHorizontalAxis() <= 3 && movePostDto.getVerticalAxis() <= 3;
    }

    public boolean isPlayerValid(MovePostDto movePostDto, Game game) {
        Player lastPlayer = game.getLastPlayer();
        return movePostDto.getPlayer() != lastPlayer;
    }

    public boolean isDuplicatedAxis(MovePostDto movePostDto, Game game) {
        List<String> currentTotalMoves = game.getMoves().stream()
                .map(move -> move.getHorizontalAxis() + "" + move.getVerticalAxis())
                .collect(Collectors.toList());
        String currentMove = movePostDto.getHorizontalAxis() + "" + movePostDto.getVerticalAxis();
        return currentTotalMoves.contains(currentMove);
    }

    public boolean isWinner(MovePostDto movePostDto, Game game) {
        List<String> currentPlayerMoves = game.getMoves().stream()
                .filter((move -> move.getPlayer() == movePostDto.getPlayer()))
                .map(move -> move.getHorizontalAxis() + "" + move.getVerticalAxis())
                .collect(Collectors.toList());

        currentPlayerMoves.add(movePostDto.getHorizontalAxis() + "" + movePostDto.getVerticalAxis());

        Optional<String[]> criteriaOption = criteriaList.stream().filter(criteria -> {
            List<String> subCriteriaList = Arrays.asList(criteria);
            return currentPlayerMoves.containsAll(subCriteriaList) ? true : false;
        }).findFirst();

        return criteriaOption.isPresent();
    }
}
