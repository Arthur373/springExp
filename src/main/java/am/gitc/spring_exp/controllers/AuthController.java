package am.gitc.spring_exp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/auth")
public class AuthController {

    @GetMapping(path = {"/", "/login"})
    public ModelAndView getLoginPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("user/login");
        return model;
    }

    @GetMapping("/success")
    public ModelAndView getSuccessPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("user/success");
        return model;
    }
}
