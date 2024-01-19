package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

@Repository
public class JdbcMemberRepository implements MemberRepository{
    // 스키마 별로 데이터를 담기 위해 Jdbc레포, Jpa레포 등을 만듦
//    Datasource는 DB와 JDBC에서 사용하는 DB연결 드라이버 객체, dataSource는 스프링이 만든 연결 정보인데 이걸 통해 DB와 연결한다
    @Autowired
    private DataSource dataSource;
    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from member";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                LocalDateTime now = resultSet.getTimestamp("create_time").toLocalDateTime();
                System.out.println(now.toString());
                Member member = new Member(name, email, password);
                member.setId(id);
//                member.setCreate_time(now);
                members.add(member);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return members;
    }

    @Override
    public Member save(Member member) {
        try {
            Connection connection = dataSource.getConnection();
            //DB정보를 가져오고 DB 서버 connection정보를 꺼내옴, 그래서 sql에 스트링으로 입력이 가능함
            String sql = "insert into member(name, email, password) values(?, ?, ?)"; // ?에 변수가 들어옴
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Member> findById(int input) {
        Member member = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select name, email, password, create_time from member where id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(input));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            LocalDateTime now = resultSet.getTimestamp("create_time").toLocalDateTime();
            member = new Member(name, email, password);
            member.setId(id);
//            member.setCreate_time(now);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(member);
    }

    @Override
    public void delete(Member member) {

    }


}
