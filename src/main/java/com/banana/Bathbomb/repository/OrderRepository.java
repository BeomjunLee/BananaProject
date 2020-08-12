package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OrderRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 주문 신청
     */
    public int insert(Order order){
        return 0;
    }

    /**
     * 주문 취소
     */
    public int update(Order order){
        return 0;
    }
}
