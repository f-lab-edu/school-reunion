package flab.schoolreunion.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String name;
}
