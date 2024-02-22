package flab.schoolreunion.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.util.UUID;

/**
 * 동창회
 */
@Entity
@Getter
public class Reunion {
    @Id
    private UUID uuid = UUID.randomUUID();

    @ManyToOne
    private School school;

    private Integer grade;

    private Integer year;
}
