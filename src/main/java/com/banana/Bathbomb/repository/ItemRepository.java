package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.Item;
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
public class ItemRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 상품 등록
     */
    public int insert(Item item){
        return jdbcTemplate.update("insert into item(item_uid, category_uid, item_name, item_price, item_content, item_image, item_stock) " +
                        "values(item_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)",
                item.getCategoryUid(), item.getItemName(), item.getItemPrice(), item.getItemContent(), item.getItemImage(), item.getItemStock());

    }

    /**
     * 상품 삭제
     */
    public int delete(int itemUid){
        return 0;
    }

    /**
     * 상품 수정
     */
    public int update(int itemUid){
        return 0;
    }
    
    /**
     * 상품 검색 by uid
     */
    public List<Item> selectByUid(int itemUid){
        List<Item> result = jdbcTemplate.query("select * from item where item_uid = ?", itemRowMapper(), itemUid);
        return result;
    }

    /**
     * 전체 상품 검색
     */

    public List<Item> selectAll(int startIndex, int pageSize){
        List<Item> result = jdbcTemplate.query("select * from item ORDER BY item_uid DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY", itemRowMapper(), startIndex, pageSize);
        return result;
    }

    /**
     * 상품 검색  by 검색창
     */
    public List<Item> selectByName(String itemName){
        List<Item> result = jdbcTemplate.query("select * from item where item_name like '%'?'%'", itemRowMapper(), itemName);
        return result;
    }

    /**
     * 카테고리로 검색
     */
    public List<Integer> selectByCategory(String name, String sort){
        List<Integer> result = jdbcTemplate.query("select distinct i.item_uid from item i, category c where i.category_uid = c.category_uid " +
                        "and c.category_name = ? or c.category_sort = ?",
                itemIntegerMapper(), name, sort);
        return result;
    }


    /**
     * 쇼핑몰 총 글수
     */
    public int totalListCount() {
        return jdbcTemplate.queryForObject("select count(*) from item", Integer.class);
    }

    /**
     * 상품정보에 필요한 메서드
     */
    private RowMapper<Item> itemRowMapper() {
        return new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                Item item = new Item();
                item.setItemUid(rs.getInt("item_uid"));
                item.setCategoryUid(rs.getInt("category_uid"));
                item.setItemName(rs.getString("item_name"));
                item.setItemPrice(rs.getInt("item_price"));
                item.setItemContent(rs.getString("item_content"));
                item.setItemImage(rs.getString("item_image"));
                item.setItemStock(rs.getInt("item_stock"));
                return item;
            }
        };
    }

    /**
     * 카테고리 검색
     */
    private RowMapper<Integer> itemIntegerMapper() {
        return new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("item_uid");
            }
        };
    }
}
