package pl.edu.pw.onlinestore.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String city;
    private String email;
    private String phone;
}
