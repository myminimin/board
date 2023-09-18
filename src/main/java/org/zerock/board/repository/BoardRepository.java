package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>,
        SearchBoardRepository {

    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);
    // 1. 엔티티 클래스 내부에 연관관계가 있는 경우
    // Board를 사용하고 있지만, Member를 같이 조회해야 하는 상황
    // Board 클래스에는 Member와의 연관관계를 맺고 있으므로 b.writer와 같은 형태로 사용
    // 이처럼 내부에 있는 엔티티를 이용할 때는 'LEFT JOIN' 뒤에 'ON'을 이용하는 부분이 없다

    @Query("select b, r from Board b left join  Reply  r ON r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
    // 2. 연관관계가 없는 엔티티 조인 처리에는 on
    // 중간에 'ON'이 사용되면서 조인 조건을 직접 지정하는 부분이 추가되는 것을 볼 수 있다.

    @Query(value = "select b, w,count(r) from Board b left join b.writer w left join Reply r on r.board = b group by b",
            countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("select b, w, count(r) from Board b left join b.writer w left outer join Reply r ON r.board = b where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
