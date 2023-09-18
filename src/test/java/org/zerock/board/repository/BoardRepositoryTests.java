package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard() {

        IntStream.rangeClosed(1,100).forEach(i -> {

            Member member = Member.builder()
                    .email("user"+i +"@aaa.com")
                    .build();
            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content..."+i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        }); // 한 명의 사용자가 하나의 게시물을 등록
    }

    @Transactional  // 해당 메서드를 하나의 '트랜젝션'으로 처리하라는 의미
    @Test
    public void testRead1() {

        Optional<Board> result = boardRepository.findById(100L);
        // 데이터베이스에서 실제 존재하는 번호

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
        // board.getWriter을 처리하기 위해 member 테이블을 다시 로딩한다.

    }   // @ManyToOne의 경우 FK(Board)쪽의 엔티티를 가져올 때 PK(Member)쪽의 엔티티도 같이 가져온다

    @Test
    public void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("-----------------------------------------");
        System.out.println(Arrays.toString(arr));

    }   // 엔티티 클래스 내부에 연관관계가 있는 경우 (Member - Board)

    @Test
    public void testGetBoardWithReply() {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[])row;

            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3() {

        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[])result;

        System.out.println(Arrays.toString(arr));
    }   // 목록 처리와 유사하지만 특정한 게시물 번호를 사용하는 부분이 다르다.

    @Test
    public void testSearch1() {
        
        boardRepository.search1();

    } // 검색1 테스트

    @Test
    public void testSearchPage() {

        Pageable pageable =
                PageRequest.of(0,10,
                        Sort.by("bno").descending()
                                .and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }
}
