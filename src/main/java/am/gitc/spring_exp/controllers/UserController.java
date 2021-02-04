package am.gitc.spring_exp.controllers;

import am.gitc.spring_exp.entity.UserEntity;
import am.gitc.spring_exp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers() {
        ModelAndView model = new ModelAndView();
        List<UserEntity> users = this.userService.getAll();
        model.addObject("users", users);
        model.setViewName("user/users");
        return model;
    }

    @GetMapping("/user-create")
    public ModelAndView createUserForm() {
        ModelAndView model = new ModelAndView();
        model.addObject("user", new UserEntity());
        model.setViewName("user/user-create");
        return model;
    }

    @PostMapping("/user-create")
    public ModelAndView createUser(UserEntity user) {
        ModelAndView model = new ModelAndView();
        if (this.userService.getUserByEmail(user.getEmail()) == null) {
            this.userService.save(user);
        }
        model.setViewName("redirect:/users");
        return model;
    }

    @GetMapping("/user-update/{id}")
    public ModelAndView updateUserForm(@PathVariable("id") ObjectId id) {
        ModelAndView model = new ModelAndView();
        Optional<UserEntity> user = this.userService.getUserById(id);
        model.addObject("user", user.get());
        model.setViewName("user/user-update");
        return model;
    }

    @PostMapping("/user-update")
    public ModelAndView updateUser(UserEntity user) {
        ModelAndView model = new ModelAndView();
        this.userService.save(user);
        model.setViewName("redirect:/users");
        return model;
    }

    @GetMapping("/user-delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") ObjectId id) {
        ModelAndView model = new ModelAndView();
        this.userService.deleteUserById(id);
        model.setViewName("redirect:/users");
        return model;
    }


}
