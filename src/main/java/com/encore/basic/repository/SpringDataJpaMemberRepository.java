package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// spring data jpa의 기본기능을 쓰기 위해서는 JpaRepository를 상속해야한다
//상속시에 entity명과 해당 entity의 pk타입을 명시
//MemberRepository, JpaRepository - 안에 findAll이 있음
//실질적인 구현클래스와 스펙은 SimpleJpaRepository 클래스에 있고,
//실질적인 구동상황에서 hiberante구현체에 동작위임.
//이게 제일 중요하다~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~JPA의 hibernate 처럼 구현체 역할 해줌<도매인, PK타입>
public interface SpringDataJpaMemberRepository extends MemberRepository, JpaRepository<Member, Integer> {//타입만 지정해주면 객체조립이 자동으로 됨
}//SpringDataJpaMemberRepository만 쓰면 MemberRepository 필요없고  JpaRepository만쓰기,
// 뭐 추가 없이 원래 다 있단다
