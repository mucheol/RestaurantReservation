package kr.co.fastcam.eatgo.application;

import kr.co.fastcam.eatgo.domain.User;
import kr.co.fastcam.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new EmailNotExistedException(email));

        if(passwordEncoder.matches(password, user.getPassword())) {  //(실제패스워드, 암호화된 패스워드)
            throw new PasswordWrongException();
        }
        return user;
    }
}
