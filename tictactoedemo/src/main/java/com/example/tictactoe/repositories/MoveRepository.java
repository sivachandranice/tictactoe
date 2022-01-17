package com.example.tictactoe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tictactoe.entities.Move;

import java.util.Set;
import java.util.UUID;

@Repository
public interface MoveRepository extends JpaRepository<Move, UUID> {

    Set<Move> findAllByGameId(UUID id);
}
