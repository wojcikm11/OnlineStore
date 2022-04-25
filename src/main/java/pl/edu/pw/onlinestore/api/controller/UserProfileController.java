package pl.edu.pw.onlinestore.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {

    @GetMapping("/profile")
    public String getUserProfilePage() {
        return "user-profile";
    }
}
