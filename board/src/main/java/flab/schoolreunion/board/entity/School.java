package flab.schoolreunion.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class School {
    @Id
    private UUID uuid = UUID.randomUUID();

    private String name;
}
