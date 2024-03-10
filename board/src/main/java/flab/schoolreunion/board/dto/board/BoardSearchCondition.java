package flab.schoolreunion.board.dto.board;

import lombok.Data;

@Data
public class BoardSearchCondition {
    private Long reunionId;
    private Target target;
    private String keyword;
}