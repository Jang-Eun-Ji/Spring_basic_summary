package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.Optional;

//service 어노테이션을 통해 싱글톤 컴포넌트로 생성 -> 스프링 빈으로
//스프링 빈: 스프링이 생성하고 관리하는 객체를 의미
//제어의 역전(Inversion of Control)  -> IOC Container: 스프링 빈을 관리함(bin을 생성, 의존성 주입)
@Controller // html에서 인식해서 입력되게 함
//@RequiredArgsConstructor // 방법 3
//화면 던져주는 역할
public class MemberController {
//    의존성주입(DI) 방법1
//    필드주입방식
//    @Autowired // @Service 빈 컨테이너에 있는걸 가져와 controller에 넣어줌
//    private MemberService memberService; // 최초 세팅은 되지만 추후 수정은 안되게 만듦

//    의존성 주입 방법2
//    생성자 주입 방식 ==> 가장 많이 사용하는 방법
//    장점: final을 통해 상수로 사용가능, 다용성 구현 가능(제일 중요, 그래서 service), 순환참조 방지
//    생성자가 1개밖에 없을때는 @Autowired 생략 가능함
    private final MemberService memberService; //방법 2에서는 final을 붙여야함
    @Autowired
    public MemberController (MemberService memberService){
        this.memberService = memberService;
    }
//
//  의존성 주입 방법3
//  @RequiredArgsConstructor를 이용한  방식 , 다형성 구현 불가능 - 다형성:왼쪽 인터페이스 오른쪽 구현체??
//@RequiredArgsConstructor: @NonNull어노테이션이 붙어있는 필드 또는 초기화 되지 않는 final필드 대상으로 생성자 생성.
//    필요한 Argments대상으로 Constructor를 만들겠다는 의미,
//    필요한 Argument를 구분하는 방법: @NonNull이거나 final인 객체 + @RequiredArgsConstructor =>생성자 만들어짐
//    private final MemberService memberService;


//    MemberController(){
//        memberService = new MemberService();
//    } 의존성 주입방식을 써서 이게 필요 없음
    @GetMapping("members/create")
    public String memberCreateScreen(){
        return "member/member-create-screen";
}
    //회원가입
    @PostMapping("members/create")
    public String memberCreate(MemberRequestDto memberRequestDto){
//       컨트롤 페이지에서 서비스를 호출하고 서비스에서 에러를 컨트롤로 던져주고 여기서 받은 다음 여기서 에러 페이지 사용자에게 줌
//        try {
//            memberService.memberCreate(memberRequestDto);
//            return "redirect:/members"; //네트워크에서 302로 나옴(200 처럼 리다이렉트의 정상이라는 뜻)
//        }catch (IllegalArgumentException e){
//            return "member/404-error-page";
//        }
        memberService.memberCreate(memberRequestDto);
//        url 리다이렉트
        return "redirect:/members";
    }
    //회원목록 조회, 화면 던져주는 역할
    @GetMapping("members")
    public String members(Model model){
        model.addAttribute("memberList", memberService.members());
        return "member/member-list";
    }

    @GetMapping("/")
    public String home(){
        return "member/header";
    }

    @GetMapping("/member/find")
    public String memberFindById(@RequestParam(value = "id")int id, Model model){
        try {
            MemberResponseDto memberResponseDto = memberService.findById(id);
//        model이 html에 데이터를 박는 역할 따라서 "memberResDto" 이 html의 변수랑 같이 맞추기
            model.addAttribute("memberResDto",memberService.findById(id));
            return "member/member-find";
        }catch (EntityNotFoundException e){
            return "member/404-error-page";
        }

    }
    @GetMapping("/member/delete")
    public String memberDelect(@RequestParam(value = "id") int id){
            memberService.delete_function(id);
            return "redirect:/members"; //redirect는 url주소로 하기
    }

    @PostMapping("members/update")
    public String memberUpdate(MemberRequestDto memberRequestDto){
        memberService.memberUpdate(memberRequestDto);
        return "redirect:/member/find?id=" + memberRequestDto.getId();
    }
}
