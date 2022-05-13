package pl.edu.pw.onlinestore.app.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.onlinestore.app.api.dto.EditUserInfo;
import pl.edu.pw.onlinestore.app.api.dto.UserInfoDTO;
import pl.edu.pw.onlinestore.app.api.dto.UserRegistration;
import pl.edu.pw.onlinestore.app.domain.Role;
import pl.edu.pw.onlinestore.app.domain.User;
import pl.edu.pw.onlinestore.app.domain.UserInfo;
import pl.edu.pw.onlinestore.app.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    public static final String PROFILE_INFO_DEFAULT_FIELD = "Nie podano";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserRegistration userRegistration) {
        if (usernameExists(userRegistration.getUsername())) {
            throw new DuplicateKeyException("There is an account with that username: " + userRegistration.getUsername());
        }
        User user = map(userRegistration);
        UserInfo userInfo = new UserInfo();
        user.setUserInfo(userInfo);
        return userRepository.save(user);
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserInfoDTO getUserInfoByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        UserInfo userInfo = user.getUserInfo();
        return map(userInfo, user.getUsername());
    }

    @Override
    public void updateUserInfo(EditUserInfo editUserInfo, String username) {
        UserInfo userInfo = userRepository.findByUsername(username).orElseThrow().getUserInfo();
        userInfo.setFirstName(editUserInfo.getFirstName());
        userInfo.setLastName(editUserInfo.getLastName());
        userInfo.setCity(editUserInfo.getCity());
        userInfo.setEmail(editUserInfo.getEmail());
        userInfo.setPhone(editUserInfo.getPhone());
    }

    private UserInfoDTO map(UserInfo userInfo, String username) {
        return new UserInfoDTO(
                userInfo.getId(),
                username,
                ifBlankReturnDefault(userInfo.getFirstName()),
                ifBlankReturnDefault(userInfo.getLastName()),
                ifBlankReturnDefault(userInfo.getCity()),
                ifBlankReturnDefault(userInfo.getEmail()),
                ifBlankReturnDefault(userInfo.getPhone())
        );
    }

    private String ifBlankReturnDefault(String s) {
        if (s == null || s.isBlank()) {
            return PROFILE_INFO_DEFAULT_FIELD;
        }
        return s;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }

    private User map(UserRegistration userRegistration) {
        return new User(
                userRegistration.getUsername(),
                passwordEncoder.encode(userRegistration.getPassword()),
                Role.USER
        );
    }
}
