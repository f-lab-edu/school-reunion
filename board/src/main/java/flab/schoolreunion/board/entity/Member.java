package flab.schoolreunion.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Member {
    @Id
    private UUID uuid = UUID.randomUUID();

    private String email;

    private String name;
}
