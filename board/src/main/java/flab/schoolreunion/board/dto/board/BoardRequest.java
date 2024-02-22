package flab.schoolreunion.board.dto.board;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BoardRequest {
    private Long writerId;
    private Long reunionId;
    private String title;
    private String content;
}
