package am.gitc.spring_exp.services.Impl;

import am.gitc.spring_exp.entity.UserModel;
import am.gitc.spring_exp.repositories.UserDAO;
import am.gitc.spring_exp.services.UserServ;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServImpl implements UserServ {

    private final UserDAO userDAO;
    private final SessionFactory sessionFactory;
    private final PasswordEncoder passwordEncoder;

    public UserServImpl(UserDAO userDAO,EntityManagerFactory entityManagerFactory,
                        PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        if(entityManagerFactory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional(readOnly = true)
    @Override
    public List<UserModel> getAll() {
        return this.userDAO.findAll();
    }

    @Override
    public Optional<UserModel> getUserById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return userDAO.findUserModelByEmail(email);
    }

    @Transactional
    @Override
    public UserModel save(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userDAO.save(userModel);
    }

    @Transactional(rollbackFor = Exception.class,noRollbackFor = EntityNotFoundException.class)
    @Override
    public void update(UserModel userModel) {
        boolean commit = false;
        if(userDAO.findUserModelByEmail(userModel.getEmail()) != null) {
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
//            Session session = sessionFactory.openSession();
            userModel.setFirstName(userModel.getFirstName() + "_session");
            userDAO.save(userModel);
            commit = true;
        }
        if(!commit){
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void deleteUserById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public UserModel getUserByEmailAndPassword(String email, String password) {
        return userDAO.findUserModelByEmailAndPassword(email,password);
    }
}
