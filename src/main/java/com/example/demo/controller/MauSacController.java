package com.example.demo.controller;

import com.example.demo.entity.MauSac;
import com.example.demo.service.MauSacService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/mau-sac/")
public class MauSacController {

    @Autowired
    private MauSacService mauSacService;

    private List<MauSac> listMauSac = new ArrayList<>();


    @GetMapping("hien-thi")
    public String hienThi(@ModelAttribute("message")String message,@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
//        listMauSac = mauSacService.getAll();

        Page<MauSac> page = mauSacService.phanTrangMauSac(pageNum, 5);
        model.addAttribute("listms", page);
        model.addAttribute("ms1", new MauSac());
        model.addAttribute("msg", message);
        return "mausac/mausac";
    }

    @PostMapping("add")
    public String addMauSac(@Valid @ModelAttribute("ms1") MauSac mauSac, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "mausac/mausac";
        }
        String ma = "MS" + new Random().nextInt(100000);
        mauSac.setMaMauSac(ma);
        mauSac.setNgayTao(new Date());
        mauSac.setTrangThai(1);
        model.addAttribute("ms1", mauSac);
        mauSacService.add(mauSac);
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String deleteMauSac(@PathVariable UUID id,RedirectAttributes redirectAttributes ,Model model) {
        mauSacService.delete(id);
        redirectAttributes.addFlashAttribute("message","Delete Success");
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateMauSac(@PathVariable UUID id, Model model) {
        MauSac mauSac = mauSacService.detail(id);
        model.addAttribute("ms1", mauSac);
        return "mausac/update-mausac";
    }

    @PostMapping("update")
    public String updateMauSac(@Valid @ModelAttribute("ms1") MauSac mauSac, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "mausac/mausac";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        mauSac.setNgayTao(ngayTaoDate);
        mauSac.setNgaySua(new Date());
        model.addAttribute("ms1", mauSac);
        mauSacService.add(mauSac);
        return "redirect:/mau-sac/hien-thi";
    }

// Xuất execl
@GetMapping("/export")
public void exportToExcel(HttpServletResponse response) throws IOException {
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=mau_sac_" + currentDateTime + ".xlsx";
    response.setHeader(headerKey, headerValue);

    List<MauSac> listMauSac = mauSacService.getAll();

    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("MauSac");

    // Tạo tiêu đề cột
    Row headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("ID");
    headerRow.createCell(1).setCellValue("Mã Màu");
    headerRow.createCell(2).setCellValue("Tên Màu");
    headerRow.createCell(3).setCellValue("Trạng Thái");

    int rowNum = 1;
    for (MauSac mauSac : listMauSac) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(mauSac.getId().toString());
        row.createCell(1).setCellValue(mauSac.getMaMauSac());
        row.createCell(2).setCellValue(mauSac.getTenMauSac());
        row.createCell(3).setCellValue(mauSac.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động");
    }

    // Ghi tệp Excel vào HttpServletResponse
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    workbook.write(response.getOutputStream());
    workbook.close();
}

// xuất file PDF
@GetMapping("/export-pdf")
public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=mau_sac.pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());

    document.open();

    List<MauSac> listMauSac = mauSacService.getAll();

    // Tạo bảng PDF và đặt tiêu đề cột
    PdfPTable table = new PdfPTable(4);
    table.setWidthPercentage(100);

    Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    PdfPCell cell = new PdfPCell();

    cell.setPhrase(new Phrase("ID", font));
    table.addCell(cell);

    cell.setPhrase(new Phrase("Mã Màu", font));
    table.addCell(cell);

    cell.setPhrase(new Phrase("Tên Màu", font));
    table.addCell(cell);

    cell.setPhrase(new Phrase("Trạng Thái", font));
    table.addCell(cell);

    // Thêm dữ liệu từ danh sách vào bảng PDF
    for (MauSac mauSac : listMauSac) {
        table.addCell(String.valueOf(mauSac.getId()));
        table.addCell(mauSac.getMaMauSac());
        table.addCell(mauSac.getTenMauSac());
        table.addCell(mauSac.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động");
    }

    document.add(table);
    document.close();
}

//    @GetMapping("/export-pdf")
//    public void exportToPdf(HttpServletResponse response) throws IOException, DocumentException {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=mau_sac.pdf");
//
//        Document document = new Document();
//        PdfWriter.getInstance(document, response.getOutputStream());
//
//        document.open();
//
//        List<MauSac> listMauSac = mauSacService.getAll();
//
//        // Tạo bảng PDF và đặt tiêu đề cột
//        PdfPTable table = new PdfPTable(4);
//        table.setWidthPercentage(100);
//
//        // Sử dụng phông chữ Unicode (ví dụ: Arial Unicode MS)
////        BaseFont unicodeFont = BaseFont.createFont("fonts/ArialUnicodeMS.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//
//
//        String fontPath = "H:\\thuctap\\Fix\\websiteaoFong-main\\websiteaoFong-main\\Website_KingFashion\\src\\main\\resources\\fonts\\ArialUnicodeMS.ttf";
//        BaseFont unicodeFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        Font font = new Font(unicodeFont, 12);
//        // Đặt phông chữ cho các ô
//        PdfPCell cell = new PdfPCell(new Phrase("ID", font));
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Mã Màu", font));
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Tên Màu", font));
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("Trạng Thái", font));
//        table.addCell(cell);
//
//        // Thêm dữ liệu từ danh sách vào bảng PDF
//        for (MauSac mauSac : listMauSac) {
//            table.addCell(new Phrase(String.valueOf(mauSac.getId()), font));
//            table.addCell(new Phrase(mauSac.getMaMauSac(), font));
//            table.addCell(new Phrase(mauSac.getTenMauSac(), font));
//            table.addCell(new Phrase(mauSac.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động", font));
//        }
//
//        document.add(table);
//        document.close();
//    }
}
