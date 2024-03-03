package flab.schoolreunion.board.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
