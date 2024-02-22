package flab.schoolreunion.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Member {
    @Id
    private UUID uuid = UUID.randomUUID();

    private String email;

    private String name;
}
