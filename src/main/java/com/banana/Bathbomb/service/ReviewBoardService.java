package com.banana.Bathbomb.service;

import com.banana.Bathbomb.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {
    private final ReviewBoardRepository reviewBoardRepository;
}
