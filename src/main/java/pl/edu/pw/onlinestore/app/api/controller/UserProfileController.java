package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {

    @GetMapping("/profile")
    public String getUserProfilePage() {
        return "user-profile";
    }
}
