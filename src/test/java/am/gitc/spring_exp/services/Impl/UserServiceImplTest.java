package am.gitc.spring_exp.services.Impl;

import am.gitc.spring_exp.entity.UserEntity;
import am.gitc.spring_exp.exceptions.UserRegistrationException;
import am.gitc.spring_exp.repositories.UserRepository;
import am.gitc.spring_exp.services.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.junit.Assert;
//import org.junit.runner.RunWith;
//import static org.junit.Assert.assertEquals;
//@RunWith(SpringRunner.class)
//@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = TestConfig.class,
//        loader = AnnotationConfigContextLoader.class)



/**
 *  scans the CLASSPATH for all Spring configuration classes
 *  and beans and sets up the Spring application context for the test class.
 */
//неявно включает @ExtendWith(SpringExtension.class) //testi zapuski hamar @RunWith() nman
@SpringBootTest
class UserServiceImplTest {
    /**
     * создает объект для внедрения фиктивной зависимости,
     * потому что в этом случае мы имитируем UserService,
     * поэтому мы создаем @InjectMock на основе UserService.
     */
//  @InjectMocks
    @Autowired
    private UserService userService;
    @MockBean // Create a mock implementation of the UserRepository
    private UserRepository userRepository;


    @Test
    @DisplayName("Test saveUser")
    public void saveWithNewEmail() {
        UserEntity userEntity = new UserEntity("alex", "ferguson", "alex@mial.ru", "123456789");
        given(userRepository.findUserEntityByEmail(userEntity.getEmail())).willReturn(null);
        given(userRepository.save(userEntity)).willAnswer(invocation -> invocation.getArgument(0));
        UserEntity user = userService.save(userEntity);

        Assertions.assertNotNull(user);
        verify(userRepository).save(any(UserEntity.class));
//        Assertions.assertNotNull(user, "The saved user should not be null");
    }

    @Test
    @DisplayName("Test notSaveUser")
    public void NotSaveUserWithExistingEmail() {
        UserEntity userEntity = new UserEntity("alex", "ferguson", "alex@mial.ru", "123456789");
        given(userRepository.findUserEntityByEmail(userEntity.getEmail())).willReturn(userEntity);
        Assertions.assertThrows(UserRegistrationException.class,
                () -> {
                    userService.save(userEntity);
                }
        );
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Test findAll")
    public void shouldReturnFindAll() {
        List<UserEntity> userList = new ArrayList<>();
        userList.add(new UserEntity("alex", "ferguson", "alex1@mail.ru", "123"));
        userList.add(new UserEntity("alex", "ferguson", "alex2@mail.ru", "123"));
        userList.add(new UserEntity("alex", "ferguson", "alex3@mail.ru", "123"));

        given(userRepository.findAll()).willReturn(userList);
//        doReturn(userList).when(userRepository).findAll();

        List<UserEntity> users = userService.getAll();
        Assertions.assertEquals(users, userList);
//        Assertions.assertEquals(2, widgets.size(), "findAll should return 2 widgets");
    }

    @Test
    @DisplayName("Test findById")
    public void findUserById() {
        ObjectId id = new ObjectId(new Date(), 11636427);
        UserEntity userEntity = new UserEntity(id,"alex", "ferguson", "alex@mail.ru", "123");

        given(userRepository.findById(id)).willReturn(Optional.of(userEntity));
//        doReturn(Optional.of(userEntity)).when(userRepository).findById(id);
        Optional<UserEntity> userById = userService.getUserById(id);
        Assertions.assertNotNull(userById);
        // Assert the response
//        Assertions.assertTrue(userById.isPresent(), "User was not found");
        Assertions.assertSame(userById.get(), userEntity, "The user returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test deleteUserById")
    public void deleteUserById() {
        ObjectId id = new ObjectId(new Date(), 11636427);
        userRepository.deleteById(id);
        userService.deleteUserById(id);
        verify(userRepository, times(2)).deleteById(id);
    }
}