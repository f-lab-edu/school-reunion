package flab.schoolreunion.board.service;

import flab.schoolreunion.board.dto.board.BoardRequest;
import flab.schoolreunion.board.dto.board.BoardResponse;
import flab.schoolreunion.board.dto.board.BoardUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BoardService {

    public List<BoardResponse> getAll() {
        return null;
    }

    public BoardResponse getOne(Long id) {
        return null;
    }

    @Transactional
    public BoardResponse post(BoardRequest boardRequest) {
        return null;
    }

    @Transactional
    public BoardResponse update(BoardUpdateRequest boardUpdateRequest, Long id) {
        return null;
    }

    @Transactional
    public void delete(Long id) {
    }
}
