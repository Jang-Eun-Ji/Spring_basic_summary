package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
@Api(tags = "회원관리서비스")
@RestController
@RequestMapping("/rest")
public class MemberRestController {
    private final MemberService memberService; //방법 2에서는 final을 붙여야함

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("member/create") //postman이랑 mapping 맞춰야함
    public String memberCreate(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.memberCreate(memberRequestDto);
        return "ok";
    }
    @GetMapping("members")
    public List<MemberResponseDto> members(){
        List<MemberResponseDto> memberResponseDtos = memberService.members();
        return memberResponseDtos;
    }
    @GetMapping("member/find/{id}") //ResponseEntity외울 필요 없음 걍 보면 이해될 정도만
    public ResponseEntity<Map<String, Object>> memberFind(@PathVariable int id){
        MemberResponseDto memberResponseDto = null;
        try {
            memberResponseDto = memberService.findById(id);
            return ResponseEntityController.responseMessage(memberResponseDto, HttpStatus.OK);
//             HttpStatus.NOT_FOUND - 에러 아닌 404status로 뜬다., OK - 200
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return ResponseEntityController.errResponseMessage(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @DeleteMapping("member/delete/{id}")
    public String memberDelect(@PathVariable int id){
        memberService.delete_function(id);
        return "ok";
    }
    @PatchMapping("member/update")
    public MemberResponseDto memberUpdate(@RequestBody MemberRequestDto memberRequestDto){
        memberService.memberUpdate(memberRequestDto);
        return memberService.findById(memberRequestDto.getId());
    }

}

