package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying  // JPQL을 이용해서 update, delte를 실행하려면 필요
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(Long bno);
    // 게시물을 삭제하려면 FK로 참고하고 있는 reply 테이블 역시 삭제가 되어야 한다.
    // 1. 해당 게시물의 모든 댓글을 삭제하고 / 2. 해당 게시물을 삭제한다
    // 이 작업이 하나의 '트랜젝션'으로 처리되어야 하기 때문에 특정 게시물 번호(bno)로 댓글을 삭제하는 기능이 필요하다.

    List<Reply> getRepliesByBoardOrderByRno(Board board);
    // 게시물로 댓글 목록 가져오기 (Board 객체를 파라미터로 받아 모든 댓글을 순번대로 가져옴)
}
