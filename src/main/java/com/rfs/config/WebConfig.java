package com.rfs.config;

import com.rfs.resolver.ExcelFacturaViewResolver;
import com.rfs.resolver.ExcelFacturacionRegistroViewResolver;
import com.rfs.resolver.ExcelViewResolver;
import com.rfs.resolver.PdfFacturaViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.APPLICATION_JSON)
                .favorPathExtension(true);
    }

    /*
     * Configure ContentNegotiatingViewResolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        // Define all possible view resolvers
        List<ViewResolver> resolvers = new ArrayList<>();

        resolvers.add(pdfViewResolver());
        resolvers.add(excelViewResolver());

        resolvers.add(excelFacturaViewResolver());
        resolvers.add(excelFacturacionRegistroViewResolver());

        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    /*
     * Configure View resolver to provide XLS output using Apache POI library to
     * generate XLS output for an object content
     */
    @Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }


    @Bean
    public ViewResolver excelFacturaViewResolver() {
        return new ExcelFacturaViewResolver();
    }
    @Bean
    public ViewResolver excelFacturacionRegistroViewResolver() {
        return new ExcelFacturacionRegistroViewResolver();
    }

    /*
     * Configure View resolver to provide Pdf output using iText library to
     * generate pdf output for an object content
     */
    @Bean
    public ViewResolver pdfViewResolver() {

        return new PdfFacturaViewResolver();
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Estimado Sr(a) %s \n\n Le adjuntamos su Factura Electronica por nuestros servicios" +
                "\n\n Atte : %s , Saludos!!\n");
        return message;
    }
}