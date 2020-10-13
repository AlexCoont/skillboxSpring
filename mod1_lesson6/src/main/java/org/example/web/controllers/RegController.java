package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegController {

    private final Logger logger = Logger.getLogger(RegController.class);
    private final LoginService loginService;

    @Autowired
    public RegController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(Model model) {
        logger.info("GET /registration returns reg_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return "reg_page";
    }

    @PostMapping()
    public String registration(LoginForm loginForm){
        if (loginService.registration(loginForm)){
            logger.info("registration OK redirect to book shelf");
            return "redirect:/books/shelf";
        } else {
            logger.info("registration FAIL redirect back to registration");
            return "redirect:/registration";
        }
    }
}
