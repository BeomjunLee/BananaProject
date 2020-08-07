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
     * 회원 가입 insert
     */
    public int insert(Member member) {
        return jdbcTemplate.update("insert into member(member_uid, member_id) values(member_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)",
                member.getMemberId(), member.getMemberPw(), member.getMemberName(), member.getMemberPhoneNum(),
                member.getMemberEmail(), member.getMemberGender(), member.getMemberRegDate());
    }

    /**
     * 회원 정보 select by uid
     */
    public Member selectByUid(int memberUid) {
        List<Member> result = jdbcTemplate.query("select * from member where member_uid = ?", memberRowMapper(), memberUid);
        if (result.isEmpty()) return null;
        else return result.get(0);
    }

    /**
     * 회원 정보 select by 아이디
     */
    public Member selectById(String memberId) {
        List<Member> result = jdbcTemplate.query("select * from member where member_id = ?", memberRowMapper(), memberId);
        if (result.isEmpty()) return null;
        else return result.get(0);
    }

    /**
     * 회원 수정 update
     */
    public int update(Member member){
        return jdbcTemplate.update("update member set member_pw = ?, member_name = ?, member_phone_num = ?, member_email = ?" +
                "member_gender = ? where member_uid = ?", member.getMemberPw(), member.getMemberName(), member.getMemberPhoneNum(),
                member.getMemberEmail(), member.getMemberGender(), member.getMemberUid());
    }

    /**
     * 회원 탈퇴 delete
     */
    public int delete(int memberUid){
        return 0;
    }

    /**
     * 전체 회원 리스트 select
     */
    public List<Member> selectAll(){
        return null;
    }

    /**
     * 회원 아이디 찾기
     */

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
                member.setMemberPw(rs.getString("member_pw"));
                member.setMemberName(rs.getString("member_name"));
                member.setMemberPhoneNum(rs.getString("member_phone_num"));
                member.setMemberEmail(rs.getString("member_email"));
                member.setMemberGender(rs.getString("member_gender")); //남성, 여성
                member.setMemberRegDate(rs.getString("member_reg_date"));
                return member;
            }
        };
    }
}
