package am.gitc.spring_exp.controllers;

import am.gitc.spring_exp.entity.UserEntity;
import am.gitc.spring_exp.services.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsers() throws Exception {
        ObjectId id = new ObjectId(new Date(), 11636427);
        List<UserEntity> userList = new ArrayList<>();
        userList.add(new UserEntity(id, "alex", "ferguson", "alex1@mail.ru", "123"));
        userList.add(new UserEntity(id, "alex", "ferguson", "alex2@mail.ru", "123"));

        given(userService.getAll()).willReturn(userList);

        this.mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", userService.getAll()))
                .andExpect(view().name("user/users"));
    }

    @Test
    void createUserForm() throws Exception {
        this.mockMvc.perform(get("/user-create")).andDo(print())
                .andExpect(model().attribute("user", new UserEntity()))
                .andExpect(view().name("user/user-create"))
                .andExpect(status().isOk());
    }

    @Test
    void createUser() throws Exception {
        UserEntity userEntity = new UserEntity("alex", "ferguson", "alex@mail.ru", "123");
        doReturn(null).when(userService).getUserByEmail(userEntity.getEmail());
        doReturn(userEntity).when(userService).save(any());
        this.mockMvc.perform(post("/user-create")).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void updateUserForm() throws Exception {
        ObjectId id = new ObjectId(new Date(), 11636428);
        UserEntity userEntity = new UserEntity("alex", "ferguson", "alex@mail.ru", "123");
        doReturn(Optional.of(userEntity)).when(userService).getUserById(id);
        this.mockMvc.perform(get("/user-update/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userEntity))
                .andExpect(view().name("user/user-update"));
    }

    @Test
    void updateUser() throws Exception {
        UserEntity userEntity = new UserEntity("alex", "ferguson", "alex@mail.ru", "123");
        doReturn(userEntity).when(userService).save(any());
        this.mockMvc.perform(post("/user-update")).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void deleteUser() throws Exception {
        ObjectId id = new ObjectId(new Date(), 11636428);
        this.userService.deleteUserById(id);
        this.mockMvc.perform(get("/user-delete/{id}",id)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }
}