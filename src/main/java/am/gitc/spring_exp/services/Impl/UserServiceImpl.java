package am.gitc.spring_exp.services.Impl;

import am.gitc.spring_exp.entity.UserEntity;
import am.gitc.spring_exp.repositories.UserRepository;
import am.gitc.spring_exp.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserEntityByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity getUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserEntityByEmailAndPassword(email, password);
    }
}
