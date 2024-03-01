package flab.schoolreunion.board.service;

import flab.schoolreunion.board.dto.board.BoardRequest;
import flab.schoolreunion.board.dto.board.BoardResponse;
import flab.schoolreunion.board.dto.board.BoardUpdateRequest;
import flab.schoolreunion.board.entity.Board;
import flab.schoolreunion.board.repository.BoardRepository;
import flab.schoolreunion.board.repository.MemberRepository;
import flab.schoolreunion.board.repository.ReunionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ReunionRepository reunionRepository;

    public BoardService(BoardRepository boardRepository, MemberRepository memberRepository, ReunionRepository reunionRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.reunionRepository = reunionRepository;
    }

    public List<BoardResponse> getAll() {
        return boardRepository.findAll().stream().map(this::boardToBoardResponse).toList();
    }

    public BoardResponse getOne(Long id) {
        return boardToBoardResponse(boardRepository.findById(id).orElseThrow());
    }

    @Transactional
    public BoardResponse post(BoardRequest boardRequest) {
        Board board = boardRequestToBoard(boardRequest);
        board = boardRepository.save(board);
        return boardToBoardResponse(board);
    }

    @Transactional
    public BoardResponse update(BoardUpdateRequest boardUpdateRequest, Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.update(boardUpdateRequest.getTitle(), boardUpdateRequest.getContent());
        return boardToBoardResponse(board);
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    private Board boardRequestToBoard(BoardRequest dto) {
        return Board.builder()
                .member(memberRepository.findById(dto.getWriterId()).orElseThrow())
                .reunion(reunionRepository.findById(dto.getReunionId()).orElseThrow())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    private BoardResponse boardToBoardResponse(Board board){
        return new BoardResponse(
                board.getMember().getName(),
                board.getMember().getId(),
                board.getReunion().getId(),
                board.getId(),board.getTitle(),
                board.getContent());
    }
}
