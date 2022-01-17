package com.example.tictactoe.entities;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.tictactoe.constants.Player;
import com.example.tictactoe.constants.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game")
@Getter
@Setter
@RequiredArgsConstructor
public class Game {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.READY;

    @Column(name = "last_player", nullable = true)
    @Enumerated(EnumType.STRING)
    private Player lastPlayer;
    
    @Column(name = "next_player", nullable = true)
    @Enumerated(EnumType.STRING)
    private Player nextPlayer;
    
    @Column(name = "player_x", nullable = false)
    private String player_x;
    
    @Column(name = "player_o", nullable = false)
    private String player_o;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "game")
    private Set<Move> moves;

}
