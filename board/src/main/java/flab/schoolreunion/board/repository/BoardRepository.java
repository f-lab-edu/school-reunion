package flab.schoolreunion.board.repository;

import flab.schoolreunion.board.dto.board.BoardResponse;
import flab.schoolreunion.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select new flab.schoolreunion.board.dto.board.BoardResponse(m.name, m.uuid, r.uuid, b.id, b.title, b.content)" +
            " from Board b" +
            " join fetch Member m" +
            " join fetch Reunion r")
    public List<BoardResponse> findAllBoardResponse();

    @Query("select new flab.schoolreunion.board.dto.board.BoardResponse(m.name, m.uuid, r.uuid, b.id, b.title, b.content)" +
            " from Board b" +
            " join fetch Member m" +
            " join fetch Reunion r" +
            " where b.id=:id")
    public BoardResponse findOneBoardResponse(@Param("id") Long id);

}