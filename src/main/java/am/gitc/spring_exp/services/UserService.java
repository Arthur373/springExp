package am.gitc.spring_exp.services;

import am.gitc.spring_exp.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> getAll();

    Optional<UserEntity> getUserById(int id);

    UserEntity getUserByEmail(String email);

    UserEntity save(UserEntity userEntity);

    void delete(int id);

    UserEntity getUserByEmailAndPassword(String email,String password);

}
