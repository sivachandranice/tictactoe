/**
 * 
 */
package com.example.tictactoe.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tictactoe.dtos.GameGetDto;
import com.example.tictactoe.dtos.GamePostDto;
import com.example.tictactoe.dtos.MovePostDto;
import com.example.tictactoe.services.GameService;

import lombok.RequiredArgsConstructor;

/**
 * @author Siva_Chandran
 *
 */

@RestController
@RequestMapping("/v1/games")
@RequiredArgsConstructor

public class GameController {
	
	private final GameService gameService;
	
	@GetMapping
    public ResponseEntity<List<GameGetDto>> findAllGames() {
        return ResponseEntity.ok(gameService.listAll());
    }
	
	@PostMapping
    public ResponseEntity<GameGetDto> createGame(@RequestBody GamePostDto gamePostDto) {
        GameGetDto gameGetDto = gameService.createGame(gamePostDto);
        return ResponseEntity.ok(gameGetDto);
    }
	
	@GetMapping("/{gameId}")
    public ResponseEntity<GameGetDto> findGameById(@PathVariable String gameId) {
        return ResponseEntity.ok(gameService.findGameById(UUID.fromString(gameId)));
    }
	
	@PostMapping("/{gameId}/moves")
    public ResponseEntity<GameGetDto> performMove(@PathVariable String gameId, @RequestBody MovePostDto movePostDto) {
        return ResponseEntity.ok(gameService.performMove(UUID.fromString(gameId), movePostDto));
    }

}
