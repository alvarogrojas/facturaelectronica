package com.rfs.resolver;

import com.rfs.view.PdfFacturaView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;


public class PdfFacturaViewResolver implements ViewResolver {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
      //  ExcelFacturaView view = new ExcelFacturaView();
        PdfFacturaView view = new PdfFacturaView();

        //view.setUrl("factura");
        view.setApplicationContext(applicationContext);
        return view;
    }
}
