package flab.schoolreunion.board.repository;

import flab.schoolreunion.board.entity.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReunionRepository extends JpaRepository<Reunion, Long> {
}
