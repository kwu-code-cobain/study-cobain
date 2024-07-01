package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService; // BoardService를 자동으로 주입받음

    // 게시물 작성 페이지를 반환하는 메서드
    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm() {
        return "boardwrite"; // boardwrite.html 파일을 반환
    }

    // 게시물 작성 처리를 하는 메서드
    @PostMapping("/board/writepro")
    public String boardwritepro(Board board) {
        boardService.write(board); // 전달받은 Board 객체를 저장
        return "redirect:/board/list"; // 게시물 목록 페이지로 리다이렉트
    }

    // 게시물 목록 페이지를 반환하는 메서드
    @GetMapping("/board/list")
    public String boardlist(Model model) {
        model.addAttribute("list", boardService.boardlist()); // 게시물 목록을 모델에 추가
        return "boardlist"; // boardlist.html 파일을 반환
    }

    // 특정 게시물 상세 페이지를 반환하는 메서드
    @GetMapping("board/view")
    public String boardView(Model model, @RequestParam(name = "id") Integer id)  {
        model.addAttribute("board", boardService.boardView(id)); // id로 조회한 게시물을 모델에 추가
        return "boardview"; // boardview.html 파일을 반환
    }

    // 게시물 삭제 처리를 하는 메서드
    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id") int id) {
        boardService.boardDelete(id); // id로 게시물 삭제
        return "redirect:/board/list"; // 게시물 목록 페이지로 리다이렉트
    }

    // 게시물 수정 페이지를 반환하는 메서드
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("board", boardService.boardView(id)); // id로 조회한 게시물을 모델에 추가
        return "boardmodify"; // boardmodify.html 파일을 반환
    }

    // 게시물 수정 처리를 하는 메서드
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {
        Board boardTemp = boardService.boardView(id); // 기존 게시물 조회
        boardTemp.setTitle(board.getTitle()); // 제목 수정
        boardTemp.setContent(board.getContent()); // 내용 수정
        boardService.write(boardTemp); // 수정된 게시물 저장
        return "redirect:/board/list"; // 게시물 목록 페이지로 리다이렉트
    }
}
