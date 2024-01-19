package com.encore.basic.repository;

import com.encore.basic.domain.Member;

import java.util.*;

public interface MemberRepository {

    List<Member> findAll();
    Member save(Member member);
    Optional<Member> findById(int id);
    void delete(Member member);

}
