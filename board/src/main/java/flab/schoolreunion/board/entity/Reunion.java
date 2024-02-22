package flab.schoolreunion.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    private Long id;

    @ManyToOne
    private School school;

    private Integer grade;

    private Integer year;
}
