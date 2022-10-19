package com.whk.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("admin")
public class AdminViewController {

    @GetMapping("/index")
    public ModelAndView index() throws Exception{
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/index");
        modelAndView.addObject("loginUser", "whk");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }




}
