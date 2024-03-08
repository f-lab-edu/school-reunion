package flab.schoolreunion.board.dto.board;

import lombok.Data;

@Data
public class BoardSearchCondition {
    private String title;
    private String content;
    private Long writerId;
    private Long reunionId;
}
