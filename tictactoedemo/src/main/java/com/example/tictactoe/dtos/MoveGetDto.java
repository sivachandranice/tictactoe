package com.example.tictactoe.dtos;

import java.util.Calendar;
import java.util.UUID;

import com.example.tictactoe.constants.Player;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MoveGetDto {

    private UUID id;

    @ApiModelProperty(value = "The vertical axis of the move player performs. The value should not be larger than 3")
    private int verticalAxis;

    @ApiModelProperty(value = "The horizontal axis of the move player performs. The value should not be larger than 3")
    private int horizontalAxis;
    
    @ApiModelProperty(value = "Date time when the action was performed")
    private Calendar dateTime;

    @ApiModelProperty(value = "Who performs the move. There are two types: PLAYER_O & PLAYER_X")
    private Player player;
    
    @ApiModelProperty(value = "Game id")
    private UUID game_id;
}
