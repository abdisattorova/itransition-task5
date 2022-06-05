package com.example.chatapp.controller;

import com.example.chatapp.entity.User;
import com.example.chatapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping("/start")
    public String loginPage(Model model){
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginPage(HttpSession session, User userDto) {

        User user = authService.findByName(userDto);
        if (user != null) {
            session.setAttribute("authUser", user);
            return "redirect:/";
        }

        return "redirect:/start";
    }

    @GetMapping("/")
    public String homePage(HttpSession session){
        User user = (User) session.getAttribute("authUser");
        if (user!=null){
            return "home";
        }
        return "redirect:/start";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/start";
    }

}
