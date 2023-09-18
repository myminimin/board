package org.zerock.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {
    // Reply 엔티티 클래스와 유사하게 작성하지만, 게시물의 번호만을 가지는 형태로 작성
    
    private Long rno;
    private String text;
    private String replyer;
    private Long bno;   // 게시글 번호
    private LocalDateTime regDate, modDate;
}
