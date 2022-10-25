package com.whk.view;

import com.whk.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ServerService service;

    @Autowired
    public void setService(ServerService service) {
        this.service = service;
    }

    /**
     * 主页
     * @return
     * @throws Exception
     */
    @GetMapping("/index")
    public ModelAndView index() throws Exception{
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/index");
        modelAndView.addObject("loginUser", "whk");
        return modelAndView;
    }

    /**
     * 登录页
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    /**
     * 服务器
     * @return
     */
    @GetMapping("/server")
    public ModelAndView server() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("servers", service.getServersByPage(0, 1, 20));
        modelAndView.setViewName("/server");
        return modelAndView;
    }


}
