package flab.schoolreunion.board.entity;

import jakarta.persistence.*;

@Entity
public class Board {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reunion reunion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;

    private String content;
}
