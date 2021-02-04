package am.gitc.spring_exp.controllers.rest;

import am.gitc.spring_exp.entity.UserEntity;
import am.gitc.spring_exp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The @AutoConfigureMockMvc annotation tells Spring to create an instance of MockMvc
 * that is associated with the application context, so that it can deliver requests
 * to the controllers handling them
 */
@AutoConfigureMockMvc
@SpringBootTest
class RestUserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsers() throws Exception {
        List<UserEntity> userList = new ArrayList<>();
        userList.add(new UserEntity("alex", "ferguson", "alex1@mail.ru", "123"));
        userList.add(new UserEntity("alex", "ferguson", "alex2@mail.ru", "123"));

        given(userService.getAll()).willReturn(userList);

        this.mockMvc.perform(get("/rest/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate headers
//                .andExpect(header().string(HttpHeaders.LOCATION, "/rest/users"))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("alex")))
                .andExpect(jsonPath("$[0].lastName", is("ferguson")))
                .andExpect(jsonPath("$[0].email", is("alex1@mail.ru")))
                .andExpect(jsonPath("$[0].password", is("123")))
                .andExpect(jsonPath("$[1].firstName", is("alex")))
                .andExpect(jsonPath("$[1].lastName", is("ferguson")))
                .andExpect(jsonPath("$[1].email", is("alex2@mail.ru")))
                .andExpect(jsonPath("$[1].password", is("123")));
    }

    @Test
    void getUserById() throws Exception {
        ObjectId id = new ObjectId(new Date(), 11636427);
        UserEntity userEntity = new UserEntity(id, "alex", "ferguson", "alex@mail.ru", "123");
        doReturn(Optional.of(userEntity)).when(userService).getUserById(id);

        // Execute the GET request
        this.mockMvc.perform(get("/rest/users/{id}", id))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate headers
//                .andExpect(header().string(HttpHeaders.LOCATION, "/rest/users/" + id))

                // Validate the returned fields
//                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is("alex")))
                .andExpect(jsonPath("$.lastName", is("ferguson")))
                .andExpect(jsonPath("$.email", is("alex@mail.ru")))
                .andExpect(jsonPath("$.password", is("123")));
    }

//    @Test
//    void getUserByIdNotFound() throws Exception {
//        ObjectId id = new ObjectId(new Date(), 11636427);
//        // Setup our mocked service
//        doReturn(Optional.empty()).when(userService).getUserById(id);
//
//        // Execute the GET request
//        mockMvc.perform(get("/rest/users/{id}", id))
//                // Validate the response code
//                .andExpect(status().isNotFound());
//    }

    @Test
    void createUser() throws Exception {
        ObjectId id = new ObjectId(new Date(), 11636428);
        // Setup our mocked service
        UserEntity userEntityPost = new UserEntity("alex", "ferguson", "alex@mail.ru", "123");
        UserEntity userEntityReturn = new UserEntity(id, "alex", "ferguson", "alex@mail.ru", "123");
        doReturn(userEntityReturn).when(userService).save(any());

        // Execute the POST request
        this.mockMvc.perform(post("/rest/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userEntityPost)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate headers
//                .andExpect(header().string(HttpHeaders.LOCATION, "/rest/users"))

                // Validate the returned fields
//                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is("alex")))
                .andExpect(jsonPath("$.lastName", is("ferguson")))
                .andExpect(jsonPath("$.email", is("alex@mail.ru")))
                .andExpect(jsonPath("$.password", is("123")));
    }

    @Test
    void updateUser() throws Exception {
        ObjectId id = new ObjectId(new Date(), 11636428);
        // Setup our mocked service
        UserEntity userEntityPut = new UserEntity("alex", "ferguson", "alex@mail.ru", "123");
        UserEntity userEntityReturnFindBy = new UserEntity(id, "alex", "ferguson", "alex@mail.ru", "123");
        UserEntity userEntityReturnSave = new UserEntity(id, "alex1", "ferguson", "alex@mail.ru", "123");
        doReturn(Optional.of(userEntityReturnFindBy)).when(userService).getUserById(id);
        doReturn(userEntityReturnSave).when(userService).save(any());

        // Execute the POST request
        this.mockMvc.perform(put("/rest/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userEntityPut)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate headers
//                .andExpect(header().string(HttpHeaders.LOCATION, "/rest/widget/" + id))

                // Validate the returned fields
//                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is("alex1")))
                .andExpect(jsonPath("$.lastName", is("ferguson")))
                .andExpect(jsonPath("$.email", is("alex@mail.ru")))
                .andExpect(jsonPath("$.password", is("123")));
    }

    @Test
    void deleteUser() throws Exception {
        ObjectId id = new ObjectId(new Date(), 11636428);
        userService.deleteUserById(id);
        this.mockMvc.perform(delete("/rest/users/{id}",id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}