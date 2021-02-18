package am.gitc.spring_exp.repositories;

import am.gitc.spring_exp.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<UserModel,Integer> {

    UserModel findUserModelByEmail(String email);

    UserModel findUserModelByEmailAndPassword(String email,String password);
}
