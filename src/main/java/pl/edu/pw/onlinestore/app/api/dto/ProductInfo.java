package pl.edu.pw.onlinestore.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInfo {
    private Long id;
    private Long categoryId;
    private String title;
    private String seller;
    private String category;
    private double price;
}
