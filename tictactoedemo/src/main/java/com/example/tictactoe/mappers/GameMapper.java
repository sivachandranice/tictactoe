package com.example.tictactoe.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.tictactoe.dtos.GameGetDto;
import com.example.tictactoe.dtos.GamePostDto;
import com.example.tictactoe.entities.Game;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameMapper {

    Game toEntity(GamePostDto gamePostDto);

    GameGetDto fromEntity(Game game);
}
