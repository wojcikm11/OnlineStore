package pl.edu.pw.onlinestore.app.api.service;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.IOException;

public interface FileService {
    void writeProductsReport(String category) throws IOException;
    InputStreamResource readReport(File file) throws IOException;
    void writeOpinionsReport(String username, String type) throws IOException;
}
