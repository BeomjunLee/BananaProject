package com.banana.Bathbomb.repository;

import com.banana.Bathbomb.domain.ReviewBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReviewBoardRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewBoardRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 리뷰게시판 글 쓰기 insert
     */
    public int insert(ReviewBoard board){
        return jdbcTemplate.update("insert into review_board(rv_board_uid, member_uid, rv_board_writer, rv_board_item, rv_board_title," +
                "rv_board_content, rv_board_file, rv_board_view_count, rv_board_reg_date) values(review_board_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)",
                board.getMemberUid(), board.getRvBoardWriter(), board.getRvBoardItem(), board.getRvBoardTitle(), board.getRvBoardContent(), board.getRvBoardFile(),
                board.getRvBoardViewCount(), board.getRvBoardRegDate());
    }

    /**
     * 리뷰게시판 글 리스트 찾기 select 페이징
     */
    public List<ReviewBoard> selectAll(int startIndex, int pageSize){
        return jdbcTemplate.query("select * from review_board ORDER BY rv_board_uid DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY", reviewBoardRowMapper(),
                startIndex, pageSize);
    }

    /**
     * 리뷰게시판 글 리스트 (베스밤)
     */


    /**
     * 리뷰게시판 글 리스트 (천연비누)
     */

    /**
     * 리뷰게시판 글 읽기 select by uid
     */
    public ReviewBoard selectByUid(int rvBoardUid){
        List<ReviewBoard> result = jdbcTemplate.query("select * from review_board where rv_board_uid = ?", reviewBoardRowMapper(), rvBoardUid);
        if(result.isEmpty()) return null;
        else return result.get(0);
    }

    /**
     * 내가 쓴 리뷰 글 리스트
     */
    public List<ReviewBoard> selectByMemberUid(int memberUid){
        List<ReviewBoard> result = jdbcTemplate.query("select * from review_board where member_uid = ?", reviewBoardRowMapper(), memberUid);
        return result;
    }

    /**
     * 리뷰 글 수정 update
     */
    public int update(ReviewBoard board){
        return jdbcTemplate.update("update review_board set rv_board_item = ?, rv_board_title = ?, rv_board_content = ?, " +
                "rv_board_file = ? where rv_board_uid = ?", board.getRvBoardItem(), board.getRvBoardTitle(),
                board.getRvBoardContent(), board.getRvBoardFile(), board.getRvBoardUid());
    }

    /**
     * 리뷰 글 삭제
     */
    public int delete(int rvBoardUid){
        return jdbcTemplate.update("delete from review_board where rv_board_uid = ?", rvBoardUid);
    }

    /**
     * 조회수 증가
     */
    public int updateViewCount(int rvBoardUid){
        return jdbcTemplate.update("update review_board set rv_board_view_count = rv_board_view_count + 1 where rv_board_uid = ?", rvBoardUid);
    }

    /**
     * 리뷰 게시판 총 글수
     */
    public int totalListCount() {
        return jdbcTemplate.queryForObject("select count(*) from review_board", Integer.class);
    }


    private RowMapper<ReviewBoard> reviewBoardRowMapper(){
        return new RowMapper<ReviewBoard>() {
            @Override
            public ReviewBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReviewBoard board = new ReviewBoard();
                board.setRvBoardUid(rs.getInt("rv_board_uid"));
                board.setMemberUid(rs.getInt("member_uid"));
                board.setRvBoardWriter(rs.getString("rv_board_writer"));
                board.setRvBoardItem(rs.getString("rv_board_item"));
                board.setRvBoardTitle(rs.getString("rv_board_title"));
                board.setRvBoardContent(rs.getString("rv_board_content"));
                board.setRvBoardFile(rs.getString("rv_board_file"));
                board.setRvBoardViewCount(rs.getInt("rv_board_view_count"));
                board.setRvBoardRegDate(rs.getString("rv_board_reg_date"));
                return board;
            }
        };
    }
}
