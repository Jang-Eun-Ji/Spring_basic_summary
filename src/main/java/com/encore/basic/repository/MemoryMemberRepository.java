package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository //빈 컨테이너에 넣는 역할, 싱글톤을 사용하기 위함
public class MemoryMemberRepository implements MemberRepository{ // DB에 진짜 붙는거
    private final List<Member> memberDB; // List 데이터 베이스 역할임
    public MemoryMemberRepository(){
        memberDB = new ArrayList<>();
    }
    static  int total_id;

    //    멤버가 있을지 없을지 모르면 optional처리를 하지만 있다고 가정하자
    @Override
    public List<Member> findAll(){
        return memberDB;
    }
    @Override
    public Member save(Member member){
        total_id +=1;
        LocalDateTime now = LocalDateTime.now();
        member.setId(total_id);
//        member.setCreate_time(now);
        memberDB.add(member);
        return member;
    }

    @Override
    public Optional<Member> findById(int id) {
        for(Member member : memberDB){
            if(member.getId() == id) return Optional.of(member);
        }
        return Optional.empty();
    }

    @Override
    public void delete(Member member) {

    }


}
