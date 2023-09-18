package org.zerock.board.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.board.entity.Board;

public interface SearchBoardRepository {

    Board search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
    // PageRequestDTO 자체를 파라미터로 처리하지 않는 이유는 DTO를 가능하면
    // Repository 영역에서 다루지 않기 위해서이다.
}
