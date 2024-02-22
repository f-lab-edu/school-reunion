package flab.schoolreunion.board.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class BoardResponse {
    private String writerName;
    private Long writerId;
    private Long reunionId;
    private Long boardId;
    private String title;
    private String content;

}
