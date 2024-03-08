package flab.schoolreunion.board.controller;

import flab.schoolreunion.board.dto.board.BoardRequest;
import flab.schoolreunion.board.dto.board.BoardResponse;
import flab.schoolreunion.board.dto.board.BoardSearchCondition;
import flab.schoolreunion.board.dto.board.BoardUpdateRequest;
import flab.schoolreunion.board.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("")
    public Page<BoardResponse> search(BoardSearchCondition condition, Pageable pageable) {
        return boardService.search(condition, pageable);
    }

    @GetMapping("/{id}")
    public BoardResponse getOne(@PathVariable Long id){
        return boardService.getOne(id);
    }

    @PostMapping("")
    public BoardResponse post(@RequestBody BoardRequest boardRequest){
        return boardService.post(boardRequest);
    }

    @PutMapping("/{id}")
    public BoardResponse update(@RequestBody BoardUpdateRequest boardUpdateRequest, @PathVariable Long id){
        return boardService.update(boardUpdateRequest,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        boardService.delete(id);
    }
}
