package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 내 주문 목록
     */
    public List<Order> selectAll(int memberUid){
        List<Order> result = jdbcTemplate.query("select * from orders where member_uid = ? order by order_uid desc", orderRowMapper(), memberUid);
        if(result.isEmpty()) return null;
        else return result;
    }

    /**
     * 주문 신청
     */
    public int insert(Order order){
        return jdbcTemplate.update("insert into orders(order_uid, member_uid, order_item_name, order_delivery_address, order_delivery_memo, " +
                "order_delivery_status, order_price, order_reg_date, order_cancel_status, order_cancel_date) " +
                "values (orders_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)", order.getMemberUid(), order.getOrderItemName(),
                order.getOrderDeliveryAddress(), order.getOrderDeliveryMemo(), order.getOrderDeliveryStatus(), order.getOrderPrice(),
                order.getOrderRegDate(), order.getOrderCancelStatus(), order.getOrderCancelDate());
    }

    /**
     * 주문 취소
     */
    public int update(Order order){
        return jdbcTemplate.update("update orders set order_cancel_status = ?, order_cancel_date = ? where order_uid = ?",
                order.getOrderCancelStatus(), order.getOrderCancelDate(), order.getOrderUid());
    }


    private RowMapper<Order> orderRowMapper(){
        return new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                Order order = new Order();
                order.setOrderUid(rs.getInt("order_uid"));
                order.setMemberUid(rs.getInt("member_uid"));
                order.setOrderItemName(rs.getString("order_item_name"));
                order.setOrderDeliveryAddress(rs.getString("order_delivery_address"));
                order.setOrderDeliveryMemo(rs.getString("order_delivery_memo"));
                order.setOrderDeliveryStatus(rs.getString("order_delivery_status"));
                order.setOrderPrice(rs.getInt("order_price"));
                order.setOrderRegDate(rs.getString("order_reg_date"));
                order.setOrderCancelStatus(rs.getString("order_cancel_status"));
                order.setOrderCancelDate(rs.getString("order_cancel_date"));
                return order;
            }
        };
    }

}
