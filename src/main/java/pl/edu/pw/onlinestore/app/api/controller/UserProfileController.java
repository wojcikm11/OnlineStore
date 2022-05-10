package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.dto.EditProduct;
import pl.edu.pw.onlinestore.app.api.dto.EditUserInfo;
import pl.edu.pw.onlinestore.app.api.dto.UserInfoDTO;
import pl.edu.pw.onlinestore.app.api.service.UserService;

@Controller
public class UserProfileController {

    private UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getUserProfilePage(@RequestParam("username") String username, Model model) {
        UserInfoDTO userInfo = userService.getUserInfoByUsername(username);
        model.addAttribute("userInfo", userInfo);
        return "user-profile";
    }

    @GetMapping("/edit-profile")
    public String getEditProfilePage(@RequestParam("username") String username, Model model) {
        UserInfoDTO userInfo = userService.getUserInfoByUsername(username);
        EditUserInfo editUserInfo = map(userInfo);
        model.addAttribute("userInfo", editUserInfo);
        return "forms/edit-user-info";
    }

    @PostMapping("/edit-profile")
    public String getEditProfilePage(@ModelAttribute("userInfo") EditUserInfo editUserInfo, @RequestParam("username") String username) {
        userService.updateUserInfo(editUserInfo, username);

        return "redirect:profile?username=" + username.toLowerCase();
    }

    private EditUserInfo map(UserInfoDTO userInfoDTO) {
        return new EditUserInfo(
          userInfoDTO.getId(),
          userInfoDTO.getFirstName(),
          userInfoDTO.getLastName(),
          userInfoDTO.getCity(),
          userInfoDTO.getEmail(),
          userInfoDTO.getPhone()
        );
    }
}
