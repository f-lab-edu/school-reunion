package flab.schoolreunion.board.entity;

import jakarta.persistence.*;


/**
 * 해당 학년에 몇년도였는지
 */
@Entity
public class GradeYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolMember schoolMember;

    private Integer grade;

    private Integer year;
}
