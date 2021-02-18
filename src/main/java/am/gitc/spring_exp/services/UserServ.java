package am.gitc.spring_exp.services;

import am.gitc.spring_exp.entity.UserModel;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserServ {

    List<UserModel> getAll();

    Optional<UserModel> getUserById(int id);

    UserModel getUserByEmail(String email);

    UserModel save(UserModel userModel);

    void update(UserModel userModel);

    void deleteUserById(int id);

    UserModel getUserByEmailAndPassword(String email,String password);
}
