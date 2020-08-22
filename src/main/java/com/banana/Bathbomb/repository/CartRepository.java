package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CartRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CartRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 카트에 담기(insert)
     */
    public int insert(Cart cart){
        return jdbcTemplate.update("insert into cart(cart_uid, item_uid, member_uid, cart_item_price, cart_item_quantity) " +
                "values cart_SEQ.NEXTVAL, ?, ?, ?, ?", cart.getItemUid(), cart.getMemberUid(), cart.getCartItemPrice(), cart.getCartItemQuantity());
    }

    /**
     * 카트 삭제
     */
    public int deleteByUid(int cartUid, int memberUid){
        return jdbcTemplate.update("delete from cart where cart_uid = ? and member_uid = ?", cartUid, memberUid);
    }

    /**
     * 카트 select all by 회원uid
     */
    public List<Cart> selectAllByMemberUid(int memberUid){
        List<Cart> result = jdbcTemplate.query("select * from cart where member_uid = ?", cartRowMapper(), memberUid);
        return result;
    }



    private RowMapper<Cart> cartRowMapper(){
        return new RowMapper<Cart>() {
            @Override
            public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cart cart = new Cart();
                cart.setCartUid(rs.getInt("cart_uid"));
                cart.setItemUid(rs.getInt("item_uid"));
                cart.setMemberUid(rs.getInt("member_uid"));
                cart.setCartItemPrice(rs.getInt("cart_item_price"));
                cart.setCartItemQuantity(rs.getInt("cart_item_quantity"));
                return cart;
            }
        };
    }
}
