package am.gitc.spring_exp.repositories;

import am.gitc.spring_exp.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,Long> {

    UserEntity findUserEntityByEmail(String email);

    UserEntity findUserEntityByEmailAndPassword(String email,String password);

}
