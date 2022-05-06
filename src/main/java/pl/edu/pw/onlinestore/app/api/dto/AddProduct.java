package pl.edu.pw.onlinestore.app.api.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.onlinestore.app.domain.Category;

@Data
public class AddProduct {
    private String category;
    private String title;
    private double price;
    private MultipartFile file;
}
