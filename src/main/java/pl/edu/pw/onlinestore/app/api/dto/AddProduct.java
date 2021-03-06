package pl.edu.pw.onlinestore.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.onlinestore.app.domain.Category;

@Data
public class AddProduct {
    private Long categoryId;
    private String title;
    private double price;
    private MultipartFile file;

}
