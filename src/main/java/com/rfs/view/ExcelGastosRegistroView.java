package com.rfs.view;

import com.rfs.domain.ConfirmaRechazaDocumento;
import com.rfs.dtos.ResultadoConfirmacionRechazoDTO;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Component("excelGastosView")
public class ExcelGastosRegistroView
    extends AbstractExcelView {

    private static final String PREFIX_RECIBO = "G";

    public ExcelGastosRegistroView() {
        super();

    }

    public ExcelGastosRegistroView(String url) {
        super();
    }

    @Override
    protected void buildExcelDocument (Map<String, Object> model,
                                       HSSFWorkbook workbook,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {

        ResultadoConfirmacionRechazoDTO dto = (ResultadoConfirmacionRechazoDTO) model.get("dto");

        HSSFSheet sheet = workbook.createSheet("Gastos");
        sheet.setDefaultColumnWidth(30);


        Font font1 = workbook.createFont();
        font1.setFontName("Arial");
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font1.setColor(HSSFColor.BLACK.index);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        CellStyle styleDua = workbook.createCellStyle();
        styleDua.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
        styleDua.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleDua.setFont(font);

        CellStyle styleFactura = workbook.createCellStyle();
        styleFactura.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        styleFactura.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleFactura.setFont(font);

        CellStyle styleImpuestosTerceros = workbook.createCellStyle();
        styleImpuestosTerceros.setFillForegroundColor(HSSFColor.YELLOW.index);
        styleImpuestosTerceros.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleImpuestosTerceros.setFont(font1);

        CellStyle styleOtros = workbook.createCellStyle();
        styleOtros.setFillForegroundColor(HSSFColor.ORANGE.index);
        styleOtros.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleOtros.setFont(font);

        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy"));

        CellStyle numberCellStyle = workbook.createCellStyle();

        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("CLAVE");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("CONSECUTIVO");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("FECHA EMISION");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("EMISOR");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("IDENTIFICACION");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("TOTAL VENTA");
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue("TOTAL IMPUESTOS");
        header.getCell(6).setCellStyle(style);
        header.createCell(7).setCellValue("TOTAL COMPROBANTE");
        header.getCell(7).setCellStyle(style);
        header.createCell(8).setCellValue("CONSECUTIVO RECEPTOR");
        header.getCell(8).setCellStyle(style);
        header.createCell(9).setCellValue("ESTADO");
        header.getCell(9).setCellStyle(style);

        int rowCount = 1;
        Cell dateCell;
        Cell numberCell;
        if (dto!=null && dto.getConfirmRechazos()!=null) {
            for (ConfirmaRechazaDocumento d : dto.getConfirmRechazos()) {
                Row r = sheet.createRow(rowCount++);
                r.createCell(0).setCellValue(d.getClave());

                r.createCell(1).setCellValue(d.getConsecutivo());
               // r.createCell(1).setCellValue(d.getFechaEmision());

                //userRow.createCell(7).setCellValue(user.getTotalInmuestos());
                if (d.getFechaEmision() != null) {
                    r.createCell(2).setCellValue(d.getFechaEmision());
                    dateCell = r.createCell(2);
                    dateCell.setCellValue(d.getFechaEmision());
                    dateCell.setCellStyle(dateCellStyle);
                }

                r.createCell(3).setCellValue(d.getEmisor());
                r.createCell(4).setCellValue(d.getIdentificacionEmisor());


                numberCell = r.createCell(5);
                numberCell.setCellValue(d.getTotalVenta());
                numberCell.setCellStyle(numberCellStyle);


                //userRow.createCell(8).setCellValue(user.getTotalInmuestos());
                numberCell = r.createCell(6);
                numberCell.setCellValue(d.getTotalImpuesto());
                numberCell.setCellStyle(numberCellStyle);

                //userRow.createCell(9).setCellValue(user.getTotalServicios());
                numberCell = r.createCell(7);
                numberCell.setCellValue(d.getTotalComprobante());
                numberCell.setCellStyle(numberCellStyle);

                r.createCell(8).setCellValue(d.getNumeroConsecutivoReceptor());
                r.createCell(9).setCellValue(d.getEstadoEnviadoHacienda());
            }
        }

        setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"gastos.xls\"");
    }


}
