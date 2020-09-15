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

    public User registerUser(String email, String name, String password) {
        Optional<User> existed = userRepository.findByEmail(email);
        if(existed.isPresent()){ //존재한다면 예외발생
            throw new EmailExistedException(email);
        }

        String encodedPassword = passwordEncoder.encode(password);
        //패스워드 관리자가 악용하지 못하도록 암호화

        User user = User.builder()
                .email(email)
                .name(name)
                .password(encodedPassword)
                .level(1L)
                .build();

        return userRepository.save(user);
    }
}
