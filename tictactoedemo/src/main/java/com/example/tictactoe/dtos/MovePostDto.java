package com.example.tictactoe.dtos;


import com.example.tictactoe.constants.Player;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MovePostDto {

    @ApiModelProperty(value = "The vertical axis of the move player performs. The value should not be larger than 3")
    private int verticalAxis;

    @ApiModelProperty(value = "The horizontal axis of the move player performs. The value should not be larger than 3")
    private int horizontalAxis;
    
    @ApiModelProperty(value = "Who performs the move.")
    private Player player;
    
}
