package com.example.tictactoe.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import com.example.tictactoe.dtos.MoveGetDto;
import com.example.tictactoe.dtos.MovePostDto;
import com.example.tictactoe.entities.Move;
@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MoveMapper {

    Move toEntity(MovePostDto movePostDto);

    MoveGetDto fromEntity(Move move);
}
