package com.banana.Bathbomb.service;

import com.banana.Bathbomb.domain.ReviewBoard;
import com.banana.Bathbomb.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {
    private final ReviewBoardRepository reviewBoardRepository;

    /**
     * 리뷰게시판 글 쓰기 insert
     */
    public int insertBoard(ReviewBoard reviewBoard){
        return reviewBoardRepository.insert(reviewBoard);
    }

    /**
     * 리뷰게시판 글 리스트 찾기 select
     */
    public List<ReviewBoard> findBoardList(int startIndex, int pageSize){
        return reviewBoardRepository.selectAll(startIndex, pageSize);
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
    public ReviewBoard readBoard(int rvBoardUid){
        return reviewBoardRepository.selectByUid(rvBoardUid);
    }

    /**
     * 내가 쓴 리뷰 글 리스트
     */
    public List<ReviewBoard> myBoardList(int memberUid){
        return reviewBoardRepository.selectByMemberUid(memberUid);
    }

    /**
     * 리뷰 글 수정 update
     */
    public int updateBoard(ReviewBoard reviewBoard){
        return reviewBoardRepository.update(reviewBoard);
    }

    /**
     * 리뷰 글 삭제
     */
    public int deleteBoard(int rvBoardUid){
        return reviewBoardRepository.delete(rvBoardUid);
    }


    /**
     * 조회수 증가
     */
    public int updateViewCount(int rvBoardUId){
        return reviewBoardRepository.updateViewCount(rvBoardUId);
    }

    /**
     * 리뷰 게시판 총 글수
     */
    public int totalListCount(){
        return reviewBoardRepository.totalListCount();
    }

}
