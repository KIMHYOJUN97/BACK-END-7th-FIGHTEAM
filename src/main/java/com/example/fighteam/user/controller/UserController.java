package com.example.fighteam.user.controller;

import com.example.fighteam.user.domain.dto.LoginRequestDto;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Member;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute LoginRequestDto loginRequestDto) {
        System.out.println("UserController.save");
        System.out.println("loginRequestDto = " + loginRequestDto);

        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/member/")
    public String findAll(Model model) {
        List<LoginRequestDto> loginRequestDtoList = userService.findAll();
        model.addAttribute("memberList", loginRequestDtoList);
        return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@pathVariable String id, Model model) {
        LoginRequestDto memberLoginRequestDtoList = userService.findById(id);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userId");
        LoginRequestDto lRD = userService.updateForm(email);
        model.addAttribute(("updateMember", memberLoginRequestDtoList);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute LoginRequestDto memberLoginRequestDtoList) {
        userService.update(memberLoginRequestDtoList);
        return "redirect:/member/" + memberLoginRequestDtoList.getId();
    }
}
