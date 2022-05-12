package pl.edu.pw.onlinestore.app.api.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpinionReport {
    private Long id;
    private String username;
    private int rating;
    private String dateAdded;
    private String description;
}
