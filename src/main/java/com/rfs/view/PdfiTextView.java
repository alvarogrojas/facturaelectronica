package com.rfs.view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public abstract class PdfiTextView extends AbstractView {


    final int PAGE_SIZE =12;


    final float MARGIN_OF_ONE_CM = 28.8f;

    static final Float TABLE_BODY_FONT_SIZE = 6f;

    static final Float FONT_HEADER_SIZE = 8f;


    static final BaseColor BACK_GROUND_TABLE_HEADER_COLOR = new BaseColor(23,169,227);


    static final BaseColor TABLE_HEADER_FONT_COLOR = BaseColor.WHITE;
    static final GrayColor TABLE_BODY_FONT_COLOR = GrayColor.GRAYBLACK;

    static final String INGPRO_IMG = "resources/img/logo-planilla.png";


    private Integer lines;

    class MyFooter extends PdfPageEventHelper {
        //protected PdfPTable header;

        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();

            Phrase footer = new Phrase("Pagina : " + writer.getPageNumber(), new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC));
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }


    }



    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // IE workaround: write into byte array first.
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        // Apply preferences and build metadata.
        Document document = new Document(PageSize.A4, 36, 36, 10, 36);
        PdfWriter writer = newWriter(document, baos);
        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);

        // Build PDF document.
        document.open();
        buildPdfDocument(model, document, writer, request, response);
        document.close();

        // Flush to HTTP response.
        writeToResponse(response, baos);
    }



    protected abstract void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception;



    public PdfiTextView() {
        setContentType("application/pdf");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }




    protected Document newDocument() {
        return new Document(PageSize.A4);
    }

    protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }

    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
            throws DocumentException {

        writer.setViewerPreferences(getViewerPreferences());
    }

    protected int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }

    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
    }


    protected Paragraph getTitle(String parafo, float size , int align, int style, BaseColor baseColor) throws DocumentException {
        Font f = new Font(Font.FontFamily.HELVETICA, size, style, baseColor);
        Paragraph preface=new Paragraph(parafo,f);
        preface.setAlignment(align);
        return preface;
    }


    protected Image getLogo() throws IOException, DocumentException {
        Image image = Image.getInstance(INGPRO_IMG);
        image.scaleAbsolute(100,50);
        image.setAlignment(Image.ALIGN_LEFT);
        return image;
    }


    protected Paragraph tableTitle(String titleTable){
        Font f = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD,BaseColor.BLACK);
        Paragraph titleP = new Paragraph(titleTable,f);
        titleP.setAlignment(Element.ALIGN_LEFT);
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        titleP.add(dottedline);
        return titleP;
    }

    protected PdfPCell getCellTitle(String tile, Float size, int style, BaseColor color, int align, boolean border, Integer colSpan, Integer rowSpan) {
        Font f = new Font(Font.FontFamily.HELVETICA, size, style, color);
        PdfPCell cell = new PdfPCell(new Phrase(tile, f));
        if(!border)
            cell.setBorder(PdfPCell.NO_BORDER);
        //cell.setBackgroundColor(BACK_GROUND_TABLE_HEADER_COLOR);
        cell.setHorizontalAlignment(align);
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

    protected PdfPCell getCellHeader(String tile, Float size, int style, BaseColor color, int align, Integer border, Integer colSpan, Integer rowSpan) {
        //Se crea que
        Font f = new Font(Font.FontFamily.HELVETICA, size, style, color!=null?color:BaseColor.BLACK);
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
        Font f = new Font(Font.FontFamily.HELVETICA, size, style, color!=null ? color: BaseColor.BLACK);
        PdfPCell cell = new PdfPCell(new Phrase(data, f));
        cell.setHorizontalAlignment(align);
        //cell.setPadding(5);
        cell.setPaddingBottom(5);

        if(valignment!=null)
            cell.setVerticalAlignment(valignment);

        cell.setBorder(border!=null?border : PdfPCell.NO_BORDER);


        if(bg!=null)
            cell.setBackgroundColor(bg);

        if (colSpan != null)
            cell.setColspan(colSpan);
        if (rowSpan != null)
            cell.setRowspan(rowSpan);
        return cell;
    }

    public Integer getLines() {
        return lines;
    }

    public void setLines(Integer lines) {
        this.lines = lines;
    }
}
