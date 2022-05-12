package pl.edu.pw.onlinestore.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditOpinion {
    private Long id;
    private int rating;
    private String description;
}
