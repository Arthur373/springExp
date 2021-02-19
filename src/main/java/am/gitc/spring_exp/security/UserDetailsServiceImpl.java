package am.gitc.spring_exp.security;

import am.gitc.spring_exp.entity.UserModel;
import am.gitc.spring_exp.services.UserServ;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserServ userServ;

    public UserDetailsServiceImpl(UserServ userServ) {
        this.userServ = userServ;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userServ.getUserByEmail(email);
        return SecurityUser.fromUser(userModel);
    }
}
