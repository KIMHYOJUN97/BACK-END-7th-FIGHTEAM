package com.example.fighteam.user.controller;

import com.example.fighteam.user.domain.dto.JoinRequestDto;
import com.example.fighteam.user.domain.repository.User;
import com.example.fighteam.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/loginUser")
    public String createUserForm(Model model){
        model.addAttribute("useForm", new JoinRequestDto());
        return "user/joinlogin/joinlogin_form";
    }

    @PostMapping("/loginUser")
    public String signUp(@Valid JoinRequestDto joinRequestDto, BindingResult result){

        if(result.hasErrors()){
            return "user/joinlogin/joinlogin_form";
        }
        userService.signUp(joinRequestDto);

        return "redirect:/";

    }




}
