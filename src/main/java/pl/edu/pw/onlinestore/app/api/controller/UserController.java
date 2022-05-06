package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.dto.UserRegistration;
import pl.edu.pw.onlinestore.app.api.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginView(@RequestParam Optional<String> error, Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String getRegistrationView(Model model) {
        UserRegistration userRegistration = new UserRegistration();
        model.addAttribute("user", userRegistration);
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistration userRegistration, BindingResult result,
                                      HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.register(userRegistration);
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
            request.login(userRegistration.getUsername(), userRegistration.getPassword());
        } catch (ServletException | DuplicateKeyException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }

        return "redirect:/";
    }
}