package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("response/entity")
public class ResponseEntityController {
//    @ResponseStatus어노테이션 방식
    @GetMapping("responsestatus")
    @ResponseStatus(HttpStatus.CREATED)
    public String responseStatus(){
        return "OK";

    }

    @GetMapping("responsestatus2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member responseStatus2(){
        Member member = new Member("jang","wdw","123");
        return member;
    }

//    ResponseEntity 객체를 직접 생성한 방식
    @GetMapping("custom1")
    public ResponseEntity<Member> custom1(){
        Member member = new Member("kim", "kim@naver.com", "1234");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }
//  ResonseEntity<String>일 경우 text/html로 설정
    @GetMapping("custom2")
    public ResponseEntity<String> custom2(){
        String html = "<h1>없는 아이디 입니다.</h1>";
        return new ResponseEntity<>(html, HttpStatus.NOT_FOUND);
        // 원래는 예외를 try,catch로 잡으면 200으로 나왔는데 이렇게 하면 400에러가 난다.
    }

//    map형태의 메시지 커스텀 , 에러상황
//    @GetMapping("map_custom1") // map을 json을 직렬화 하면 json이 된다.
public static ResponseEntity<Map<String, Object>> errResponseMessage(HttpStatus httpStatus, String s){
    Map<String, Object> body = new HashMap<>();
    body.put("status", Integer.toString(httpStatus.value()));
    body.put("status message", s);
    body.put("error message", httpStatus.getReasonPhrase());
    return new ResponseEntity<>(body, httpStatus);
}

    // status 201, message : 객체
    // //    map형태의 메시지 커스텀 , 정상상황
    //////    status 201, message: 객체
    ////@GetMapping("map_custom2") // map을 json을 직렬화 하면 json이 된다.
    public static ResponseEntity<Map<String, Object>> responseMessage(Object object, HttpStatus httpStatus) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(httpStatus.value()));
        body.put("message", object);
        return new ResponseEntity<>(body, httpStatus);
    }
//  메서드 체이닝: ResponseEntity의 클래스 메소드 사용
    @GetMapping("chaing1")
    public ResponseEntity<Member> chaining1(){
        Member member = new Member("jang","wdw","123");
        return ResponseEntity.ok(member);
    }
//    메서드 체이닝 2
@GetMapping("chaing2")
    public ResponseEntity<Member> chaining2(){
        return ResponseEntity.notFound().build();
    }
@GetMapping("chaing3")
    public ResponseEntity<Member> chaining3(){
        Member member = new Member("jang","wdw","123");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

@GetMapping("chaing4")
    public ResponseEntity<Member> chaining4(){
        Member member = new Member("jang","wdw","123");
        return new ResponseEntity(member, HttpStatus.CREATED);
    }
}