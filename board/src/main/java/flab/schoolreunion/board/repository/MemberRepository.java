package flab.schoolreunion.board.repository;

import flab.schoolreunion.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
