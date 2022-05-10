package pl.edu.pw.onlinestore.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditUserInfo {
    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String email;
    private String phone;
}
