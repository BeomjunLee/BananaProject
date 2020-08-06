package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.Member;
import com.banana.Bathbomb.domain.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubscribeRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SubscribeRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 구독 insert
     */
    public int save(Subscribe subscribe){
       return jdbcTemplate.update("insert into subscribe(subscribe_uid, member_uid, subscribe_status) values(subscribe_SEQ.NEXTVAL, ?, ?)",
                subscribe.getMemberUid(), subscribe.getSubscribeStatus());
    }

    /**
     * 구독 select by member_uid
     */
    public Subscribe findOne(int member_uid){
        List<Subscribe> result = jdbcTemplate.query("select * from subscribe where member_uid = ?", subscribeRowMapper(), member_uid);
        if (result.isEmpty()) return null;
        else return result.get(0);
    }





    /**
     * 구독정보 select에 필요한 메서드
     */
    private RowMapper<Subscribe> subscribeRowMapper() {
        return new RowMapper<Subscribe>() {
            @Override
            public Subscribe mapRow(ResultSet rs, int rowNum) throws SQLException {
                Subscribe subscribe = new Subscribe();
                subscribe.setSubscribeUid(rs.getInt("subscribe_uid"));
                subscribe.setMemberUid(rs.getInt("member_uid"));
                subscribe.setSubscribeStatus(rs.getString("subscribe_status"));
                return subscribe;
            }
        };
    }
}
