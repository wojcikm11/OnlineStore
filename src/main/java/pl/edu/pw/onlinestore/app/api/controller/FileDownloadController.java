package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.service.FileService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class FileDownloadController {

    private FileService fileService;

    public FileDownloadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/report/products")
    public ResponseEntity<Resource> downloadProductsReport(@RequestParam("category") String category) throws IOException {
        String reportType = "products";
        fileService.writeProductsReport(category);
        File file = getFile(List.of(reportType, category));
        return sendReport(file);
    }

    @GetMapping("/report/opinions")
    public ResponseEntity<Resource> downloadOpinionsReport(@RequestParam("username") String username, @RequestParam("type") String opinionsType) throws IOException {
        String reportType = "opinions";
        fileService.writeOpinionsReport(username, opinionsType);
        File file = getFile(List.of(reportType, opinionsType, username));
        return sendReport(file);
    }

    private ResponseEntity<Resource> sendReport(File file) throws IOException {
        InputStreamResource resource = fileService.readReport(file);

        HttpHeaders headers = new HttpHeaders();
        addHeaders(headers, file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private void addHeaders(HttpHeaders headers, String fileName) {
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
    }

    private File getFile(List<String> filePrefixes) {
        StringBuilder combinedFilePrefix = new StringBuilder("");
        for (String filePrefix : filePrefixes) {
            combinedFilePrefix.append(filePrefix).append("-");
        }
        String fileName = combinedFilePrefix + "report.xlsx";
        return new File(getClass().getClassLoader().getResource(fileName).getFile());
    }
}
