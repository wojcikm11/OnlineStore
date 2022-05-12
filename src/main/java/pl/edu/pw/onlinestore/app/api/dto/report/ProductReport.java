package pl.edu.pw.onlinestore.app.api.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductReport {
    private Long id;
    private String title;
    private String seller;
    private String category;
    private double price;
}
