package pl.edu.pw.onlinestore.app.api.service;

import pl.edu.pw.onlinestore.app.api.dto.EditUserInfo;
import pl.edu.pw.onlinestore.app.api.dto.UserInfoDTO;
import pl.edu.pw.onlinestore.app.api.dto.UserRegistration;
import pl.edu.pw.onlinestore.app.domain.User;

import java.util.Optional;

public interface UserService {
    User register(UserRegistration user);
    Optional<User> getByUsername(String username);
    UserInfoDTO getUserInfoByUsername(String username);
    void updateUserInfo(EditUserInfo editUserInfo, String username);
}
