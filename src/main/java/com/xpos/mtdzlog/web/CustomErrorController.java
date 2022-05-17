package com.xpos.mtdzlog.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    
    @ExceptionHandler(Throwable.class)
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String statusMsg = status.toString();
        
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(statusMsg));
        Integer randNum = 0;
        while (randNum < 1 || randNum > 8) {
        	randNum = (int)((Math.random()*10000)%10);
        }
        model.addAttribute("msg", statusMsg + " " + httpStatus.getReasonPhrase());
        model.addAttribute("randNum", randNum);
        
        return "error";
    }
}