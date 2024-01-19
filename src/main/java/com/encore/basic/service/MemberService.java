package com.encore.basic.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.encore.basic.controller.MemberController;
import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service //빈 컨테이너에 넣는 역할, 싱글톤을 사용하기 위함

//트렌젝션 어노테이션 있어야 하는 상황// 클래스 위에 붙이면 밑 메소드에 다 적용됨
//  1. 전형적인 트랜젝션 작동이 필요한 상황
//  2. 중간에 예외처리시 rollback/서비스에서 에러가 터지면 데이터베이스에 안들어가게

public class MemberService {
//    @Autowired //의존성주입(DI) 방법1
//    private MemoryMemberRepository memoryMemberRepository;

    private final MemberRepository memberRepository; //MemberRepository 으로 오버라이드 된 Jdbc,Jpa 등등이 서비스 기능을 사용 가능
    @Autowired
    public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository){
        //repository를 갈아끼울 가능성이 많아 여기서 MemoryMemberRepository 바꾸기만 하면 된다.
        this.memberRepository = springDataJpaMemberRepository;
    }

//    public MemberService(){
//        memoryMemberRepository = new MemoryMemberRepository();
//    } -- 이거 없애고 스트링 빈 만듦
    public List<MemberResponseDto> members(){
        //DB에는 member타입으로 들어감 Entity는 DB와 싱크가 맞아야 함
        List<Member> members = memberRepository.findAll(); // -> jpa레파지토리에 있는 findAll()객체임
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for (Member member : members){
            MemberResponseDto memberResponseDto = new MemberResponseDto();
            memberResponseDto.setId(member.getId());
            memberResponseDto.setName(member.getName());
            memberResponseDto.setEmail(member.getEmail());
            memberResponseDto.setPassword(member.getPassword());
            memberResponseDto.setCreate_time(member.getCreate_time());
            memberResponseDtos.add(memberResponseDto);
        }
        return memberResponseDtos;
    }

    //Transactional어노테이션 클래스 단위로 붙이면 모든 메서드에 각각 Transaction 적용
    //Transactional을 적용하면 한 메서드 단위로 트렌젝션 지정
    @Transactional
    public void memberCreate(MemberRequestDto memberRequestDto) throws IllegalArgumentException{

        Member member = new Member(memberRequestDto.getName(), memberRequestDto.getEmail(), memberRequestDto.getPassword());
        memberRepository.save(member);
//        transaction테스트 롤백 테스트 : 에러나니까 DB에도 안넘어감
//        if(member.getName().equals("kim")){
//            throw new IllegalArgumentException();
//        }
    }

    public MemberResponseDto findById(int id) throws EntityNotFoundException{
        Member member = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("검색하신 ID가 멤버가 없습니다."));//값이 없으면
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setId(member.getId());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setPassword(member.getPassword());
        memberResponseDto.setName(member.getName());
        memberResponseDto.setCreate_time(member.getCreate_time());

        return memberResponseDto;
    }
    public void delete_function(int id) {
        memberRepository.delete(memberRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public void memberUpdate(MemberRequestDto memberRequestDto){
        Member member = memberRepository.findById(memberRequestDto.getId()).orElseThrow(EntityNotFoundException::new);
        member.updateMember(memberRequestDto.getName(), memberRequestDto.getPassword());
        memberRepository.save(member);
    }

}
