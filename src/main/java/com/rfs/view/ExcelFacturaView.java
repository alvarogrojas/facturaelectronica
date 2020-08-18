package com.rfs.view;

import com.rfs.dtos.FacturaDataDTO;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import java.util.Map;

@Component("excelFacturaView")
public class ExcelFacturaView
        //extends AbstractXlsView {
    extends AbstractExcelView {

    public ExcelFacturaView() {
        super();
        this.setUrl("factura");

    }

    public ExcelFacturaView(String url) {
        super();
        this.setUrl("factura");

    }

    @Override
    protected void buildExcelDocument (Map<String, Object> model,
                                       HSSFWorkbook workbook,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        //response.setHeader("Content-Disposition", "attachment; filename=\"factura.xls\"");
        FacturaDataDTO dto = (FacturaDataDTO) model.get("facturaDataDTO");

//        HSSFSheet sheet = workbook.getSheet("factura");
        HSSFSheet sheet = workbook.getSheet("factura");
        if (sheet==null) {
            System.out.println("TEST ****************");
            sheet = workbook.createSheet("factura " + dto.getFacturaId());
        }
        Font font1 = workbook.createFont();
        font1.setFontName("Arial");

        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font1.setColor(HSSFColor.BLACK.index);

        HSSFRow header = sheet.getRow(3);
//        HSSFRow row1 = sheet.getRow(0);
        String factura = "";
        if (dto.getFacturaId()!=null) {
            factura = dto.getFacturaId().toString();
        }
        header.getCell(35).setCellValue(factura);

        if (dto.getCredito()==1) {
            header = sheet.getRow(7);
            header.getCell(40).setCellValue("X");
        } else {
            header = sheet.getRow(5 );
            header.getCell(40).setCellValue("X");
        }

        header = sheet.getRow(69 );
        header.getCell(34).setCellValue(dto.getEncargado().getNombre());

        setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"factura.xls\"");

//        FileInputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(new File("factura.xls"));
//            //@SuppressWarnings("resource")
//            workbook = new HSSFWorkbook(inputStream);
//            HSSFSheet sheet = workbook.getSheet("factura");
//            HSSFRow header = sheet.getRow(3);
//            //header.getCell(29).setCellValue(dto.getFacturaId());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        URL url = Thread.currentThread().getContextClassLoader().getResource("factura.xls");
//        File file = new File(url.getPath());
//
//            try (FileOutputStream fileOutputStream = new FileOutputStream("facturaTemplate.xls")) {
//
//                Files.copy(file.toPath(), fileOutputStream);
//
//                workbook = new HSSFWorkbook();
//                workbook.write(fileOutputStream);
//
//                FileInputStream inp = new FileInputStream("facturaTemplate.xls");
//
//                Workbook workbook1 = WorkbookFactory.create(inp);
//
//                try (FileOutputStream fileOut = new FileOutputStream("facturaTemplate.xls")) {
//                    workbook1.write(fileOut);
//                }
//
//
//            } catch (InvalidFormatException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            //HSSFSheet sheet1 = wb.getSheet("factura");

//        try {
////            POIFSFileSystem fs = new POIFSFileSystem(
////                    new FileInputStream("factura.xls"));
//            POIFSFileSystem fs = new POIFSFileSystem(getClass().getResourceAsStream("/factura.xls"));
//            workbook = new  HSSFWorkbook(fs, true);
//
////        InputStream fis = ChartSample.class.getResourceAsStream("/templates.xls");
//
//        HSSFSheet sheet = workbook.getSheet("factura");
//
//
//        Font font1 = workbook.createFont();
//        font1.setFontName("Arial");
//
//        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        font1.setColor(HSSFColor.BLACK.index);
//
//        HSSFRow header = sheet.getRow(4);
//
//        //header.getCell(1).setCellValue("Nombre de la Institucion");
//
//
//        header.getCell(29).setCellValue(dto.getFacturaId());

//        header.getCell(4).setCellValue(planningData.getName());
//
//
//        header = sheet.getRow(1);
//        //header.getCell(1).setCellValue("Nombre del Profesor");
//        header.getCell(3).setCellValue("Jorge Quesada");
//
//        header = sheet.getRow(2);
//        // header.getCell(1).setCellValue("Año");
//        header.getCell(3).setCellValue("2016");
//            fs.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//
//        }


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
