package com.example.demo.controller;

import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;


    // 게시글 수정 폼
    @GetMapping("/article_edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        var opt = blogService.findById(id);
        if (opt.isEmpty()) {
            return "error"; // 없는 글이면 에러 페이지
        }
        model.addAttribute("article", opt.get());
        return "article_edit"; // templates/article_edit.html
    }


    // 게시글 수정 저장
    @PostMapping("/article_update")
    public String update(@RequestParam Long id, AddArticleRequest req) {
        blogService.update(id, req);
        return "redirect:/article_list";
    }


    // 게시글 작성 저장
    @PostMapping("/article_write")
    public String write(AddArticleRequest req) {
        blogService.save(req);
        return "redirect:/article_list";
    }


    // 게시글 삭제
    @PostMapping("/article_delete/{id}")
    public String delete(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/article_list";
    }


    // 삭제 (게시판 글 삭제)
    @PostMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/board_list";    // 삭제 후 목록으로 이동
    }


    // 게시판 글쓰기 페이지
    @GetMapping("/board_write")
    public String boardWrite() {
        return "board_write";
    }


    // 글쓰기 게시판 저장
    @PostMapping("/api/boards")
    public String addboards(@ModelAttribute AddArticleRequest request,
                            HttpSession session) {

        String loginUser = (String) session.getAttribute("email");

        if (loginUser == null) {
            return "redirect:/member_login";
        }

        request.setUser(loginUser);
        blogService.save(request);
        return "redirect:/board_list";
    }


    // 게시판 목록 (세션 체크 + 페이징 + 검색)
    @GetMapping("/board_list")
    public String board_list(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "") String keyword,
                             HttpSession session) {

        String userId = (String) session.getAttribute("userId");
        String email  = (String) session.getAttribute("email");

        if (userId == null) {
            return "redirect:/member_login";
        }

        System.out.println("세션 userId: " + userId);

        int pageSize = 3;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Board> list;

        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable);
        } else {
            list = blogService.searchByKeyword(keyword, pageable);
        }

        int startNum = page * pageSize + 1;

        model.addAttribute("startNum", startNum);
        model.addAttribute("email", email);

        model.addAttribute("boards", list.getContent());
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "board_list";
    }


    // 게시판 글 상세보기
    @GetMapping("/board_view/{id}")
    public String boardView(Model model,
                            @PathVariable Long id,
                            HttpSession session) {

        String loginEmail = (String) session.getAttribute("email");

        Optional<Board> board = blogService.findById(id);
        if (board.isEmpty()) {
            return "/error_page/article_error";
        }

        model.addAttribute("boards", board.get());
        model.addAttribute("loginEmail", loginEmail);

        return "board_view";
    }


    // 게시판 글 수정 폼
    @GetMapping("/board_edit/{id}")
    public String boardEditForm(@PathVariable Long id, Model model) {

        Optional<Board> board = blogService.findById(id);

        if (board.isEmpty()) {
            return "/error_page/article_error";
        }

        model.addAttribute("board", board.get());
        return "board_edit";
    }


    // 게시판 글 수정 저장
    @PostMapping("/board_edit/{id}")
    public String boardEdit(@PathVariable Long id,
                            AddArticleRequest request) {

        blogService.update(id, request);
        return "redirect:/board_list";
    }
}
