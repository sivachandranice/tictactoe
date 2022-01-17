/**
 * 
 */
package com.example.tictactoe.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Siva_Chandran
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GamePostDto {
	
	@ApiModelProperty(value = "The name of the Player1")
	private String player_x;
	
	@ApiModelProperty(value = "The name of the Player2")
	private String player_o;
	
	@ApiModelProperty(value = "The name of the status, There are 3 types:READY, STARTED, FINISHED")
    private String status;

}
