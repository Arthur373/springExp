package am.gitc.spring_exp.controllers.rest;

import am.gitc.spring_exp.entity.UserEntity;
import am.gitc.spring_exp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("rest/")
public class RestUserController {

    private UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            return ResponseEntity
                    .ok(this.userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") ObjectId id) {
        Optional<UserEntity> userEntity = this.userService.getUserById(id);
        try {
            return ResponseEntity.ok().body(userEntity.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        UserEntity user = userService.save(userEntity);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userEntity,
                                                 @PathVariable("id") ObjectId id) {
        // Get the widget with the specified id
        Optional<UserEntity> user = userService.getUserById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        // Update the widget
        userEntity.setId(id);
        userEntity = this.userService.save(userEntity);

        try {
            // Return a 200 response with the updated widget
            return ResponseEntity.ok().body(userEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") ObjectId id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
