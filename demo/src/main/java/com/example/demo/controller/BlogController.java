package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest; // Board 생성용 DTO (toEntity()가 Board 반환)
import com.example.demo.model.service.BlogService;     // Board용 서비스

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    // 목록
    @GetMapping("/board_list")
    public String boardList(Model model) {
        List<Board> list = blogService.findAll();
        model.addAttribute("boards", list); // 뷰에서 ${boards}로 접근
        return "board_list";                // board_list.html
    }

    // 상세/수정 페이지
    @GetMapping("/board_edit/{id}")
    public String boardEdit(@PathVariable Long id, Model model) {
        Optional<Board> opt = blogService.findById(id);
        if (opt.isEmpty()) return "error";
        model.addAttribute("board", opt.get()); // ${board}
        return "board_edit";                    // board_edit.html
    }

    // 등록 (폼: title, content, user, newdate, count, likec 등)
    @PostMapping("/api/boards")
    public String addBoard(@ModelAttribute AddArticleRequest request) {
        blogService.save(request); // request.toEntity()가 Board를 생성해야 함
        return "redirect:/board_list";
    }

    // 수정
    @PutMapping("/api/boards/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        blogService.update(id, request); // Board#update(title, content) 기준
        return "redirect:/board_list";
    }

    // 삭제
    @DeleteMapping("/api/boards/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";
    }
}
