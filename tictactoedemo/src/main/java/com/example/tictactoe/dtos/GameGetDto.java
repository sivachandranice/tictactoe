/**
 * 
 */
package com.example.tictactoe.dtos;

import java.util.ArrayList;
import java.util.UUID;

import com.example.tictactoe.entities.Move;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Siva_Chandran
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameGetDto {

	@ApiModelProperty(value = "The primary key of the game")
	private UUID id;

	@ApiModelProperty(value = "The name of the Player1")
	private String player_x;
	
	@ApiModelProperty(value = "The name of the Player2")
	private String player_o;
	
	@ApiModelProperty(value = "The name of the status, There are 3 types:READY, STARTED, FINISHED")
    private String status;
	
	@ApiModelProperty(value = "Next player")
	private String nextPlayer;
	
	@ApiModelProperty(value = "Array of the moves with timestamps")
	private ArrayList<Move> moves;
}
