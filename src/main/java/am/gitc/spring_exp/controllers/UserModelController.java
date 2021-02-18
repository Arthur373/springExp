package am.gitc.spring_exp.controllers;

import am.gitc.spring_exp.entity.UserModel;
import am.gitc.spring_exp.services.UserServ;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/api/v1/")
public class UserModelController {

    private final UserServ userServ;


    public UserModelController(UserServ userServ) {
        this.userServ = userServ;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers() {
        ModelAndView model = new ModelAndView();
        List<UserModel> users = this.userServ.getAll();
        model.addObject("users", users);
        model.setViewName("user/users");
        return model;
    }

    @GetMapping("/user-create")
    public ModelAndView createUserForm() {
        ModelAndView model = new ModelAndView();
        model.addObject("user", new UserModel());
        model.setViewName("user/user-create");
        return model;
    }

    @PostMapping("/user-create")
    public ModelAndView createUser(UserModel user) {
        ModelAndView model = new ModelAndView();
        if (this.userServ.getUserByEmail(user.getEmail()) == null) {
            this.userServ.save(user);
        }
        model.setViewName("redirect:/users");
        return model;
    }

    @GetMapping("/user-update/{id}")
    public ModelAndView updateUserForm(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        Optional<UserModel> user = this.userServ.getUserById(id);
        model.addObject("user", user.get());
        model.setViewName("user/user-update");
        return model;
    }

    @PostMapping("/user-update")
    public ModelAndView updateUser(UserModel user) {
        ModelAndView model = new ModelAndView();
        this.userServ.update(user);
        model.setViewName("redirect:/users");
        return model;
    }

    @GetMapping("/user-delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        this.userServ.deleteUserById(id);
        model.setViewName("redirect:/users");
        return model;
    }
}
