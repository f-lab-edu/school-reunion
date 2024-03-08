package flab.schoolreunion.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import flab.schoolreunion.board.dto.board.BoardSearchCondition;
import flab.schoolreunion.board.entity.Board;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static flab.schoolreunion.board.entity.QBoard.board;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Board> search(BoardSearchCondition condition, Pageable pageable) {
        List<Board> content = queryFactory.
                select(board)
                .from(board)
                .join(board.member).fetchJoin()
                .join(board.reunion).fetchJoin()
                .join(board.reunion.school).fetchJoin()
                .where(board.reunion.id.eq(condition.getReunionId()),
                        titleContain(condition.getTitle()),
                        contentContain(condition.getContent()),
                        writerEq(condition.getWriterId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression writerEq(Long writerId) {
        return writerId == null ? null : board.member.id.eq(writerId);
    }

    private BooleanExpression contentContain(String content) {
        return hasText(content) ? board.content.contains(content) : null;
    }

    private BooleanExpression titleContain(String title) {
        return hasText(title) ? board.title.contains(title) : null;
    }
}