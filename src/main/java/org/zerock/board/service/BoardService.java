package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);
    // 게시물 작성 - BoardDTO 타입을 파라미터로 전달받고, 생성된 게시물의 번호를 반환하도록 작성

    BoardDTO get(Long bno);
    // 게시물 조회
    
    void removeWithReplies(Long bno);
    // 게시물 삭제
    
    void modify(BoardDTO boardDTO);
    // 게시물 수정
    
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    // 목록 처리
    
    
    /* default */
    default Board dtoTOEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();
        // DTO가 연관관계를 가진 Board 엔티티 객체와 Member 엔티티 객체를 구성해야 하므로
        // 내부적으로 Member 엔티티를 처리하는 과정을 거쳐야 함 (이때 Member는
        // 실제 데이터베이스에 있는 이메일 주소를 사용해야 한다.)

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }   // BoardDTO를 Board 엔티티 타입으로 변환 - register()에서 사용할 예정

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        // 3개의 파라미터를 처리할 수 있도록 구성 (Board, Member, 댓글의 수)

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // long으로 나오므로 int로 처리해야한다
                .build();

        return boardDTO; // 파라미터들을 이용해서 BoardDTO 객체를 생성
    }
}
