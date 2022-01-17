package com.example.tictactoe.entities;


import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.example.tictactoe.constants.Player;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "move")
@Getter
@Setter
@ToString(exclude = "game")
@RequiredArgsConstructor
public class Move {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "vertical_axis", nullable = false)
    private String verticalAxis;

    @Column(name = "horizontal_axis", nullable = false)
    private String horizontalAxis;
    
    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Calendar dateTime;

    @Enumerated(EnumType.STRING)
    private Player player;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
}
