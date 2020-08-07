package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.ReviewBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewBoardRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewBoardRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 리뷰 글 작성 insert
     */
    public int insert(ReviewBoard reviewBoard){
        return 0;
    }

    /**
     * 모든 리뷰 글 리스트 select
     */
    public List<ReviewBoard> selectAll(){
        return null;
    }

    /**
     * 리뷰 글 읽기 select by rv_board_uid
     */
    public ReviewBoard selectByUid(int rvBoardUid){
        return null;
    }

    /**
     * 리뷰 글 수정 update
     */
    public int update(ReviewBoard reviewBoard){
        return 0;
    }



}
