package flab.schoolreunion.board.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class SchoolMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
