package am.gitc.spring_exp.services;

import am.gitc.spring_exp.entity.UserEntity;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> getAll();

    Optional<UserEntity> getUserById(ObjectId id);

    UserEntity getUserByEmail(String email);

    UserEntity save(UserEntity userEntity);

    void deleteUserById(ObjectId id);

    UserEntity getUserByEmailAndPassword(String email,String password);

}
