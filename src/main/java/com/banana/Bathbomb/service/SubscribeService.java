package com.banana.Bathbomb.service;
import com.banana.Bathbomb.domain.Subscribe;
import com.banana.Bathbomb.repository.MemberRepository;
import com.banana.Bathbomb.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final MemberRepository memberRepository;

    /**
     * 구독 신청
     */
    public int doSubscribe(Subscribe subscribe){
        //구독 처리 완료
        return subscribeRepository.save(subscribe); //성공1 실패0
    }

    /**
     * 구독 찾기 by member_uid
     */
    public Subscribe findSubscribe(int memberUid){
        return subscribeRepository.findOne(memberUid);
    }

}
