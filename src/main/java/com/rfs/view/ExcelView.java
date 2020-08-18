package com.rfs.view;

import com.rfs.dtos.ReciboDTO;
import com.rfs.dtos.RecibosDataTableDTO;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public class ExcelView extends AbstractXlsView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"recibo-xls-file.xls\"");

        @SuppressWarnings("unchecked")

        RecibosDataTableDTO data = (RecibosDataTableDTO) model.get("recibosDataTableDTO");
        List<ReciboDTO> rdl = data.getRecibos();
        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Recibos");
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

        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(
        createHelper.createDataFormat().getFormat("d/m/yyyy"));

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("RECIBO");
        header.getCell(0).setCellStyle(style);



        header.createCell(1).setCellValue("FECHA");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("ADUANA");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("TRAMITE");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("CLIENTE");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("CONSIGNATARIO");
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue("DUA");
        header.getCell(6).setCellStyle(style);
        header.createCell(7).setCellValue("ENCARGADO");
        header.getCell(7).setCellStyle(style);
        header.createCell(8).setCellValue("ESTADO");
        header.getCell(8).setCellStyle(style);
        header.createCell(9).setCellValue("ANTIGUEDAD");
        header.getCell(9).setCellStyle(style);
        header.createCell(10).setCellValue("CORELACION");
        header.getCell(10).setCellStyle(style);
        header.createCell(11).setCellValue("FACTURACION");
        header.getCell(11).setCellStyle(style);

        header.createCell(12).setCellValue("EXAMEN PREVIO");
        header.getCell(12).setCellStyle(style);
        header.createCell(13).setCellValue("AFORO FISICO");
        header.getCell(13).setCellStyle(style);
        header.createCell(14).setCellValue("PERMISOS");
        header.getCell(14).setCellStyle(style);
        header.createCell(15).setCellValue("PEDIMENTADOS");
        header.getCell(15).setCellStyle(style);

        //previoExamen, aforoFisico, permisos, pedimentados

        int rowCount = 1;
        Cell dateCell;
        for(ReciboDTO user : rdl){
            Row userRow =  sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(user.getRecibo());
            dateCell = userRow.createCell(1);
            dateCell.setCellValue(user.getFecha());
            dateCell.setCellStyle(dateCellStyle);
            //userRow.createCell(1).setCellValue(user.getAduana());
            userRow.createCell(2).setCellValue(user.getAduana());
            userRow.createCell(3).setCellValue(user.getTipo());
            userRow.createCell(4).setCellValue(user.getCliente());
            userRow.createCell(5).setCellValue(user.getConsignatario());
            userRow.createCell(6).setCellValue(user.getDua());
            userRow.createCell(7).setCellValue(user.getEncargado());
            userRow.createCell(8).setCellValue(user.getEstado());
            userRow.createCell(9).setCellValue(user.getAntiguedad());
            userRow.createCell(10).setCellValue(user.getCorelacionId());
            userRow.createCell(11).setCellValue(user.getFacturas());

            userRow.createCell(12).setCellValue(user.getExamenPrevioStr());
            userRow.createCell(13).setCellValue(user.getAforoFisicoStr());
            userRow.createCell(14).setCellValue(user.getPermisosStr());
            userRow.createCell(15).setCellValue(user.getPedimentadosStr());
        }

    }

}