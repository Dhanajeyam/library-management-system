package com.example.lms.repository;

import com.example.lms.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", new BeanPropertyRowMapper<>(Member.class));
    }

    public Member findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM member WHERE id = ?", new BeanPropertyRowMapper<>(Member.class), id);
    }

    public int save(Member member) {
        return jdbcTemplate.update("INERT INTO member (name, phone, registered_date) VALUES (?, ?, ?)",
        member.getName(), member.getPhone(), member.getRegisteredDate());
    }

    public int update(Member member){
        return jdbcTemplate.update("UPDATE member SET name = ?, phone = ?, registered_date = ? WHERE id = ?",
                member.getName(), member.getPhone(), member.getRegisteredDate(), member.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM member WHERE id = ?", id);
    }
}
