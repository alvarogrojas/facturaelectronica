package com.rfs.view;

import com.google.common.base.Strings;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.rfs.domain.Empresa;
import com.rfs.domain.TipoCambio;
import com.rfs.dtos.NotaCreditoDataDTO;
import com.rfs.dtos.NotaCreditoDetalleDTO;
import com.rfs.fe.v43.nc.NotaCreditoElectronica;
import com.rfs.service.factura.billapp.BillDataService;
import com.rfs.service.factura.billapp.BillHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Map;


public class PdfNotaCreditoView extends PdfiTextView {

    final int PAGE_SIZE =12;

    final float MARGIN_OF_ONE_CM = 28.8f;

    static final Float TABLE_BODY_FONT_SIZE = 6f;

    static final Float FONT_HEADER_SIZE = 8f;

//    PdfNotaCreditoView notaCreditoView = new PdfNotaCreditoView();

    @Autowired
    private ApplicationContext applicationContext;

    private static final String FULL_LOGO_PATH = "/home/alvaro/billapp/images/";


    static final BaseColor BACK_GROUND_TABLE_HEADER_COLOR = new BaseColor(23,169,227);


    static final BaseColor TABLE_HEADER_FONT_COLOR = new BaseColor(35,59,102);
    static final BaseColor TABLE_BODY_FONT_COLOR = new BaseColor(35,59,102);;
    static final BaseColor TABLE_BORDER_COLOR = new BaseColor(31,78,121);;
    static final BaseColor TABLE_BORDER_TRANSPARENT = new BaseColor(255,255,255);
    static final BaseColor TOTAL_BK_COLOR = new BaseColor(218,227,243);;
    static final BaseColor ORIGINAL_FONT_COLOR = new BaseColor(255,0,0);;

    static final String RFS_IMG = "logo/logo.png";
    static final String RFS_IMG_1 = "logo.png";
    private Integer lines;

    //
    public static final String DEST = "app.pdf";
    private static final String TITLE_IMG = "title.png";
    private static final String LOGO_IMG = "logo.png";


    private String clave;
    private String numeroFacturaElectronica;

    private NotaCreditoDataDTO notaCreditoDataDTO;
    private String claveNotaCredito;
    private String numeroNotaCreditoElectronica;

    private NumberFormat formatNumber = new DecimalFormat("#,##0.00");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat largeDateFormat = new SimpleDateFormat("dd MMMM, yyyy");

    private Integer MAX_ITEMS_IMPUESTOS = 3;
    private Integer MAX_ITEMS_TERCEROS = 30;
    private Integer MAX_ITEMS_SERVICIOS = 5;


    private TipoCambio defaultTC;
    private Empresa empresa;

    private PdfWriter pdfWriter;

    private Map<String, Object> model;

    private HttpServletRequest request;
    private  HttpServletResponse response;

    private BillDataService billDataService;

    public PdfNotaCreditoView() {}

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Map<String, Object> data = (Map<String, Object>) model.get("data");

        //PayRoll payRoll = (PayRoll) data.get("payRoll");
        empresa = (Empresa) model.get("empresa");
        //this.initFacturaElectronica(facturaDataDTO.getFacturaId());

        //facturaDataDTO = (FacturaDataDTO) model.get("facturaDataDTO");
        this.clave = (String) model.get("clave");
        //this.pdfWriter = writer;
        numeroFacturaElectronica = (String) model.get("numeroFacturaElectronica");

        notaCreditoDataDTO = (NotaCreditoDataDTO) model.get("notaCreditoDataDTO");
        this.claveNotaCredito = (String) model.get("claveNotaCredito");
        this.pdfWriter = writer;
        numeroNotaCreditoElectronica = (String) model.get("numeroNotaCreditoElectronica");
        this.model = model;
        this.request = request;
        this.response = response;


        initDefaultTC();
        createPdf(doc);


    }

    public void generatePdfFile(NotaCreditoElectronica nce, String filenamePdf, String facturaClave, BillDataService billDataService, String numFacturaElectronica,Empresa e) throws Exception {
        Document document = new Document();

        this.numeroNotaCreditoElectronica = nce.getNumeroConsecutivo();
        this.claveNotaCredito = nce.getClave();
        this.clave = facturaClave;

        numeroFacturaElectronica = numFacturaElectronica;
        empresa = e;

        //this.facturaDataDTO = this.facturaService.getFacturaData(ft.getId(),null);
        Map<Integer,NotaCreditoDataDTO> d = (Map<Integer,NotaCreditoDataDTO>) billDataService.getNcData();
        if (d!= null) {
            NotaCreditoDataDTO dto  = d.get(nce.getId());
            if (dto==null) {
                throw new Exception("NO SE ENCONTRO LA NOTA DE CREDITO EN EL MAP");
            }
            this.notaCreditoDataDTO= dto;
        } else {

            throw new Exception("NO SE ENCONTRO LA NOTA DE CREDITO EN EL MAP");
        }


        try {
            initDefaultTC();
            PdfWriter.getInstance(document, new FileOutputStream(filenamePdf));
            createPdf(document);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void createPdf(Document document)
            throws Exception, DocumentException {

        document.setMargins(5, 5, 35, 10);

        document.open();

        float[] columnWidths = {10};
        PdfPTable rootTable = initPdfTable(columnWidths);

        rootTable.addCell(createHeaderTable(document));
        rootTable.addCell(createClienteTable(document));
        rootTable.addCell(createDetalleFacturaTable(document));
        PdfPTable t = createGestionTable(document);
        rootTable.addCell(t);
        t = createTotalesTable1(t);
        rootTable.addCell(t);
        rootTable.addCell(createReciboTable(document));
        rootTable.addCell(createRazonTipo());
        rootTable.addCell(createFEData());
        rootTable.addCell(createLeyDevolucionesTable());

        document.add(rootTable);

        document.close();

    }

    private PdfPTable initPdfTable(float[] columnWidths) {
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setPaddingLeft(3);
        rootTable.getDefaultCell().setPaddingRight(3);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidthBottom(3);
        rootTable.getDefaultCell().setBorderWidthTop(3);
        rootTable.getDefaultCell().setBorderWidthLeft(3);
        rootTable.getDefaultCell().setBorderWidthRight(3);
        rootTable.getDefaultCell().setBorderColor(TABLE_BORDER_COLOR);
        return rootTable;
    }

    /**
     * Creates our first table
     * @return our first table
     */
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
        PdfPCell c = null;
        c = getCellData(BillHelper.DOCUMENTO_NOTA_CREDITO_NO, 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null, null, null, null, null);
        //PdfPCell c = getCellData("Factura No.", 12f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null);
        c.setPaddingTop(5);
        table.addCell(c);
        c = getCellData(this.notaCreditoDataDTO.getNotaCreditoId().toString(), 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null);

        c.setPaddingTop(5);

        table.addCell(c);
        table.addCell(getCellData(" ", 12f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));

        String fStr = largeDateFormat.format(this.notaCreditoDataDTO.getFechaNotaCreditocion());
        PdfPCell cell = getCellData(fStr, 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null);
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));

        String creditoStr = " ";
        String contadoStr = " ";

        if (this.notaCreditoDataDTO.getCredito()==1) {
            creditoStr = "  X";
        } else {
            contadoStr = "  X";
        }
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

        String tcMonto = "";

        if (this.notaCreditoDataDTO.getTipoCambioMonto()!=null) {
            tcMonto = formatNumber.format(this.notaCreditoDataDTO.getTipoCambioMonto());
        }

//        table.addCell(getCellData("Tipo Cambio", 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
//        table.addCell(getCellData(tcMonto, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1));

        table.addCell(getCellData("Moneda", 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null,1));
        table.addCell(getCellData(this.defaultTC.getNombre(), 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1));

        float[] columnWidths1 = {7,3};

        PdfPTable table2 = new PdfPTable(columnWidths1);
        table2.getDefaultCell().setBorder(0);
        table2.getDefaultCell().setPadding(20);

        PdfPCell cellImage = getCellImage(getLogo(empresa.getLogoPath()), Element.ALIGN_CENTER,false,null,null);
        cellImage.setPaddingTop(5);

        float[] columnWidths11 = {10};

        PdfPTable table22 = new PdfPTable(columnWidths11);
        table22.getDefaultCell().setBorder(0);
        table22.getDefaultCell().setPadding(0);
        table22.addCell(cellImage);
        table22.addCell(getCellData(empresa.getNombre(), 14f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null, 1));

        float[] columnWidthsInfo2 = {10};
        PdfPTable infoTable2 = new PdfPTable(columnWidthsInfo2);
        infoTable2.setPaddingTop(0);
        infoTable2.setSpacingBefore(0f);
        c = getCellData("Identificación: " + empresa.getCedula(), 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1);
        infoTable2.addCell(c);

        c1 = getCellData("Teléfono: " + empresa.getTelefono(), 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null,1);

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
    public PdfPTable createClienteTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setBackgroundColor(TOTAL_BK_COLOR);


        rootTable.addCell(getCellTitle("CLIENTE",7f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        float[] columnWidths0 = {2,6,1.5f,2};
        PdfPTable table = new PdfPTable(columnWidths0);
        table.getDefaultCell().setBackgroundColor(TOTAL_BK_COLOR);
        table.addCell(getCellData("Cliente:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        table.addCell(getCellData(this.notaCreditoDataDTO.getCliente(), 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null,1));

        table.addCell(getCellData("Condición de Pago:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        String creditoStr = " ";
        creditoStr = getCreditoStr(creditoStr);

        table.addCell(getCellData(creditoStr, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null,1));



        table.addCell(getCellData("Contacto", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        table.addCell(getCellData(this.notaCreditoDataDTO.getContacto(), 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null,1));
        table.addCell(getCellData("Fecha de Emisión:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        String fechaEmisionStr = "";
        if (notaCreditoDataDTO.getFechaNotaCreditocion()!=null) {
            fechaEmisionStr = dateFormat.format(notaCreditoDataDTO.getFechaNotaCreditocion());
        }
        table.addCell(getCellData(fechaEmisionStr, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null,1));


        float[] columnWidths01 = {2,5,2,3};


        PdfPTable table1 = new PdfPTable(columnWidths01);
        table1.getDefaultCell().setBackgroundColor(TOTAL_BK_COLOR);

        //table1.setWidthPercentage(100);
        table1.getDefaultCell().setBorder(0);
        table1.getDefaultCell().setBorderWidth(0);


        table1.getDefaultCell().setPadding(0);
        table1.getDefaultCell().setBorderWidth(0);

        table1.addCell(getCellData("Teléfono:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        table1.addCell(getCellData(this.notaCreditoDataDTO.getTelefono(), 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null,1));

        table1.addCell(getCellData("Cedula", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        table1.addCell(getCellData(this.notaCreditoDataDTO.getCedula(), 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null,1));
        PdfPCell c111 = new PdfPCell(table1);
        c111.setColspan(2);
        c111.setBorder(0);

        table.addCell(c111);

        table.addCell(getCellData("Fecha Vence:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        String fechaVenceStr = "";
        if (notaCreditoDataDTO.getFechaVencimiento()!=null && this.notaCreditoDataDTO.getCredito()==1) {
            fechaVenceStr = dateFormat.format(notaCreditoDataDTO.getFechaVencimiento());
        }
        table.addCell(getCellData(fechaVenceStr, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null,1));

        table.addCell(getCellData("Dirección:", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        PdfPCell c1 = getCellData(this.notaCreditoDataDTO.getDireccion(), 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null,1);
        c1.setRowspan(3);
        table.addCell(c1);

        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));
        table.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null,1));



        rootTable.addCell(table);

        return rootTable;
    }

    private String getCreditoStr(String creditoStr) {
        if (this.notaCreditoDataDTO.getDiasCredito()==null || this.notaCreditoDataDTO.getDiasCredito()<0) {
            this.notaCreditoDataDTO.recalculateDiasCredito();
        }
        if (this.notaCreditoDataDTO.getCredito()==1 && this.notaCreditoDataDTO.getDiasCredito()>0) {
            creditoStr = "CREDITO " + this.notaCreditoDataDTO.getDiasCredito() + " DIAS";
        } else if (this.notaCreditoDataDTO.getCredito()==0) {
            creditoStr = "CONTADO";
        }
        return creditoStr;
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
        String montoString = " ";
        if (this.notaCreditoDataDTO.getImpuestoVentas()!=null) {
            if (this.defaultTC!=null) {
                montoString = this.defaultTC.getSimbol() + montoString;
            }
            montoString = montoString + formatNumber.format(this.notaCreditoDataDTO.getImpuestoVentas());
            table.addCell(getCellData(montoString, 9f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, TOTAL_BK_COLOR,null,null,null, null));
        } else {
            table.addCell(getCellData(" ", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, TOTAL_BK_COLOR,null,null,null, null));

        }
        //table.addCell(getSmallCellData(montoString, 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, TOTAL_BK_COLOR, 0, null, null, null));


        rootTable.addCell(table);

        float[] columnWidthsDetalles = {8,2,3,4};
        PdfPTable tableDetalles = new PdfPTable(columnWidthsDetalles);
        tableDetalles.getDefaultCell().setBorder(0);

        String detalle;
        String montoDolares;
        String montoColones;
        for (int i=0;i<MAX_ITEMS_IMPUESTOS;i++) {
            detalle = " ";
            montoDolares = " ";
            montoColones = " ";
            if (this.notaCreditoDataDTO.getDetallesDTO()!=null && this.notaCreditoDataDTO.getDetallesDTO().size() > i) {
                NotaCreditoDetalleDTO fd = this.notaCreditoDataDTO.getDetallesDTO().get(i);
                detalle = fd.getDetalle().toUpperCase();
                if (fd.getTipoCambio().getId()!=1 && fd.getMonto()!=null) {
                    montoDolares = formatNumber.format(fd.getMonto());
                }
                if (fd.getMonto()!=null) {
                    montoColones = formatNumber.format(fd.getMontoColones());
                }

            }
            tableDetalles.addCell(getCellData(detalle, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null,1));
            tableDetalles.addCell(getCellData(montoDolares, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
            tableDetalles.addCell(getCellData(montoColones, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,0,null,null, null,1));
            tableDetalles.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,0,null,null, null,1));
        }



        rootTable.addCell(tableDetalles);

        return rootTable;
    }

    public PdfPTable createDetalleFacturaTable(Document d) throws IOException, DocumentException {
        // a table with three columns
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);


        rootTable.addCell(getCellTitle("DETALLE FACTURADO",8f, Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        float[] columnWidths0 = {6,2,2};
        PdfPTable table = new PdfPTable(columnWidths0);

        table.addCell(getCellTitle(" ", 7f, Font.BOLD, BaseColor.WHITE, BaseColor.WHITE, Element.ALIGN_CENTER, false, null, null));
        String montoString = " ";
        String tcString = " ";
        if (this.defaultTC!=null) {
            tcString = " " + this.notaCreditoDataDTO.getTipoCambio().getSimbol();
        }

        if (this.notaCreditoDataDTO.getSubtotal()!=null) {

            montoString = tcString + formatNumber.format(this.notaCreditoDataDTO.getSubtotal());
            table.addCell(getCellData(montoString, 9f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        } else {
            table.addCell(getCellData(" ", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, TOTAL_BK_COLOR,null,null,null, null));

        }

        if (this.notaCreditoDataDTO.getTotal()!=null) {

            montoString = tcString + formatNumber.format(this.notaCreditoDataDTO.getTotal());
            table.addCell(getCellData(montoString, 9f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        } else {
            table.addCell(getCellData(" ", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, TOTAL_BK_COLOR,null,null,null, null));

        }

        rootTable.addCell(table);

        float[] columnWidthsDetalles = {1,5,2,2};
        PdfPTable tableDetalles = new PdfPTable(columnWidthsDetalles);
        tableDetalles.getDefaultCell().setBorder(0);

        tableDetalles.addCell(getCellTitle("CANTIDAD", 7f, Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR, Element.ALIGN_CENTER, false,null,null));
        tableDetalles.addCell(getCellTitle("DETALLE", 7f, Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR, Element.ALIGN_LEFT, false, null, null));

        tableDetalles.addCell(getCellTitle("MONTO", 7f, Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR, Element.ALIGN_RIGHT, false, null, null));
        tableDetalles.addCell(getCellTitle("MONTO + IMPUESTOS", 7f, Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR, Element.ALIGN_RIGHT, false,null,null));


        String detalle;
        String montoAntesImpuestos;
        String montoColones;
        String cantidad;
        for (int i=0;i<MAX_ITEMS_TERCEROS;i++) {
            detalle = " ";
            montoAntesImpuestos = " ";
            montoColones = " ";
            cantidad = " ";
            if (this.notaCreditoDataDTO.getDetallesDTO()!=null && this.notaCreditoDataDTO.getDetallesDTO().size() > i) {
                NotaCreditoDetalleDTO fd = this.notaCreditoDataDTO.getDetallesDTO().get(i);
                if (!Strings.isNullOrEmpty(fd.getDetalle())) {
                    detalle = fd.getDetalle();
                }

                montoColones = this.defaultTC.getSimbol() + " " +formatNumber.format(fd.getMontoColones());
                montoAntesImpuestos = this.defaultTC.getSimbol() + " " +formatNumber.format(fd.getMonto());
                if (fd.getCantidad()!=null) {
                    cantidad = fd.getCantidad().toString();
                }
            }
            tableDetalles.addCell(getCellData(cantidad, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));

            tableDetalles.addCell(getCellData(detalle, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null, null,null, null));
            tableDetalles.addCell(getCellData(montoAntesImpuestos, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null, null,null, null));
            tableDetalles.addCell(getCellData(montoColones, 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,null,null,null, null));
        }

        rootTable.addCell(tableDetalles);

        return rootTable;
    }


    public PdfPTable createFEData() {
        float[] columnWidths = {10};
        PdfPTable rootTable = new PdfPTable(columnWidths);
        rootTable.setWidthPercentage(100);
        rootTable.getDefaultCell().setBorder(0);
        rootTable.getDefaultCell().setBorderWidth(0);
        rootTable.getDefaultCell().setPadding(0);

        //rootTable.addCell(getCellTitle("TOTALES",8f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_RIGHT,false,null,null));

        float[] columnWidths0 = {1,5,2,3};
        PdfPTable table = new PdfPTable(columnWidths0);

        table.addCell(getCellData("Clave:", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));

        table.addCell(getSmallCellData(this.claveNotaCredito, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,1,null,null, null,true));
        table.addCell(getCellData("Nota Credito Electronica:", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));

        table.addCell(getSmallCellData(this.numeroNotaCreditoElectronica, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,1,null,null, null,true));

        rootTable.addCell(table);

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


        rootTable.addCell(getCellTitle("TOTALES",8f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_RIGHT,false,null,null));

        float[] columnWidths0 = {3,5,3,3};
        PdfPTable table = new PdfPTable(columnWidths0);

        String vStr = " ";



        table.addCell(getCellData("", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));

        table.addCell(getCellData("Sub-Total:", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        vStr = " ";
        if (this.notaCreditoDataDTO.getSubtotal()!=null) {
            if (this.defaultTC!=null) {
                vStr = this.defaultTC.getSimbol() + vStr;
            }
            vStr = vStr + formatNumber.format(this.notaCreditoDataDTO.getSubtotal());
        }
        table.addCell(getSmallCellData(vStr, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,1,null,null, null,true));


        //ADUANA
        table.addCell(getCellData("", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));


        table.addCell(getCellData("Impuestos:", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        vStr = " ";
        if (this.notaCreditoDataDTO.getImpuestoVentas()!=null) {
            if (this.defaultTC!=null) {
                vStr = this.defaultTC.getSimbol() + vStr;
            }
            vStr = vStr + formatNumber.format(this.notaCreditoDataDTO.getImpuestoVentas()) ;
        }
        table.addCell(getSmallCellData(vStr, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,1,null,null, null,true));





        table.addCell(getCellData("", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));

        table.addCell(getCellData("Total:", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        vStr = " ";
        if (this.notaCreditoDataDTO.getTotal()!=null) {
            if (this.defaultTC!=null) {
                vStr = this.defaultTC.getSimbol() + vStr;
            }
            vStr = vStr + formatNumber.format(this.notaCreditoDataDTO.getTotal()) ;
        }
        table.addCell(getSmallCellData(vStr, 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,1,null,null, null,true));

        // PROVEEDOR
        table.addCell(getCellData("", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        table.addCell(getCellData("", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR,null,null,null, null));


        table.addCell(getCellData("Monto Anticipado:", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        vStr = " ";
        if (this.notaCreditoDataDTO.getMontoAnticipado()!=null) {
            if (this.defaultTC!=null) {
                vStr = this.defaultTC.getSimbol() + vStr;
            }
            vStr = vStr + formatNumber.format(this.notaCreditoDataDTO.getMontoAnticipado()) ;
        }
        table.addCell(getSmallCellData(vStr, 8f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,1,null,null, null,true));

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



        float[] columnWidths0 = {12};
        PdfPTable table = new PdfPTable(columnWidths0);
        table.getDefaultCell().setBackgroundColor(TOTAL_BK_COLOR);

        //table1.setWidthPercentage(100);
        table.getDefaultCell().setBorder(0);
        table.getDefaultCell().setBorderWidth(0);


        table.getDefaultCell().setPadding(0);
        table.getDefaultCell().setBorderWidth(0);
        table.addCell(getCellTitle("OBSERVACIONES",6f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = getCellData(this.notaCreditoDataDTO.getObservaciones(), 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,null,null,null, null);
        //cell.setColspan(4);
        //cell.setRowspan(2);
        table.addCell(cell);
        cell = new PdfPCell(table);
        cell.setBorder(0);
        cell.setBorderWidth(0);
        cell.setRowspan(3);
        rootTable.addCell(cell);

        cell = getCellData("Saldo Pendiente:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null);
        cell.setBorderWidth(0);
        cell.setBorder(0);
        rootTable.addCell(cell);

        String saldoPendienteStr = " ";
        if (this.notaCreditoDataDTO.getSaldoPendiente()!=null) {
            if (this.defaultTC!=null) {
                saldoPendienteStr = this.defaultTC.getSimbol() + saldoPendienteStr;
            }
            saldoPendienteStr = saldoPendienteStr + formatNumber.format(this.notaCreditoDataDTO.getSaldoPendiente());
        }

        rootTable.addCell(getSmallCellData(saldoPendienteStr, 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,1,null,null, null,false));
        if (this.esFacturaTipoCambioSystem()) {
            rootTable.addCell(getCellData(" ", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR, null, null, null, null));
            rootTable.addCell(getCellData(" ", 7f, Font.NORMAL, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, TOTAL_BK_COLOR, null, null, null, null));
        } else {
            rootTable.addCell(getCellData("Saldo Pendiente Dolares:", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));

            saldoPendienteStr = " ";
            if (this.notaCreditoDataDTO.getSaldoPendiente()!=null) {
                if (this.defaultTC!=null) {
                    saldoPendienteStr = this.notaCreditoDataDTO.getTipoCambio().getSimbol() + saldoPendienteStr;
                }
                saldoPendienteStr = saldoPendienteStr + formatNumber.format(this.notaCreditoDataDTO.getSaldoPendienteMoneda());
            }

            rootTable.addCell(getSmallCellData(saldoPendienteStr, 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, null,1,null,null, null,true));
        }

        rootTable.addCell(getCellData(" ", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));
        rootTable.addCell(getCellData(" ", 7f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_RIGHT, TOTAL_BK_COLOR,null,null,null, null));

        return rootTable;
    }

    private boolean esFacturaTipoCambioSystem() {
        boolean esFacturaTipoCambioSystem = false;

        if (this.defaultTC==null) {
            return esFacturaTipoCambioSystem;
        }
        if (this.defaultTC!=null && this.defaultTC.getId()== this.notaCreditoDataDTO.getTipoCambio().getId()) {
            esFacturaTipoCambioSystem = true;
        }
        return esFacturaTipoCambioSystem;
    }

//    private void initDefaultTC() {
//        if (this.notaCreditoDataDTO.getMonedas()==null) {
//            return;
//        }
//        TipoCambio tcResult = null;
//        for (TipoCambio tc: this.notaCreditoDataDTO.getMonedas()) {
//            if (tc.getEsDefault()==1) {
//                this.defaultTC = tc;
//                break;
//            }
//        }
//    }

    private void initDefaultTC() {

        if (this.notaCreditoDataDTO.getTipoCambio()==null) {
            return;
        }
        this.defaultTC = this.notaCreditoDataDTO.getTipoCambio();

    }

    public PdfPTable createReciboTable(Document d) throws IOException, DocumentException {

        float[] columnWidths1 = {10};
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


        float[] columnWidths = {2,2,2,2,2};
        PdfPTable reciboTable = new PdfPTable(columnWidths);
        reciboTable.setWidthPercentage(100);
        reciboTable.getDefaultCell().setBorder(0);
        reciboTable.getDefaultCell().setPadding(0);

        reciboTable.getDefaultCell().setBorderWidth(0);

        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData(" ", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("_____________________", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData(this.notaCreditoDataDTO.getEncargado(), 8f, Font.UNDERLINE, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));


        reciboTable.addCell(getCellData("Nombre Completo", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("No. de Cédula", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("Firma", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("Fecha", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable.addCell(getCellData("Realizado Por", 8f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_CENTER, null,null,null,null, null));
        reciboTable0.addCell(reciboTable);

        return rootTable1;
    }

    public PdfPTable createLeyDevolucionesTable() throws IOException, DocumentException {

        float[] columnWidths1 = {10};
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
        if (this.notaCreditoDataDTO.getEnviadaHacienda()==1 || this.notaCreditoDataDTO.getEnviadaHacienda()==0) {
            cell = getCellData("Autorizado mediante la resolucion DGT-R-033-2019 del veinte de junio de dos mil diecinueve de la Direccion General de Tributacion", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, 1, null, null, null);
        } else {
            cell = getCellData("Autorizado mediante la resolucion DGT-R-033-2019 del veinte de junio de dos mil diecinueve de la Direccion General de Tributacion", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, 1, null, null, null);

        }
        //cell.setRowspan(3);
        rootTable1.addCell(cell);
        if (this.notaCreditoDataDTO.getEnviadaHacienda()==1 || this.notaCreditoDataDTO.getEnviadaHacienda()==0) {

        } else {
            //cell = getCellData("", 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null, 1, null, null, null);
        }
        return rootTable1;
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


    protected Image getEmptyLogo() throws IOException, DocumentException {
        //ClassLoader.getSystemClassLoader().
        URL urlFondo= this.getClass().getResource( "/logo/logo.png" );
        Image image = Image.getInstance(urlFondo);
        image.scaleAbsoluteHeight(30);
        image.scaleAbsoluteWidth(30);
        image.setAlignment(Image.ALIGN_RIGHT);
        return image;
    }

    protected Image getLogo(String fileName) throws IOException, DocumentException {
        return getEmptyLogo();
        //ClassLoader.getSystemClassLoader().
//        URL urlFondo= this.getClass().getResource( "/logo/logo.png" );
        //Image image = new ImageIcon(fullPath).getImage();
//        Image image = ImageIO.read(new File(fullPath));
        //Image image = Image.getInstance(urlFondo);
//        if (Strings.isNullOrEmpty(fileName)) {
//            return getEmptyLogo();
//        }
//        try {
//
//
//            Image image = Image.getInstance(FULL_LOGO_PATH + fileName);
//            image.scaleAbsoluteHeight(30);
//            image.scaleAbsoluteWidth(30);
//            image.setAlignment(Image.ALIGN_RIGHT);
//            return image;
//        }catch (Exception e) {
//            e.printStackTrace();
//            return getEmptyLogo();
//        }
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
        cell.setPaddingBottom(2);
        cell.setPaddingTop(2);

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

    protected PdfPCell getCellData(String data, Float size, int style, BaseColor color, int align,BaseColor bg,Integer border, BaseColor borderColor, Integer valignment,Integer colSpan, Integer rowSpan, Integer cellFixedHeight) {
        Font f = new Font(Font.getFamily("CALIBRI"), size, style, color != null ? color : BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(data, f));
        cell.setHorizontalAlignment(align);
        //cell.setPadding(5);
        cell.setPaddingBottom(2);
        cell.setPaddingTop(2);

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
            cell.setBackgroundColor(borderColor);

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

    public PdfPTable createRazonTipo() throws IOException, DocumentException {

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

        //rootTable1.addCell(getCellTitle("Razon",7f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));


        float[] columnWidths7 = {7};
        PdfPTable razonTable = new PdfPTable(columnWidths7);
        razonTable.getDefaultCell().setBackgroundColor(TOTAL_BK_COLOR);

        //table1.setWidthPercentage(100);
        razonTable.getDefaultCell().setBorder(0);
        razonTable.getDefaultCell().setBorderWidth(0);
        razonTable.setWidthPercentage(100);
        razonTable.addCell(getCellTitle("Razon Nota Credito",8f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        float[] columnWidths3 = {3};
        PdfPTable refTable = new PdfPTable(columnWidths3);
        refTable.getDefaultCell().setBackgroundColor(TOTAL_BK_COLOR);

        //table1.setWidthPercentage(100);
        refTable.getDefaultCell().setBorder(0);
        refTable.getDefaultCell().setBorderWidth(0);
        refTable.setWidthPercentage(100);
        refTable.addCell(getCellTitle("Referencia",8f,Font.BOLD, BaseColor.WHITE, TABLE_BORDER_COLOR,Element.ALIGN_CENTER,false,null,null));

        PdfPCell cell;
        cell = getCellData(this.clave, 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null);
        //cell.setRowspan(3);
        refTable.addCell(cell);

//        rootTable1.addCell(cell);
        cell = getCellData(this.notaCreditoDataDTO.getRazon(), 6f, Font.BOLD, TABLE_BODY_FONT_COLOR, Element.ALIGN_LEFT, null,1,null,null, null);
        //cell.setRowspan(3);

        razonTable.addCell(cell);
        rootTable1.addCell(razonTable);
        rootTable1.addCell(refTable);

        return rootTable1;

    }

    public Integer getLines() {
        return lines;
    }

    public void setLines(Integer lines) {
        this.lines = lines;
    }
}