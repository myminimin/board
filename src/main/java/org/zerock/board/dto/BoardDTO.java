package org.zerock.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    // DTO를 구성하는 기준은 화면에 전달하는 데이터이거나, 반대로 화면 쪽에서 전달되는 데이터를
    // 기준으로 하기 때문에 엔티티 클래스의 구성과 일치하지 않는 경우가 많다.

    private Long bno;
    private String title;
    private String content;
    private String writerEmail; // 작성자의 이메일(id)
    private String writerName; // 작성자의 이름
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCount; // 해당 게시글의 댓글 수

}
