package flab.schoolreunion.board.repository;

import flab.schoolreunion.board.dto.board.BoardSearchCondition;
import flab.schoolreunion.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @Query("select b" +
            " from Board b" +
            " join fetch b.member" +
            " join fetch b.reunion r" +
            " join fetch r.school")
    public List<Board> findAll();

    @Override
    Page<Board> search(BoardSearchCondition condition, Pageable pageable);

    @Query("select b" +
            " from Board b" +
            " join fetch b.member" +
            " join fetch b.reunion r" +
            " join fetch r.school" +
            " where b.id = :id")
    public Optional<Board> findById(@Param("id") Long id);
}