package com.example.tictactoe.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.tictactoe.constants.Player;
import com.example.tictactoe.constants.Status;
import com.example.tictactoe.dtos.GameGetDto;
import com.example.tictactoe.dtos.GamePostDto;
import com.example.tictactoe.dtos.MovePostDto;
import com.example.tictactoe.services.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testCreateGame() throws Exception {
        GamePostDto gamePostDto = new GamePostDto();
        gamePostDto.setStatus(Status.READY.name());
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setStatus(Status.READY.name());
        BDDMockito.given(gameService.createGame(gamePostDto)).willReturn(gameGetDto);
        mockMvc.perform(post("/v1/games")
                .content(objectMapper.writeValueAsString(gamePostDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(Status.READY.name()));
    }


    @Test
    public void testFindNameById() throws Exception {
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setStatus(Status.READY.name());
        UUID id = UUID.randomUUID();
        BDDMockito.given(gameService.findGameById(id)).willReturn(gameGetDto);
        mockMvc.perform(get("/v1/games/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(Status.READY.name()));
    }



    @Test
    public void testPerformMove() throws Exception {
        MovePostDto movePostDto = new MovePostDto();
        movePostDto.setVerticalAxis(1);
        movePostDto.setHorizontalAxis(2);
        movePostDto.setPlayer(Player.PLAYER_X);
        GameGetDto gameGetDto = new GameGetDto();
        gameGetDto.setStatus(Status.READY.name());
        UUID gameId = UUID.randomUUID();
        BDDMockito.given(gameService.performMove(gameId, movePostDto)).willReturn(gameGetDto);
        mockMvc.perform(post("/v1/games/" + gameId + "/moves")
                .content(objectMapper.writeValueAsString(movePostDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
