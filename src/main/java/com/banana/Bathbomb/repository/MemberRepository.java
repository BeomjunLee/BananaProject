package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 회원 insert
     */
    public int save(Member member) {
        return jdbcTemplate.update("insert into member(member_uid, member_id) values(member_SEQ.NEXTVAL, ?)", member.getMemberId());
    }

    /**
     * 회원 select
     */
    public Member findOne(int memberUid) {

        return null;
    }

    /**
     * 회원 select by id
     */
    public Member findById(String memberId) {
        List<Member> result = jdbcTemplate.query("select * from member where member_id = ?", memberRowMapper(), memberId);
        if (result.isEmpty()) return null;
        else return result.get(0);
    }

    //회원리스트


    /**
     * 회원정보 select에 필요한 메서드
     */
    private RowMapper<Member> memberRowMapper() {
        return new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                Member member = new Member();
                member.setMemberUid(rs.getInt("member_uid"));
                member.setMemberId(rs.getString("member_id"));
                return member;
            }
        };
    }
}
