package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    
    private final ReplyService replyService; // 자동주입을 위해 final 선언
    
    @GetMapping(value = "/board/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){
        // GetMapping에서 '{}'로 묶은 변수를 이용하는데 이는 @PathVariable이라는 것으로 처리한다
        log.info("bno: " + bno);
        
        return new ResponseEntity<>( replyService.getList(bno), HttpStatus.OK);
        // ResponseEntity라는 객체로 반환 타입을 이용하는데 이를 이용하면
        // HTTP의 상태 코드 등을 같이 전달할 수 있다.
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){
        // @RequestBody는 JSON으로 들어오는 데이터를 자동으로 해당 타입의 객체로 매핑해주는 역할을 한다
        // 때문에 개발 시에는 별도의 처리 없어도 JSON 데이터를 특정 타입의 객체로 변환해서 처리할 수 있다.

        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
}
