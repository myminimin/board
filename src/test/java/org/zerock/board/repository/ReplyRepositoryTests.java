package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        IntStream.rangeClosed(1,300).forEach(i -> {

            long bno = (long)(Math.random() * 100) + 1;
            // 1부터 100까지의 임의의 번호

            Board board = Board.builder().bno(bno).build();
            // 임의의 번호

            Reply reply = Reply.builder()
                    .text("Reply...." +i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);

        }); // 300개의 댓글을 1~100사이의 번호로 추가한다.
    }

    @Test
    public void readReply1() {

        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());

    }   // Reply를 가져올 때 board와 member 테이블까지 모두 조인해온다. 매번 모두 가져올 필요가
        // 많지는 않으므로 위와 같이 여러 테이블이 조인으로 처리되는 상황은 효율적이지 않다.
        // 이렇게 로딩하는 것을 'Eager loading' 이라고 한다. = 즉시 로딩

    @Test
    public void testListByBoard() {

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build());

        replyList.forEach(reply -> System.out.println(reply));
    }
}
