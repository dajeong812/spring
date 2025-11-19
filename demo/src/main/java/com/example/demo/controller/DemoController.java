package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.service.TestService; // 서비스 클래스
import com.example.demo.model.domain.TestDB;

@Controller // 컨트롤러 어노테이션
public class DemoController {

    @Autowired
    private TestService testService; // 서비스 객체 주입

    @GetMapping("/hello") // GET 요청
    public String hello(Model model) {
        model.addAttribute("data", "방갑습니다."); 
        return "hello"; // hello.html 렌더링
    }

    @GetMapping("/about_detailed")
    public String about() {
        return "about_detailed";
    }

    @GetMapping("/return_index")
    public String return_index() {
        return "index";
    }

    @GetMapping("/test1")
    public String thymeleaf_test1(Model model) {
        model.addAttribute("data1", "<h2> 방갑습니다 </h2>");
        model.addAttribute("data2", "태그의 속성 값");
        model.addAttribute("link", "01");  // 숫자 대신 문자열로
        model.addAttribute("name", "홍길동");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", "002");
        return "thymeleaf_test1";
    }

    // DB 테스트 매핑
@GetMapping("/testdb")
public String getAllTestDBs(Model model) {
    List<TestDB> testList = new ArrayList<>();

    // DB에 있는 이름 그대로 넣어야 함
    TestDB user1 = testService.findByName("홍길동");
    TestDB user2 = testService.findByName("아저씨");
    TestDB user3 = testService.findByName("꾸러기");

    if(user1 != null) testList.add(user1);
    if(user2 != null) testList.add(user2);
    if(user3 != null) testList.add(user3);

    model.addAttribute("dataList", testList);
    return "testdb";
}

    @GetMapping("/article_list")
    public String article_list() {
    return "article_list";
    }

}
