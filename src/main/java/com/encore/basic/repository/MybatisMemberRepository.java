package com.encore.basic.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
@Mapper  // 구현체가 없어도 mybatits를 통해/ mapper.xml 파일이 들어온다.
@Repository // 빈에 매퍼랑 레포지토리 어노테이션으로 들어간다
public interface MybatisMemberRepository extends MemberRepository{
//    본문에 MybatisRepository에서 사용할 메서드 명세를 정의해야 하나,
//    MemberRepository에서 상속 받고 있으므로, 생략가능
//    실질적인 쿼리 등 구현은 resources/mapper/MemberMapper.xml 파일에 수행

}
