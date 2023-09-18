package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository; // 자동 주입용 final
    private final ReplyRepository replyRepository;  // 게시글 삭제 시 댓글도 삭제 (트랜젝션) 하기 위해서 추가

    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board = dtoTOEntity(dto);
        // dto 객체를 board 엔티티로 변환해주는 역할

        repository.save(board);

        return board.getBno();
        // 파라미터로 DTO타입을 전달받아 생성된 게시물의 번호를 반환
    } 

    @Override
    public BoardDTO get(Long bno) {

        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
        // Board 엔티티, Member 엔티티, 댓글의 수
    } 

    @Transactional  // 트랜젝션
    @Override
    public void removeWithReplies(Long bno) {

        replyRepository.deleteByBno(bno); // 댓글부터 삭제하고
        
        repository.deleteById(bno); // 게시글 삭제
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = repository.findById(boardDTO.getBno());
        Board board = result.get();

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(board.getContent());

        repository.save(board);
    }


    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));
        // entityToDTO()를 이용해서

        // Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, fn); // fn - PageResultDTO 객체를 구성하는 부분이 핵심이다.
    }
}
