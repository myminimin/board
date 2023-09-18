package org.zerock.board.service;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);
    // 댓글 등록

    List<ReplyDTO> getList(Long bno);
    // 특정 게시물의 댓글 목록 가져오기

    void modify(ReplyDTO replyDTO);
    // 댓글 수정

    void remove(Long rno);
    // 댓글 삭제

    /* default */
    default Reply dtoToEntity(ReplyDTO replyDTO){

        Board board = Board.builder().bno(replyDTO.getBno()).build();
        // 연관관계가 있는 엔티티 처리는 하나의 DTO를 처리하기 위해서 여러 개의 엔티티를 처리해야 한다.
        // Reply 엔티티 객체가 Board 엔티티 객체를 참조하기 때문에 Board 객체에 대한 별도의 처리가 필요하다.

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();

        return reply;
    }

    default ReplyDTO entityToDTO(Reply reply){
        // Reply 객체를 ReplyDTO로 변환 -> Board 객체가 필요하지 않으므로 게시물 번호만

        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return dto;
    }
}
