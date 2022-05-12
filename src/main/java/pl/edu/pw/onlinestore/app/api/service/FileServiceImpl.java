package pl.edu.pw.onlinestore.app.api.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.onlinestore.app.api.dto.ProductInfo;
import pl.edu.pw.onlinestore.app.api.dto.ProfileOpinion;
import pl.edu.pw.onlinestore.app.api.dto.report.OpinionReport;
import pl.edu.pw.onlinestore.app.api.dto.report.ProductReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private ProductService productService;
    private OpinionService opinionService;

    public FileServiceImpl(ProductService productService, OpinionService opinionService) {
        this.productService = productService;
        this.opinionService = opinionService;
    }

    @Override
    public void writeProductsReport(String category) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        String reportType = "products";
        Sheet sheet = workbook.createSheet(reportType);

        prepareProductsHeaders(sheet, workbook);

        List<ProductInfo> products;
        if (category.equalsIgnoreCase("all")) {
            products = productService.getAll();
        } else {
            products = productService.getProductsByCategory(category);
        }
        List<ProductReport> productsReport = products.stream().map(this::map).toList();

        fillWithProducts(sheet, workbook, productsReport);
        saveFile(workbook, List.of(reportType, category));
    }

    @Override
    public void writeOpinionsReport(String username, String type) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        String reportType = "opinions";
        Sheet sheet = workbook.createSheet(reportType);

        prepareOpinionsHeaders(sheet, workbook);

        List<ProfileOpinion> opinions;
        if (type.equalsIgnoreCase("all")) {
            opinions = opinionService.getProfileOpinions(username);
        } else {
            opinions = opinionService.getProfileGivenTypeOpinions(type, username);
        }
        List<OpinionReport> opinionsReport = opinions.stream().map(this::map).toList();

        fillWithOpinions(sheet, workbook, opinionsReport);
        saveFile(workbook, List.of(reportType, type, username));
    }

    @Override
    public InputStreamResource readReport(File file) throws IOException {
        return new InputStreamResource(new FileInputStream(file));
    }

    private void prepareProductsHeaders(Sheet sheet, Workbook workbook) {
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 7000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = getHeaderStyle(workbook);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Tytuł");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Sprzedawca");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Kategoria");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Cena (zł)");
        headerCell.setCellStyle(headerStyle);
    }

    private void prepareOpinionsHeaders(Sheet sheet, Workbook workbook) {
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 20000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = getHeaderStyle(workbook);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Użytkownik");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Ocena (Max. 5)");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Data wystawienia");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Opis");
        headerCell.setCellStyle(headerStyle);
    }

    private CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();;

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private void saveFile(Workbook workbook, List<String> filePrefixes) throws IOException {
        StringBuilder combinedFilePrefix = new StringBuilder("");
        for (String filePrefix : filePrefixes) {
            combinedFilePrefix.append(filePrefix).append("-");
        }
        String fileName = combinedFilePrefix + "report.xlsx";
        FileOutputStream outputStream = new FileOutputStream(getClass().getClassLoader().getResource(".").getFile() + fileName);
        workbook.write(outputStream);
        workbook.close();
    }

    private void fillWithProducts(Sheet sheet, Workbook workbook, List<ProductReport> products) {
        int rowIndex = 2;
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        for (ProductReport productReport : products) {
            Row row = sheet.createRow(rowIndex);
            createCell(row, productReport.getId().toString(), style, 0);
            createCell(row, productReport.getTitle(), style, 1);
            createCell(row, productReport.getSeller(), style, 2);
            createCell(row, productReport.getCategory(), style, 3);
            createCell(row, String.valueOf(productReport.getPrice()), style, 4);
            rowIndex++;
        }
    }

    private void fillWithOpinions(Sheet sheet, Workbook workbook, List<OpinionReport> opinions) {
        int rowIndex = 2;
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        for (OpinionReport opinionReport : opinions) {
            Row row = sheet.createRow(rowIndex);
            createCell(row, opinionReport.getId().toString(), style, 0);
            createCell(row, opinionReport.getUsername(), style, 1);
            createCell(row, String.valueOf(opinionReport.getRating()), style, 2);
            createCell(row, opinionReport.getDateAdded(), style, 3);
            createCell(row, opinionReport.getDescription(), style, 4);
            rowIndex++;
        }
    }

    private void createCell(Row row, String cellContent, CellStyle style, int cellIndex) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellContent);
        cell.setCellStyle(style);
    }

    private ProductReport map(ProductInfo productInfo) {
        return new ProductReport(
                productInfo.getId(),
                productInfo.getTitle(),
                productInfo.getSeller(),
                productInfo.getCategory(),
                productInfo.getPrice()
        );
    }

    private OpinionReport map(ProfileOpinion profileOpinion) {
        return new OpinionReport(
                profileOpinion.getId(),
                profileOpinion.getUsername(),
                profileOpinion.getRating(),
                profileOpinion.getDateAdded(),
                profileOpinion.getDescription()
        );
    }
}