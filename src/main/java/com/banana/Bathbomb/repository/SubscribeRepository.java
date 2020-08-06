package com.banana.Bathbomb.repository;
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
       return jdbcTemplate.update("insert into subscribe(sb_uid, member_uid, sb_delivery_address, sb_delivery_memo, sb_price" +
                       "sb_reg_date, sb_repeat_cnt, sb_delevery_status, sb_cancel_status, sb_cancel_date) " +
                       "values(subscribe_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
               subscribe.getMemberUid(), subscribe.getSbDeliveryAddress(), subscribe.getSbDeliveryMemo(), subscribe.getSbPrice(),
               subscribe.getSbRegDate(), subscribe.getSbMonthChk(), subscribe.getSbDeliveryStatus(), subscribe.getSbCancelStatus(), subscribe.getSbCancelDate());
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
     * 구독 취소 delete
     */



    /**
     * 다음달도 구독 했을 경우
     */
    public int update(){
        return 0;
    }



    /**
     * 구독정보 select에 필요한 메서드
     */
    private RowMapper<Subscribe> subscribeRowMapper() {
        return new RowMapper<Subscribe>() {
            @Override
            public Subscribe mapRow(ResultSet rs, int rowNum) throws SQLException {
                Subscribe subscribe = new Subscribe();
                subscribe.setMemberUid(rs.getInt("member_uid"));
                return subscribe;
            }
        };
    }
}
