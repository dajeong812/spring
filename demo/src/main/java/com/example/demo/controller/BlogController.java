package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.example.demo.model.service.BlogService; // 서비스 클래스
import com.example.demo.model.domain.Article;

@Controller // 컨트롤러 어노테이션
public class BlogController {

    @Autowired
    BlogService blogService; 

    // @GetMapping("/article_list")
    // public String article_list() {
    // return "article_list";
    // }


    @GetMapping("/article_list") // 게시판 링크 지정
    public String article_list(Model model) {
        List<Article> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("articles", list); // 모델에 추가

        return "article_list"; // .HTML 연결
        }

}