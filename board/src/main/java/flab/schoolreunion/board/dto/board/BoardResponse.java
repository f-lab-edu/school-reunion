package flab.schoolreunion.board.dto.board;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BoardResponse {
    private String writerName;
    private UUID writerUuid;
    private UUID reunionUuid;
    private Long boardId;
    private String title;
    private String content;

}
