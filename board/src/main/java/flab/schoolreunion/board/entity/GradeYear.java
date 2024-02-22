package flab.schoolreunion.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

/**
 * 해당 학년에 몇년도였는지
 */
@Entity
public class GradeYear {
    @Id
    private UUID uuid = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolMember schoolMember;

    private Integer grade;

    private Integer year;
}
