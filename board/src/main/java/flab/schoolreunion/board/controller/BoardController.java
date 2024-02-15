package flab.schoolreunion.board.controller;

import flab.schoolreunion.board.dto.board.BoardRequest;
import flab.schoolreunion.board.dto.board.BoardResponse;
import flab.schoolreunion.board.dto.board.BoardUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class BoardController {

    @GetMapping("/")
    public List<BoardResponse> getAll(){
        return null;
    }

    @GetMapping("/{id}")
    public BoardResponse getOne(@PathVariable Long id){
        return null;
    }

    @PostMapping("")
    public BoardResponse post(@RequestBody BoardRequest boardRequest){
        return null;
    }

    @PutMapping("/{id}")
    public BoardResponse update(@RequestBody BoardUpdateRequest boardUpdateRequest, @PathVariable Long id){
        return null;
    }

    @PostMapping("/{id}")
    public void delete(@PathVariable Long id){
    }
}
