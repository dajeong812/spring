package com.example.demo.model.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter // setter는 없음(무분별한 변경 x)
@Entity
@Table(name = "article") // 테이블 이름 지정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title = "";

    @Column(name = "content", nullable = false)
    private String content = "";


    @Builder // 생성자에 빌더 패턴 적용
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
