package org.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") // exclude 문자열 변환 하지 않음 (writer는 객체화하겠다.)
public class Board extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    
    private String title;
    private String content;
    
    @ManyToOne (fetch = FetchType.LAZY)
    // board와 member 관계는 N:1(다대일)
    // 명시적으로 Lazy 로딩을 지정 (지연 로딩)
    private Member writer; // 연관관계 지정

    /* 게시글의 제목과 내용에 한해서는 수정이 가능하도록 설정 */
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

}
