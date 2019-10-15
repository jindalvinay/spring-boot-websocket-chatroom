package com.cfmonkey.udacity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Health Check
     */
    @GetMapping("/healthCheck")
    public @ResponseBody String healthCheck() {
        SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return "Service up: " + time_formatter.format(System.currentTimeMillis());
    }

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        //if "/login" is used instead of "login", endpoint works fine when app is run from IDE
        //but does NOT run when packaged and executed with jar and show error message
        return new ModelAndView("login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
        //TODO-DONE: add code for login to chatroom.

        //redirect to login page if username is missing
        if(username == null || username.trim() == ""){
            return new ModelAndView("login");
        }

        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addObject("username", username);

        //System.out.println("username: " + username);
        modelAndView.addObject("url", "chat/username");
        return modelAndView;
    }
}
