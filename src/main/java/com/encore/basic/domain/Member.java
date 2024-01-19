package com.encore.basic.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

// 이렇게 간단하게 만드는게 어노테이션임// @붙은거
@Getter // entity역할인데 - 데이터 베이스의 한 로우를 하나의 객체로 만드는 역할임
//@AllArgsConstructor //모든 매개변수를 넣은 생성자

@Entity //spa를 쓰고 싶을때 사용
@NoArgsConstructor // 기본 생성자 만들어줌
//entity어노테이션을 통해 mariaDB의 테이블 및 컬럼을 자동생성
//class명은 테이블명, 변수명은 컬럼명
public class Member { //DB의 들어갈 5개의 객체임
    @Setter
    @Id //pk설정
    //identity = auto_increment설정, auto = JPA구현체가 자동으로 적절한 키생성 전략 선택.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    string은 DB의 varchar로 변환 ,column설정 안하면 255설정으로 됨
    private String name;
    @Column(nullable = false, length = 50) //네임 옵션을 통해 DB의 컬럼명 별도 지정 가능
    private String email;
    @Column(nullable = false, length = 50)
    private String password;
//    @Setter
    @Column(name = "created_time")// name옵션을 통해 DB의 컬럼명 별도 지정 가능
    @CreationTimestamp
    private LocalDateTime create_time; // 자바 코드 이름
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    public Member(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public void updateMember(String name, String password){
        this.name = name;
        this.password = password;
    }
}
