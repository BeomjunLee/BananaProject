package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CategoryRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 카테고리 추가
     */
    public int insert(Category category) {
        return jdbcTemplate.update("insert into category(category_uid, category_name, category_sort) values(category_SEQ.NEXTVAL, ?, ?)",
                category.getCategoryName(), category.getCategorySort());

    }

    /**
     * 최근에 insert된 pk값 뽑기(order by는 제일 나중에 실행되므로 괄호로 먼저 실행시키고 rownum해야됨)
     */
    public int selectUid(){
        return jdbcTemplate.queryForObject("select category_uid from( select category_uid from category ORDER BY category_uid DESC) " +
                "where rownum <= 1", categoryIntegerMapper());
    }

    /**
     * 카테고리 검색
     */
    private RowMapper<Integer> categoryIntegerMapper() {
        return new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("category_uid");
            }
        };
    }

}
