package flab.schoolreunion.board.repository;

import flab.schoolreunion.board.dto.board.BoardSearchCondition;
import flab.schoolreunion.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BoardRepositoryCustom{

    public Page<Board> search(BoardSearchCondition condition, Pageable pageable);
}
