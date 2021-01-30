package am.gitc.spring_exp.controllers;

import am.gitc.spring_exp.entity.UserEntity;
import am.gitc.spring_exp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers(){
        ModelAndView model = new ModelAndView();
        List<UserEntity> users = userService.getAll();
        model.addObject("users",users);
        model.setViewName("user/home");
        return model;
    }
}
