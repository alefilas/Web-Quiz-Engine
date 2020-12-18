package engine.service;

import engine.entity.User;
import engine.exceptions.UserAlreadyExistException;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (emailExist(user.getEmail())) {
            throw new UserAlreadyExistException();
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

}
