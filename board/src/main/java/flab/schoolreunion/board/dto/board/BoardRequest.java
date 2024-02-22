package flab.schoolreunion.board.dto.board;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BoardRequest {
    private UUID writerUuid;
    private UUID reunionUuid;
    private String title;
    private String content;
}
