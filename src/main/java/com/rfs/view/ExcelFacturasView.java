package com.rfs.view;

import com.rfs.dtos.FacturaRegistroDTO;
import com.rfs.dtos.FacturaTableDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


public class ExcelFacturasView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument (Map<String, Object> model,
    HSSFWorkbook workbook,
    HttpServletRequest request,
    HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"facturas.xls\"");

//        TicaTableDTO data = (TicaTableDTO) model.get("ticaTableDTO");
//        List<Tica> tl = data.getTicas();
        FacturaTableDTO dto = (FacturaTableDTO) model.get("facturaTableDTO");
        List<FacturaRegistroDTO> lfr = dto.getFacturas();
        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Listado Facturas");
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
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("d/m/yyyy"));

        CellStyle numberCellStyle = workbook.createCellStyle();

        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("FACTURA");
        header.getCell(0).setCellStyle(style);
//        header.createCell(1).setCellValue("RECIBO");
//        header.getCell(1).setCellStyle(style);
        header.createCell(1).setCellValue("FECHA FACTURACION");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("CLIENTE");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("CEDULA");
        header.getCell(3).setCellStyle(style);

        header.createCell(4).setCellValue("SUBTOTAL");
        header.getCell(4).setCellStyle(style);

        header.createCell(5).setCellValue("IMPUESTOS");
        header.getCell(5).setCellStyle(style);

        header.createCell(6).setCellValue("TOTAL");
        header.getCell(6).setCellStyle(style);

        header.createCell(7).setCellValue("CLAVE");
        header.getCell(7).setCellStyle(style);

        header.createCell(8).setCellValue("CONSECUTIVO");
        header.getCell(8).setCellStyle(style);

        header.createCell(9).setCellValue("HACIENDA");
        header.getCell(9).setCellStyle(style);

        Cell dateCell;
        Cell montoCell;
        int rowCount = 1;
        for(FacturaRegistroDTO t : lfr){
            Row userRow =  sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(t.getFacturaId());
            //userRow.createCell(1).setCellValue(t.getReciboId());
            if (t.getFechaFacturacion()!=null) {
                dateCell = userRow.createCell(1);
                dateCell.setCellValue(t.getFechaFacturacion());
                dateCell.setCellStyle(dateCellStyle);
//                userRow.createCell(2).setCellValue(t.getFechaFacturacion());
            } else {
                userRow.createCell(1).setCellValue("");
            }
            userRow.createCell(2).setCellValue(t.getCliente());
            userRow.createCell(3).setCellValue(t.getCedula());
            if (t.getSubtotal()!=null) {
                montoCell = userRow.createCell(4);
                montoCell.setCellValue(t.getSubtotal());
                montoCell.setCellStyle(numberCellStyle);
            } else {
                userRow.createCell(4).setCellValue("");
            }

            if (t.getImpuestoVentas()!=null) {
                montoCell = userRow.createCell(5);
                montoCell.setCellValue(t.getImpuestoVentas());
                montoCell.setCellStyle(numberCellStyle);
            } else {
                userRow.createCell(5).setCellValue("");
            }

            if (t.getTotal()!=null) {
                montoCell = userRow.createCell(6);
                montoCell.setCellValue(t.getTotal());
                montoCell.setCellStyle(numberCellStyle);
            } else {
                userRow.createCell(4).setCellValue("");
            }

            if (t.getClave()!=null) {
                montoCell = userRow.createCell(7);
                montoCell.setCellValue(t.getClave());
                montoCell.setCellStyle(numberCellStyle);
            } else {
                userRow.createCell(7).setCellValue("");
            }

            if (t.getConsecutivo()!=null) {
                montoCell = userRow.createCell(8);
                montoCell.setCellValue(t.getConsecutivo());
                montoCell.setCellStyle(numberCellStyle);
            } else {
                userRow.createCell(8).setCellValue("");
            }

            if (t.getHacienda()!=null) {
                montoCell = userRow.createCell(9);
                montoCell.setCellValue(t.getHacienda());
                montoCell.setCellStyle(numberCellStyle);
            } else {
                userRow.createCell(9).setCellValue("");
            }

        }

    }

}