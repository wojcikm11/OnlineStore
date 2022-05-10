package pl.edu.pw.onlinestore.security;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.pw.onlinestore.app.domain.User;

public class SecurityUtils {
    public static User getLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
