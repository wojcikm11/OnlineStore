package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.dto.*;
import pl.edu.pw.onlinestore.app.api.service.OpinionService;
import pl.edu.pw.onlinestore.app.api.service.ProductService;
import pl.edu.pw.onlinestore.app.api.service.UserService;
import pl.edu.pw.onlinestore.app.api.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@Controller
public class UserProfileController {

    private UserService userService;
    private OpinionService opinionService;
    private ProductService productService;

    public UserProfileController(UserService userService, OpinionService opinionService, ProductService productService) {
        this.userService = userService;
        this.opinionService = opinionService;
        this.productService = productService;
    }

    @GetMapping("/profile")
    public String getUserProfilePage(@RequestParam("username") String username, @RequestParam("opinions") Optional<String> opinionsType, Model model) {
        List<ProfileOpinion> profileOpinions;
        if (opinionsType.isEmpty()) {
            profileOpinions = opinionService.getProfileOpinions(username);
        } else {
            profileOpinions = opinionService.getProfileGivenTypeOpinions(opinionsType.get(), username);
        }
        UserInfoDTO userInfo = userService.getUserInfoByUsername(username);
        List<OpinionTypeDTO> opinionTypes = opinionService.getOpinionTypes();
        List<ProductInfo> wishList = productService.getAllFromWishList(username);

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("opinions", profileOpinions);
        model.addAttribute("opinionsTypes", opinionTypes);
        model.addAttribute("wishList", wishList);
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
                ifDefaultEmptyField(userInfoDTO.getFirstName()),
                ifDefaultEmptyField(userInfoDTO.getLastName()),
                ifDefaultEmptyField(userInfoDTO.getCity()),
                ifDefaultEmptyField(userInfoDTO.getEmail()),
                ifDefaultEmptyField(userInfoDTO.getPhone())
        );
    }

    private String ifDefaultEmptyField(String field) {
        if (field.equals(UserServiceImpl.PROFILE_INFO_DEFAULT_FIELD)) {
            return null;
        }
        return field;
    }
}
