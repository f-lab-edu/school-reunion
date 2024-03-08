package flab.schoolreunion.board.entity;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 동창회
 */
@Entity
@Getter
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private School school;

    private Integer grade;

    private Integer year;
}
