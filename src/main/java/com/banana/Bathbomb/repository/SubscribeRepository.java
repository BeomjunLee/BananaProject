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
     * 구독 완료 insert
     */
    public int insert(Subscribe subscribe){
       return jdbcTemplate.update("insert into subscribe(sb_uid, member_uid, sb_delivery_address, sb_delivery_memo, sb_price, " +
                       "sb_reg_date, sb_delivery_status, sb_cancel_status, sb_cancel_date) " +
                       "values(subscribe_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)",
               subscribe.getMemberUid(), subscribe.getSbDeliveryAddress(), subscribe.getSbDeliveryMemo(), subscribe.getSbPrice(),
               subscribe.getSbRegDate(), subscribe.getSbDeliveryStatus(), subscribe.getSbCancelStatus(), subscribe.getSbCancelDate());
    }

     /**
     * 구독 정보 select by member_uid
     */
    public Subscribe selectByUid(int member_uid){
        List<Subscribe> result = jdbcTemplate.query("select * from subscribe where member_uid = ? and sb_cancel_status = '구독중'", subscribeRowMapper(), member_uid);
        if (result.isEmpty()) return null;
        else return result.get(0);
    }


    /**
     * 구독 취소 delete
     */
    public int delete(String status, String date, int uid){
        return jdbcTemplate.update("update subscribe set sb_cancel_status = ?, sb_cancel_date = ? where member_uid = ? and sb_cancel_status = '구독중'",
                status, date, uid);
    }


    /**
     * 구독정보 select에 필요한 메서드
     */
    private RowMapper<Subscribe> subscribeRowMapper() {
        return new RowMapper<Subscribe>() {
            @Override
            public Subscribe mapRow(ResultSet rs, int rowNum) throws SQLException {
                Subscribe subscribe = new Subscribe();
                subscribe.setSbUid(rs.getInt("sb_uid"));
                subscribe.setMemberUid(rs.getInt("member_uid"));
                subscribe.setSbDeliveryAddress(rs.getString("sb_delivery_address"));
                subscribe.setSbDeliveryMemo(rs.getString("sb_delivery_memo"));
                subscribe.setSbPrice(rs.getInt("sb_price"));
                subscribe.setSbRegDate(rs.getString("sb_reg_date"));
                subscribe.setSbDeliveryStatus(rs.getString("sb_delivery_status"));
                subscribe.setSbCancelStatus(rs.getString("sb_cancel_status"));
                subscribe.setSbCancelDate(rs.getString("sb_cancel_date"));
                return subscribe;
            }
        };
    }
}
