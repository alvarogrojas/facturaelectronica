package com.rfs.view;

import com.rfs.dtos.FacturaDataDTO;
import com.rfs.dtos.FacturaRegistroDTO;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

@Component("excelFacturacionRegistroView")
public class ExcelFacturacionRegistroView
        //extends AbstractXlsView {
    extends AbstractExcelView {

    private static final String PREFIX_RECIBO = "R";

    public ExcelFacturacionRegistroView() {
        super();
        //this.setUrl("/v15");

    }

    public ExcelFacturacionRegistroView(String url) {
        super();
        //this.setUrl("v15");

    }

    @Override
    protected void buildExcelDocument (Map<String, Object> model,
                                       HSSFWorkbook workbook,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        //response.setHeader("Content-Disposition", "attachment; filename=\"factura.xls\"");
        List<FacturaRegistroDTO> dto = (List<FacturaRegistroDTO>) model.get("facturaRegistroDTO");

//        HSSFSheet sheet = workbook.getSheet("factura");
        HSSFSheet sheet = workbook.createSheet("Registro Facturas");
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
//        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,###.#"));


        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("RECIBO");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("FECHA");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("ADUANA");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("CLIENTE");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("TRAMITE");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("DUA");
        header.getCell(5).setCellStyle(styleDua);
        header.createCell(6).setCellValue("FACTURA");
        header.getCell(6).setCellStyle(styleFactura);
        header.createCell(7).setCellValue("FECHA FACTURACION");
        header.getCell(7).setCellStyle(style);
        header.createCell(8).setCellValue("IMPUESTOS");
        header.getCell(8).setCellStyle(styleImpuestosTerceros);
        header.createCell(9).setCellValue("TERCEROS");
        header.getCell(9).setCellStyle(styleImpuestosTerceros);
        header.createCell(10).setCellValue("NACIONALIZACION");
        header.getCell(10).setCellStyle(styleOtros);

        header.createCell(11).setCellValue("OTROS");
        header.getCell(11).setCellStyle(styleOtros);

        header.createCell(12).setCellValue(" ");
        header.getCell(12).setCellStyle(styleOtros);

        header.createCell(13).setCellValue(" ");
        header.getCell(13).setCellStyle(styleOtros);

        header.createCell(14).setCellValue(" ");
        header.getCell(14).setCellStyle(styleOtros);

        header.createCell(15).setCellValue(" ");
        header.getCell(15).setCellStyle(styleOtros);

        header.createCell(16).setCellValue(" ");
        header.getCell(16).setCellStyle(styleOtros);

        header.createCell(17).setCellValue(" ");
        header.getCell(17).setCellStyle(styleOtros);
        header.createCell(18).setCellValue(" ");
        header.getCell(18).setCellStyle(styleOtros);
        header.createCell(19).setCellValue(" ");
        header.getCell(19).setCellStyle(styleOtros);
        header.createCell(20).setCellValue(" ");
        header.getCell(20).setCellStyle(styleOtros);
        header.createCell(21).setCellValue(" ");
        header.getCell(21).setCellStyle(styleOtros);
        header.createCell(22).setCellValue(" ");
        header.getCell(22).setCellStyle(styleOtros);
        header.createCell(23).setCellValue(" ");
        header.getCell(23).setCellStyle(styleOtros);
        header.createCell(24).setCellValue(" ");
        header.getCell(24).setCellStyle(styleOtros);
        header.createCell(25).setCellValue(" ");
        header.getCell(25).setCellStyle(styleOtros);
        header.createCell(26).setCellValue(" ");
        header.getCell(26).setCellStyle(styleOtros);
        header.createCell(27).setCellValue(" ");
        header.getCell(27).setCellStyle(styleOtros);
        header.createCell(28).setCellValue(" ");
        header.getCell(28).setCellStyle(styleOtros);

        header.createCell(29).setCellValue("COMISION");
        header.getCell(29).setCellStyle(styleOtros);
        header.createCell(30).setCellValue("IMP VENTAS");
        header.getCell(30).setCellStyle(styleImpuestosTerceros);
        header.createCell(31).setCellValue("TOTAL");
        header.getCell(31).setCellStyle(styleImpuestosTerceros);
        header.createCell(32).setCellValue("ADELANTOS");
        header.getCell(32).setCellStyle(styleImpuestosTerceros);
        header.createCell(33).setCellValue("SALDO");
        header.getCell(33).setCellStyle(styleImpuestosTerceros);

        int rowCount = 1;
        Cell dateCell;
        Cell numberCell;
        for(FacturaRegistroDTO user : dto){
            Row userRow =  sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(formatRecibo(user));
            if (user.getFechaRecibo()!=null) {
                userRow.createCell(1).setCellValue(user.getFechaRecibo());
                dateCell = userRow.createCell(1);
                dateCell.setCellValue(user.getFechaRecibo());
                dateCell.setCellStyle(dateCellStyle);
            }
            userRow.createCell(2).setCellValue(user.getAduana());
            userRow.createCell(3).setCellValue(user.getCliente());
            userRow.createCell(4).setCellValue(user.getTramite());
            userRow.createCell(5).setCellValue(user.getDua());
            userRow.createCell(6).setCellValue(user.getFacturaId());

            //userRow.createCell(7).setCellValue(user.getTotalInmuestos());
            if (user.getFechaFacturacion()!=null) {
                userRow.createCell(7).setCellValue(user.getFechaFacturacion());
                dateCell = userRow.createCell(7);
                dateCell.setCellValue(user.getFechaFacturacion());
                dateCell.setCellStyle(dateCellStyle);
            }

            numberCell = userRow.createCell(8);
            numberCell.setCellValue(user.getTotalInmuestos());
            numberCell.setCellStyle(numberCellStyle);


            //userRow.createCell(8).setCellValue(user.getTotalInmuestos());
            numberCell = userRow.createCell(9);
            numberCell.setCellValue(user.getTotalTerceros());
            numberCell.setCellStyle(numberCellStyle);

            //userRow.createCell(9).setCellValue(user.getTotalServicios());
            numberCell = userRow.createCell(10);
            numberCell.setCellValue(user.getNacionalizacion());
            numberCell.setCellStyle(numberCellStyle);

            numberCell = userRow.createCell(11);
            numberCell.setCellValue(user.getOtros());
            numberCell.setCellStyle(numberCellStyle);

            //userRow.createCell(28).setCellValue(user.getComisionFinanciamiento());
            numberCell = userRow.createCell(29);
            numberCell.setCellValue(formatearMonto(user,user.getComisionFinanciamiento()));
//            numberCell.setCellValue(user.getComisionFinanciamiento());
            numberCell.setCellStyle(numberCellStyle);

            //userRow.createCell(29).setCellValue(user.getImpuestoVentas());
            numberCell = userRow.createCell(30);
            numberCell.setCellValue(user.getImpuestoVentas());
            numberCell.setCellStyle(numberCellStyle);


            //userRow.createCell(30).setCellValue(user.getTotal());
            numberCell = userRow.createCell(31);
            numberCell.setCellValue(user.getTotal());
            numberCell.setCellStyle(numberCellStyle);


            //userRow.createCell(31).setCellValue(user.getMontoAnticipado());
            numberCell = userRow.createCell(32);
            numberCell.setCellValue(user.getMontoAnticipado());
            numberCell.setCellStyle(numberCellStyle);


            //userRow.createCell(32).setCellValue(user.getSaldoPendiente());
            numberCell = userRow.createCell(33);
            numberCell.setCellValue(user.getSaldoPendiente());
            numberCell.setCellStyle(numberCellStyle);


        }

        setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"factura.xls\"");
    }

    private Double formatearMonto(FacturaRegistroDTO user, Double monto) {
        if (monto==null) {
            return null;
        }
        return monto;
    }

    private String formatRecibo(FacturaRegistroDTO user) {
        if (user.getReciboId()==null) {
            return "";
        }
        return PREFIX_RECIBO + user.getReciboId();
    }

    //@Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"factura.xls\"");
        FacturaDataDTO dto = (FacturaDataDTO) map.get("facturaDataDTO");


        URL url = Thread.currentThread().getContextClassLoader().getResource("factura.xls");
        File file = new File(url.getPath());

        try (FileOutputStream fileOutputStream = new FileOutputStream("facturaTemplate.xls")) {

            Files.copy(file.toPath(), fileOutputStream);

            //workbook = new HSSFWorkbook();
            workbook = WorkbookFactory.create(file);
            workbook.write(fileOutputStream);

            FileInputStream inp = new FileInputStream("facturaTemplate.xls");

            workbook = WorkbookFactory.create(inp);

            try (FileOutputStream fileOut = new FileOutputStream("facturaTemplate.xls")) {
                workbook.write(fileOut);
            }


        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
////            POIFSFileSystem fs = new POIFSFileSystem(
////                    new FileInputStream("factura.xls"));
//            POIFSFileSystem fs = new POIFSFileSystem(getClass().getResourceAsStream("/factura.xls"));
//            workbook = new  HSSFWorkbook(fs, true);
//
////        InputStream fis = ChartSample.class.getResourceAsStream("/templates.xls");
//


    }
}
