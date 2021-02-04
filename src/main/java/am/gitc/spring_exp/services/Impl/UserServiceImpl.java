package am.gitc.spring_exp.services.Impl;

import am.gitc.spring_exp.entity.UserEntity;
import am.gitc.spring_exp.exceptions.UserRegistrationException;
import am.gitc.spring_exp.repositories.UserRepository;
import am.gitc.spring_exp.services.UserService;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserEntityByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if(userRepository.findUserEntityByEmail(userEntity.getEmail()) == null) {
            return userRepository.save(userEntity);
        }
        throw new UserRegistrationException("User whit this email exists");
    }

    @Override
    public void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity getUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserEntityByEmailAndPassword(email, password);
    }
}
