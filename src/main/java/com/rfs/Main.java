package com.rfs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main  {
    final int PAGE_SIZE =12;

    final float MARGIN_OF_ONE_CM = 28.8f;

    static final Float TABLE_BODY_FONT_SIZE = 6f;

    static final Float FONT_HEADER_SIZE = 8f;


    static final BaseColor BACK_GROUND_TABLE_HEADER_COLOR = new BaseColor(23,169,227);


    static final BaseColor TABLE_HEADER_FONT_COLOR = new BaseColor(35,59,102);
    static final BaseColor TABLE_BODY_FONT_COLOR = new BaseColor(35,59,102);;
    static final BaseColor TABLE_BORDER_COLOR = new BaseColor(31,78,121);;
    static final BaseColor TOTAL_BK_COLOR = new BaseColor(218,227,243);;
    static final BaseColor ORIGINAL_FONT_COLOR = new BaseColor(255,0,0);;

    static final String RFS_IMG = "logo.png";
    private Integer lines;

    //
    public static final String DEST = "app.pdf";
    private static final String TITLE_IMG = "title.png";
    private static final String LOGO_IMG = "logo.png";

    public static void main(String[] args) throws IOException, DocumentException, NoSuchAlgorithmException {

//        String cedula12345 = "2074302-  16";
//        String lastFourDigits = "";
//        cedula12345 = cedula12345.replace(" " , "");
//        cedula12345 = cedula12345.replace("-","");
//        cedula12345 = cedula12345.replaceAll("^\\da-zA-Z\\W","");
//
//        if (cedula12345.length() > 4)
//        {
//            lastFourDigits = cedula12345.substring(cedula12345.length() - 4);
//        }


        BigInteger bg = new BigInteger("08",16);
        System.out.println("VALUE " + bg);


        Double value11 = 9305 * 12 * 0.13;
        BigDecimal bd11 = createDinero(value11);
        System.out.println("VALUE " + bd11);


        Double value0 = 0d;
        Double value = 6902.65486;
        BigDecimal bd = createDinero(value);
        System.out.println("VALUE " + bd);
        value0 = value0 + bd.doubleValue();
        Double value1 = 1898.23008;
        bd = createDinero(value1);
        System.out.println("VALUE " + bd);

        value0 = value0 + bd.doubleValue();

        Double value2 = 575.22123;
        bd = createDinero(value2);
        System.out.println("VALUE " + bd);

        value0 = value0 + bd.doubleValue();
        Double value3 = 6955.575222;
        bd = createDinero(value3);
        System.out.println("VALUE " + bd);

        value0 = value0 + bd.doubleValue();
//        value = value + value1 + value2 + value3;
        System.out.println("VALUE " + value0);

        BigDecimal bd1 = BigDecimal.valueOf(value0 );
       bd1 =  bd1.setScale(5,BigDecimal.ROUND_HALF_EVEN);
        System.out.println("VAL " + bd1.doubleValue());

        /*MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] hash = sha256.digest(
                lastFourDigits.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }*/
//        System.out.println(lastFourDigits);
            //System.out.println(hexString);



//        String cedula12345 = "hola";
//        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
//        byte[] hash = sha256.digest(
//                cedula12345.getBytes(StandardCharsets.UTF_8));
//        StringBuffer hexString = new StringBuffer();
//        for (int i = 0; i < hash.length; i++) {
//            String hex = Integer.toHexString(0xff & hash[i]);
//            if(hex.length() == 1) hexString.append('0');
//            hexString.append(hex);
//        }
//
//
//        System.out.println(hexString.toString());
//        StringBuffer sb=new StringBuffer();
//        String hash=sb.toString();
        //new Main().createPdf(DEST);
//        Document doc = new Document();
//        // step 2
//        PdfWriter.getInstance(doc, new FileOutputStream(DEST));
//        // step 3
//        doc.open();
//
//        Image image = Image.getInstance(LOGO_IMG);
//        //image.scaleAbsolute(100,50);
//        image.setAlignment(Image.ALIGN_CENTER);
//        doc.add(image);
//
//        BaseColor titleColor = new BaseColor(35,59,102);
//        addTitle(doc,"RFS LOGISTICA INTEGRADA, S.A.",22, Element.ALIGN_CENTER,Font.BOLD,titleColor);
//
//        addTitle(doc,"Cédula Jurídica: 3-101-654879-08",8,Element.ALIGN_CENTER,Font.BOLD,titleColor);
//        addTitle(doc,"Teléfono: (506) 4036-3727 - Fax: (506) 2265-1182",8,Element.ALIGN_CENTER,Font.BOLD,titleColor);
//        addTitle(doc,"ORIGINAL",11,Element.ALIGN_RIGHT,Font.BOLD,BaseColor.RED);
//
//        float[] columnWidths = {1,5, 2,2,2};
//
//        PdfPTable main = new PdfPTable(columnWidths);
//        main.setWidthPercentage(100);
//        main.setSpacingBefore(10);
//        main.addCell(getCellHeader("TESTING JAJAJA", 22f, Font.NORMAL, TABLE_HEADER_FONT_COLOR, Element.ALIGN_CENTER, PdfPCell.BOX,null, null));
//        // step 5
//        doc.add(main);
//        //doc.add()
//        doc.close();
    }

    /**
     * Creates a PDF with information about the movies
     * @param    filename the name of the PDF file that will be created.
     * @throws    DocumentException
     * @throws    IOException
     */
    public void createPdf(String filename)
            throws IOException, DocumentException {
        // step 1
        Document document = new Document();

        document.setMargins(5, 5, 10, 10);

        // step 2
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4

        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setPaddingLeft(3);
        rootTable.getDefaultCell().setPaddingRight(3);
        //rootTable.getDefaultCell().setBorder(1);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidthBottom(3);
        rootTable.getDefaultCell().setBorderWidthTop(3);
        rootTable.getDefaultCell().setBorderWidthLeft(3);
        rootTable.getDefaultCell().setBorderWidthRight(3);
        //rootTable.getDefaultCell().setBorderWidth(20);
        rootTable.getDefaultCell().setBorderColor(TABLE_BORDER_COLOR);

        rootTable.addCell(createHeaderTable(document));
        rootTable.addCell(createClienteTable(document));
        rootTable.addCell(createImpuestosTable(document));
        rootTable.addCell(createTercerosTable(document));
        rootTable.addCell(createServiciosTable(document));
        PdfPTable t = createGestionTable(document);
        rootTable.addCell(t);
        t = createTotalesTable1(t);
        rootTable.addCell(t);
//        rootTable.addCell(createGestionTable(document));
//        rootTable.addCell(createTotalesTable());
        rootTable.addCell(createReciboTable(document));
        rootTable.addCell(createLeyDevolucionesTable());

        document.add(rootTable);

        // step 5
        document.close();
    }

    public PdfPTable createHeaderTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        float[] columnWidthso1 = {7,3};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(1);

        rootTable.getDefaultCell().setBorderColor(TABLE_BORDER_COLOR);

        PdfPTable rootTableo1 = new PdfPTable(columnWidthso1);
        rootTableo1.setWidthPercentage(100);
        rootTableo1.getDefaultCell().setBorder(0);
        rootTableo1.getDefaultCell().setBorderWidth(0);
        rootTableo1.getDefaultCell().setPadding(1);

        float[] columnWidths0 = {4,3,2};
        PdfPTable table = new PdfPTable(columnWidths0);

        PdfPCell c = getCellData("Factura No.", 12f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null);
        c.setPaddingTop(5);
        table.addCell(c);
        c = getCellData("17046", 12f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null);
        //c.setColspan(2);
        c.setPaddingTop(5);

        table.addCell(c);
        table.addCell(getCellData(" ", 12f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));


        String fStr = "30 December, 2017";
        PdfPCell cell = getCellData(fStr, 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));

        String creditoStr = " X ";
        String contadoStr = " ";

        PdfPCell c1 = getCellData("Contado", 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null);
        c1.setColspan(2);
        table.addCell(c1);
        table.addCell(getSmallCellData(contadoStr, 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,0,null,null, null));
        c1 = getCellData("Crédito", 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null);
        c1.setColspan(2);
        table.addCell(c1);
        table.addCell(getSmallCellData(creditoStr, 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,0,null,null, null));
        table.addCell(getCellData("ORIGINAL", 9f, Font.BOLD, ORIGINAL_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null, 1));
        //creditoTable.addCell(getCellData("", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));

        String tcMonto = "574.36";

        table.addCell(getCellData("Tipo Cambio", 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData(tcMonto, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1));

        float[] columnWidths1 = {7,3};

        PdfPTable table2 = new PdfPTable(columnWidths1);
        table2.getDefaultCell().setBorder(0);
        table2.getDefaultCell().setPadding(20);

        PdfPCell cellImage = getCellImage(getLogo(), Element.ALIGN_CENTER,false,null,null);
        cellImage.setPaddingTop(5);

        float[] columnWidths11 = {10};

        PdfPTable table22 = new PdfPTable(columnWidths11);
        table22.getDefaultCell().setBorder(0);
        table22.getDefaultCell().setPadding(0);
        table22.addCell(cellImage);
        table22.addCell(getCellData("RFS LOGISTICA INTEGRADA, S.A.", 16f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null, 1));

        float[] columnWidthsInfo2 = {10};
        PdfPTable infoTable2 = new PdfPTable(columnWidthsInfo2);
        infoTable2.setPaddingTop(0);
        infoTable2.setSpacingBefore(0f);
        c = getCellData("Cédula Jurídica: 3-101-654879-08", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1);
        infoTable2.addCell(c);

       c1 = getCellData("Teléfono: (506) 4036-3727 - Fax: (506) 2265-1182", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1);

        infoTable2.addCell(c1);

        table22.addCell(infoTable2);

        table2.addCell(table22);

        rootTableo1.addCell(table22);
        rootTableo1.addCell(table);

        rootTable.addCell(rootTableo1);

        return rootTable;
    }



    /**
     * Creates our first table
     * @return our first table
     */
    /*public PdfPTable createHeaderTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(1);
        
        rootTable.getDefaultCell().setBorderColor(TABLE_BORDER_COLOR);

        rootTable.addCell(getCellTitle("INFO",3f,Font.BOLD, TABLE_BORDER_COLOR, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));


        float[] columnWidths0 = {10,2,2};
        PdfPTable table = new PdfPTable(columnWidths0);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        table.addCell(getCellImage(getLogo(), Element.ALIGN_CENTER,false,null,null));
        table.addCell(getCellData("Factura No.", 10f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));
        table.addCell(getCellData("17055", 10f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null));
        rootTable.addCell(table);

        float[] columnWidths1 = {7,3};

        PdfPTable table2 = new PdfPTable(columnWidths1);
        table2.getDefaultCell().setBorder(0);
        table2.getDefaultCell().setPadding(0);


        float[] columnWidths11 = {7};

        PdfPTable table22 = new PdfPTable(columnWidths11);
        table22.getDefaultCell().setBorder(0);
        table22.getDefaultCell().setPadding(0);
        table22.addCell(getCellData("RFS LOGISTICA INTEGRADA, S.A.", 16f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));

        float[] columnWidthsInfo2 = {10};
        PdfPTable infoTable2 = new PdfPTable(columnWidthsInfo2);
        infoTable2.setPaddingTop(1);
        infoTable2.setSpacingBefore(0f);
        PdfPCell c = getCellData("Cédula Jurídica: 3-101-654879-08", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1);
        infoTable2.addCell(c);

        PdfPCell c1 = getCellData("Teléfono: (506) 4036-3727 - Fax: (506) 2265-1182", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1);

        infoTable2.addCell(c1);

        table22.addCell(infoTable2);

        table2.addCell(table22);

        float[] columnWidthsCredito = {3,1};

        PdfPTable creditoTable = new PdfPTable(columnWidthsCredito);
        creditoTable.getDefaultCell().setBorder(0);
        creditoTable.setPaddingTop(3);
        creditoTable.addCell(getCellData("Contado", 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));
        creditoTable.addCell(getSmallCellData("X", 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,1,null,null, null));
        creditoTable.addCell(getCellData("Crédito", 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));
        creditoTable.addCell(getSmallCellData("", 9f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,1,null,null, null));
        creditoTable.addCell(getCellData("ORIGINAL", 9f, Font.BOLD, ORIGINAL_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null));
        creditoTable.addCell(getCellData("", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));

        creditoTable.addCell(getCellData("Tipo Cambio", 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        creditoTable.addCell(getCellData("580.72", 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1));

        table2.addCell(creditoTable);
        rootTable.addCell(table2);

        float[] columnWidthsInfo = {10};

        PdfPTable infoTable = new PdfPTable(columnWidthsInfo);
        infoTable.setPaddingTop(1);
        infoTable.setSpacingBefore(0f);
        PdfPCell c11 = getCellData("", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);
        c.setLeading(1f,1f);
        infoTable.addCell(c11);

        PdfPCell c111 = getCellData("", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);
        c.setLeading(1f,1f);

        infoTable.addCell(c111);

        float[] columnWidthsRoot2 = {5};

        PdfPTable rootTable2 = new PdfPTable(columnWidthsRoot2);
        rootTable2.getDefaultCell().setBorder(0);
        rootTable2.setPaddingTop(0);

        float[] columnWidthsRoot1 = {10,5};

        PdfPTable rootTable1 = new PdfPTable(columnWidthsRoot1);
        rootTable1.getDefaultCell().setBorder(0);

        rootTable1.setPaddingTop(0);
        rootTable1.addCell(infoTable);
        rootTable1.addCell(rootTable2);
        rootTable.addCell(rootTable1);

        return rootTable;
    }*/

    /**
     * Creates our first table
     * @return our first table
     */
    public PdfPTable createClienteTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);


        rootTable.addCell(getCellTitle("CLIENTE",1f,Font.BOLD, TABLE_BORDER_COLOR, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        float[] columnWidths0 = {3,6,2,2};
        PdfPTable table = new PdfPTable(columnWidths0);
        table.addCell(getCellData("Cliente:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData("DISTRIBUIDORA VERMEZA SOCIEDAD ANONIMA", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        table.addCell(getCellData("Condición de Pago:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData("CREDITO 30 DIAS", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));

        table.addCell(getCellData("Teléfono:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData("3232-3344", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        table.addCell(getCellData("Fecha de Emisión:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData("26/12/2017", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));

        table.addCell(getCellData("Dirección:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData("Avenida Insurgentes Sur  #819  Primer piso   Colonia Ampliación", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        table.addCell(getCellData("Fecha Vence:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData("26/1/2018", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));

        table.addCell(getCellData("Factura a cobro por cuenta de:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        table.addCell(getCellData("Contacto:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData("Wayner Villarreal", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));


        rootTable.addCell(table);

        return rootTable;
    }

    public PdfPTable createImpuestosTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);


        rootTable.addCell(getCellTitle("DERECHOS E IMPUESTOS DE ADUANA",8f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        float[] columnWidths0 = {8,2};
        PdfPTable table = new PdfPTable(columnWidths0);
        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));
        table.addCell(getSmallCellData("132,000.00", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,1,null,null, null,true));

        rootTable.addCell(table);

        float[] columnWidthsDetalles = {8,2,3,2};
        PdfPTable tableDetalles = new PdfPTable(columnWidthsDetalles);
        tableDetalles.getDefaultCell().setBorder(0);
        tableDetalles.addCell(getCellData("Impuestos Segun DUA", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData("130,000.00", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData("Impuestos Segun DUA", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData("2,000.00", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData("Impuestos Segun DUA2 ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData("2,000.001 ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        rootTable.addCell(tableDetalles);

        return rootTable;
    }

    public PdfPTable createTercerosTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);


        rootTable.addCell(getCellTitle("PAGOS A TERCEROS",8f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        float[] columnWidths0 = {8,2};
        PdfPTable table = new PdfPTable(columnWidths0);

        table.addCell(getCellTitle(" ", 7f, Font.BOLD, BaseColor.WHITE, BaseColor.WHITE, Element.ALIGN_CENTER, false, null, null));
        table.addCell(getSmallCellData("39,000.00", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,1,null,null, null,true));


        rootTable.addCell(table);

        float[] columnWidthsDetalles = {8,2,3,2};
        PdfPTable tableDetalles = new PdfPTable(columnWidthsDetalles);
        tableDetalles.getDefaultCell().setBorder(0);
        tableDetalles.addCell(getCellData("BODEGAJES - ALMACENAJES ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData("39,000.00", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));


        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        rootTable.addCell(tableDetalles);

        return rootTable;
    }

    public PdfPTable createServiciosTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);

        rootTable.addCell(getCellTitle("SERVICIOS DE CORREDURIA ADUANERA",8f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        float[] columnWidths1 = {8,2};
        PdfPTable table1 = new PdfPTable(columnWidths1);
        table1.addCell(getCellTitle(" ", 7f, Font.BOLD, BaseColor.WHITE, BaseColor.WHITE, Element.ALIGN_CENTER, false, null, null));
        table1.addCell(getSmallCellData("19,000.00", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,1,null,null, null,true));


        rootTable.addCell(table1);

        float[] columnWidthsDetalles = {8,2,3,2};
        PdfPTable tableDetalles = new PdfPTable(columnWidthsDetalles);
        tableDetalles.getDefaultCell().setBorder(0);
        tableDetalles.addCell(getCellData("NACIONALIZACION ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData("19,000.00", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));


        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        tableDetalles.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));

        rootTable.addCell(tableDetalles);

        return rootTable;

    }

    public PdfPTable createGestionTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(0);


        rootTable.addCell(getCellTitle("DETALLE DE GESTION",8f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        float[] columnWidths0 = {4,3,4,3,4,3};
        PdfPTable table = new PdfPTable(columnWidths0);
        table.addCell(getCellData("Trámite:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("IMPORTACION", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));
        table.addCell(getCellData("Valor CIF:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("322323", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));

        table.addCell(getCellData("Sub-Total:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("322323", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));


        table.addCell(getCellData("DUA No.:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("231212", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));
        table.addCell(getCellData("Comisión Financiamiento:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("2.5%", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));

        table.addCell(getCellData("Comisión Financiamiento:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("322323", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));


        table.addCell(getCellData("Aduana:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("CENTRAL", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));
        table.addCell(getCellData("Cantidad Bultos:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("2", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));

        table.addCell(getCellData("Impuesto de Ventas 13%:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("322323", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));


        table.addCell(getCellData("Recibo No.:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("16037", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));
        table.addCell(getCellData("Peso:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));

        table.addCell(getCellData("Total:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("322323", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));


        table.addCell(getCellData("Conocimiento de Embarque:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("XX", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));
        table.addCell(getCellData("Volumen:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));

        table.addCell(getCellData("Monto Anticipado:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("322323", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));

        table.addCell(getCellData("Proveedor:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getSmallCellData("XX", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));

        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));

        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));

        rootTable.addCell(table);

        return rootTable;
    }

    public PdfPTable createTotalesTable1(PdfPTable t) {
        // a table with three columns
        float[] columns = {12,3,3};
        PdfPTable rootTable = new PdfPTable(columns);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(0);

        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = getCellData("Observacion 1, espero que cambie de linea despues de finalizar la linea, bla cla cla", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null);
        //cell.setColspan(4);
        cell.setRowspan(2);
        rootTable.addCell(cell);

        rootTable.addCell(getCellData("Saldo Pendiente:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        rootTable.addCell(getSmallCellData("322323", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null,true));
        rootTable.addCell(getCellData(" ", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        rootTable.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));

        return rootTable;
    }

    public PdfPTable createReciboTable(Document d) throws IOException, DocumentException {

        float[] columnWidths1 = {7,3};
        PdfPTable rootTable1 = new PdfPTable(columnWidths1);
        rootTable1.setWidthPercentage(100);
        rootTable1.getDefaultCell().setBorder(0);
        rootTable1.getDefaultCell().setBorderWidth(0);
        rootTable1.getDefaultCell().setPadding(0);

        rootTable1.getDefaultCell().setBorderColor(TABLE_BORDER_COLOR);


        // a table with three columns
        float[] columnWidths0 = {8};
        PdfPTable reciboTable0 = new PdfPTable(columnWidths0);
        reciboTable0.setWidthPercentage(100);
        reciboTable0.getDefaultCell().setPadding(0);
        reciboTable0.getDefaultCell().setBorder(0);
        reciboTable0.getDefaultCell().setBorderWidth(0);
        reciboTable0.addCell(getCellTitle("Recibo Conforme",9f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));


        float[] columnWidths = {2,2,2,2};
        PdfPTable reciboTable = new PdfPTable(columnWidths);
        reciboTable.setWidthPercentage(100);
        reciboTable.getDefaultCell().setBorder(0);
        reciboTable.getDefaultCell().setPadding(0);

        reciboTable.getDefaultCell().setBorderWidth(0);

        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));

        reciboTable.addCell(getCellData("Nombre Completo", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("No. de Cédula", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("Firma", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("Fecha", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable0.addCell(reciboTable);

        float[] columnWidths3 = {2};
        PdfPTable reciboTable1 = new PdfPTable(columnWidths3);
        reciboTable1.setWidthPercentage(100);
        reciboTable1.addCell(getCellTitle("Por la Empresa",9f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));


        reciboTable1.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable1.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));

        reciboTable1.addCell(getCellData("Hecho Por", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        rootTable1.addCell(reciboTable0);
        rootTable1.addCell(reciboTable1);

        return rootTable1;
    }

    public PdfPTable createLeyDevolucionesTable() throws IOException, DocumentException {

        float[] columnWidths1 = {7,3};
        PdfPTable rootTable1 = new PdfPTable(columnWidths1);
        rootTable1.setWidthPercentage(100);
        rootTable1.getDefaultCell().setBorder(1);
        rootTable1.getDefaultCell().setBorderWidth(1);
        rootTable1.getDefaultCell().setBorder(1);
        rootTable1.getDefaultCell().setBorderWidthBottom(1);
        rootTable1.getDefaultCell().setBorderWidthLeft(1);
        rootTable1.getDefaultCell().setBorderWidthRight(1);
        rootTable1.getDefaultCell().setBorderWidth(20);
        rootTable1.getDefaultCell().setBorderColor(TABLE_BORDER_COLOR);

        PdfPCell cell;
        cell = getCellData("De acuerdo al articulo 460 del Código de Comercio, esta factura constituye Título Ejecutivo. El Deudor renuncia su domicilio, los requerimientos de pago y trámite de juicio ejecutivo. La persona que firma esta factura tiene autorización para ello y es en este acto, representante del comprador. \"Autorizado mediante Resolución No. 11-97 del 12 de agosto de 1997, de la Dirección General de Tributación\"", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null);
        cell.setRowspan(3);
        rootTable1.addCell(cell);
        cell = getCellData("Sin Excepción no se aceptan devoluciones despues de ochos dias. Esta factura devenga intereses mensuales de 3% a partir de la fecha de vencimiento.", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null);
        cell.setRowspan(3);
        rootTable1.addCell(cell);




        return rootTable1;
    }

    private void paintHeader(Document doc)  throws IOException, DocumentException {
        float[] columnWidths = {5, 5, 5};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.getDefaultCell().setBorder(0);
        table.addCell(getCellImage(getLogo(), Element.ALIGN_LEFT,false,null,null));
        table.addCell(getCellData("HOLA MUNDO OTRA VEZ", 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
//            table.addCell(this.getCellData(gp.getDescription(), 10f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        //table.addCell(this.getTables(pop.getOrderNumber(),StringHelper.getDate2String(pop.getDate())));
        doc.add(table);
    }

    private static void addTitle(Document doc,String parafo, float size ,int align,int style,BaseColor baseColor) throws DocumentException {
        Font f = new Font(Font.getFamily("CARIBARI"), size, style, baseColor);
        Paragraph preface=new Paragraph(parafo,f);
        preface.setAlignment(align);
        doc.add(preface);
    }

    protected Paragraph getTitle(String parafo, float size , int align, int style, BaseColor baseColor) throws DocumentException {
        Font f = new Font(Font.getFamily("CALIBRI"), size, style, baseColor);
        Paragraph preface=new Paragraph(parafo,f);
        preface.setAlignment(align);
        return preface;
    }


    protected Image getLogo() throws IOException, DocumentException {
        Image image = Image.getInstance(RFS_IMG);
        image.scaleAbsoluteHeight(30);
        image.scaleAbsoluteWidth(30);
        image.setAlignment(Image.ALIGN_RIGHT);
        return image;
    }


    protected Paragraph tableTitle(String titleTable){
        Font f = new Font(Font.getFamily("CALIBRI"), 6, Font.BOLD,BaseColor.BLACK);
        Paragraph titleP = new Paragraph(titleTable,f);
        titleP.setAlignment(Element.ALIGN_LEFT);
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        titleP.add(dottedline);
        return titleP;
    }

    protected PdfPCell getCellTitle(String tile, Float size, int style, BaseColor fontColor, BaseColor backgroundColor, int align, boolean border, Integer colSpan, Integer rowSpan) {
        Font f = new Font(Font.getFamily("CALIBRI"), size, style, fontColor);
        PdfPCell cell = new PdfPCell(new Phrase(tile, f));
        if(!border)
            cell.setBorder(PdfPCell.NO_BORDER);
        //cell.setBackgroundColor(BACK_GROUND_TABLE_HEADER_COLOR);
        cell.setHorizontalAlignment(align);
        cell.setBackgroundColor(backgroundColor);
        //cell.setPadding(5);
        cell.setPaddingBottom(5);
        if (colSpan != null)
            cell.setColspan(colSpan);
        if (rowSpan != null)
            cell.setRowspan(rowSpan);
        return cell;
    }

    protected PdfPCell getCellImage(Image image,  int align, boolean border, Integer colSpan, Integer rowSpan) {
        PdfPCell cell = new PdfPCell(image);

        if(!border)
            cell.setBorder(PdfPCell.NO_BORDER);

        cell.setHorizontalAlignment(align);

        cell.setPaddingBottom(5);

        if (colSpan != null)
            cell.setColspan(colSpan);

        if (rowSpan != null)
            cell.setRowspan(rowSpan);
        return cell;
    }

    protected  PdfPCell getCellHeader(String tile, Float size, int style, BaseColor color, int align, Integer border, Integer colSpan, Integer rowSpan) {
        //Se crea que
        Font f = new Font(Font.getFamily("CALIBRI"), size, style, color!=null?color:BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(tile, f));
        cell.setBackgroundColor(BACK_GROUND_TABLE_HEADER_COLOR);
        cell.setHorizontalAlignment(align);
        cell.setPaddingBottom(5);

        cell.setBorder(border!=null?border:PdfPCell.NO_BORDER);

        if (colSpan != null)
            cell.setColspan(colSpan);
        if (rowSpan != null)
            cell.setRowspan(rowSpan);
        return cell;
    }

    protected PdfPCell getCellData(String data, Float size, int style, BaseColor color, int align,BaseColor bg,Integer border, Integer valignment,Integer colSpan, Integer rowSpan) {
        return getCellData(data,size, style,color, align,bg,border,valignment,colSpan,rowSpan,null);
    }

    protected PdfPCell getCellData(String data, Float size, int style, BaseColor color, int align,BaseColor bg,Integer border, Integer valignment,Integer colSpan, Integer rowSpan, Integer cellFixedHeight) {
        Font f = new Font(Font.getFamily("CALIBRI"), size, style, color != null ? color : BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(data, f));
        cell.setHorizontalAlignment(align);
        //cell.setPadding(5);
        cell.setPaddingBottom(5);

        if (valignment != null) {
            cell.setVerticalAlignment(valignment);
        }

        if (border != null) {

            cell.setBorder(border);
            cell.setBorderWidthBottom(border);
            cell.setBorderWidthLeft(border);
            cell.setBorderWidthRight(border);
            cell.setBorderColor(TABLE_BORDER_COLOR);
        } else {

            cell.setBorder(PdfPCell.NO_BORDER);
        }

        if (cellFixedHeight!=null) {
            //cell.setFixedHeight(cellFixedHeight);
            cell.setLeading(0.5f, 0.5f);
        }


        if(bg!=null)
            cell.setBackgroundColor(bg);

        if (colSpan != null)
            cell.setColspan(colSpan);
        if (rowSpan != null)
            cell.setRowspan(rowSpan);
        return cell;
    }

    protected PdfPCell getSmallCellData(String data, Float size, int style, BaseColor color, int align,BaseColor bg,Integer border, Integer valignment,Integer colSpan, Integer rowSpan) {
        return getSmallCellData(data,size,style,color,align,bg,border,valignment,colSpan,rowSpan,false);
    }

    protected PdfPCell getSmallCellData(String data, Float size, int style, BaseColor color, int align,BaseColor bg,Integer border, Integer valignment,Integer colSpan, Integer rowSpan, Boolean isSetLeading) {
        Font f = new Font(Font.getFamily("CALIBRI"), size, style, color!=null ? color: BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(data, f));
        cell.setHorizontalAlignment(align);
        //cell.setPadding(5);
        cell.setPaddingBottom(1);
        cell.setLeading(1,1);

        if (isSetLeading!=null) {
            //cell.setFixedHeight(cellFixedHeight);
            cell.setLeading(0.9f, 0.9f);
        }


        if(valignment!=null)
            cell.setVerticalAlignment(valignment);

        if (border!=null) {
            cell.setBorder(border);
            cell.setBorderWidthBottom(border);
            cell.setBorderWidthLeft(border);
            cell.setBorderWidthRight(border);
            cell.setBorderColor(TABLE_BORDER_COLOR);

        } else {
            cell.setBorder(PdfPCell.NO_BORDER);
        }


        if(bg!=null)
            cell.setBackgroundColor(bg);

        if (colSpan != null)
            cell.setColspan(colSpan);
        if (rowSpan != null)
            cell.setRowspan(rowSpan);
        return cell;
    }

    private static BigDecimal createDinero(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(5,BigDecimal.ROUND_HALF_EVEN);
    }

    public Integer getLines() {
        return lines;
    }

    public void setLines(Integer lines) {
        this.lines = lines;
    }
}
