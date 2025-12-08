package com.example.demo.model.service;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.demo.model.domain.Board;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddArticleRequest {

    private String title;
    private String content;
    private String user;
    private String count;
    private String newdate;
    private String likec;


    public Board toEntity() {

        // "MM월dd일" 형식으로 오늘 날짜 만들기
        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("MM월dd일"));

        return Board.builder()
                .title(title)
                .content(content)
                .user(user)     // 세션에서 받아오는 email

                .count("0")     // 기본 조회수
                .likec("0")     // 기본 좋아요
                .newdate(today) // 오늘 날짜

                .build();
    }
}
