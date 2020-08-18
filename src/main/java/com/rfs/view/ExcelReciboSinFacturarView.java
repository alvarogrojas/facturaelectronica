package com.rfs.view;


import com.rfs.dtos.PendientesFacturarDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public class ExcelReciboSinFacturarView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument (Map<String, Object> model,
    HSSFWorkbook workbook,
    HttpServletRequest request,
    HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"tica.xls\"");

//        TicaTableDTO data = (TicaTableDTO) model.get("ticaTableDTO");
//        List<Tica> tl = data.getTicas();
        List<PendientesFacturarDTO> lpf = (List<PendientesFacturarDTO>) model.get("recibosPendientes");
        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Pendientes Facturar");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("RECIBO");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("CLIENTE");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("DUA");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("ENCARGADO");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("APERTURA");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("CORELACION");
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue("JUSTIFICACION");
        header.getCell(6).setCellStyle(style);

        int rowCount = 1;
        for(PendientesFacturarDTO t : lpf){
            Row userRow =  sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(t.getRecibo());
            userRow.createCell(1).setCellValue(t.getCliente());
            userRow.createCell(2).setCellValue(t.getDua());
            userRow.createCell(3).setCellValue(t.getEncargado());
            userRow.createCell(4).setCellValue(t.getFecha());
            userRow.createCell(5).setCellValue(t.getCorelacionId());
            userRow.createCell(6).setCellValue(t.getTicaEstado());

        }

    }

}