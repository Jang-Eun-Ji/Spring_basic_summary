package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
@Repository
public class JpaMemberRepository implements MemberRepository{
//    EntityManger는 JPA의 핵심 클래스(객체)
//    Entity의 생명주기를 관리. 데이터베이스와의 모든 상호작용을 책임.
//    엔티티를 대상으로 CRUD하는 기능을 제공
    private final EntityManager entityManager;

    public JpaMemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Member save(Member member) {
        entityManager.persist(member);
//        persist: 저장하는 문법, 전달된 엔티티(멤버)가 EntityMenger(JPA)의 관리상태가 되도록 만들어 주고,
//        트랜잭션이 커밋될 때 데이터베이스에 저장. insert
        return member;
    }

    @Override
    public Optional<Member> findById(int id) {
//        find메서드는 pk를 매개변수로 준다.
//        null로 데이터를 넘기면 런타임에러남,
        Member member = entityManager.find(Member.class, id); //optional객체로 만들수 없음(JPA에 지원이 안됨,<T> 타입이라?)
        return Optional.ofNullable(member); //그래서 여기서 처리해주는겨
    }

    @Override
    public void delete(Member member) {
//      delect 의 경우 remove메소드 사용
//      update의 경우 merge메소드 사용.
    }


    //    pk외의 컬럼으로 조회할때
//    public List<Member> findbyName (String name){
//        List<Member> members = entityManager
//                .createQuery("select m from Member m where m.name = :name", Member.class)
//                .setParameter("name", name).getResultList();
//        return members;
//    }
    @Override
    public List<Member> findAll() {
//      jpql: jpa의 객체 지향 쿼리문법
//        장점: DB에 따라 문법이 달라지지 않는 객체 지향언어, 컴파일타임에서 check.(SpringDataJpa의 @Query 기능)
//        단점:DB고유의 기능과 성능을 극대화하기는 어려움
//                                                                 m은 멤버의 약자, p는 의 약자 select m.name, m.email도 가능
        List<Member> members = entityManager.createQuery("select m from Member m",Member.class).getResultList();
        return members;
    }
}
