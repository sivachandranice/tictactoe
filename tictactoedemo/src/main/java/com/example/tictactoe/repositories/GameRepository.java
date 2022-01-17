package com.example.tictactoe.repositories;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tictactoe.entities.Game;


public interface GameRepository extends JpaRepository<Game, UUID> {
    
}
