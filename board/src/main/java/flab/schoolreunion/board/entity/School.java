package flab.schoolreunion.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class School {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
