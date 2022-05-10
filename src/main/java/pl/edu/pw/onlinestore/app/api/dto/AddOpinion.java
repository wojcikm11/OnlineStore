package pl.edu.pw.onlinestore.app.api.dto;

import lombok.Data;

@Data
public class AddOpinion {
    private String receiverUsername;
    private int rating;
    private String description;
}
