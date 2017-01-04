package com.msc.facturierws.helper;

import com.msc.facturierws.entity.Client;
import com.msc.facturierws.entity.Facture;
import com.msc.facturierws.entity.MonEntreprise;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micky
 */
public class FreeMarkerHelper {

    private static Configuration cfg;

    static {
        try {
            cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDirectoryForTemplateLoading(new File("."));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
        } catch (IOException ex) {
            Logger.getLogger(FreeMarkerHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String convert(Facture facture, Client client, MonEntreprise moi) {

        Map<String, Object> root = new HashMap<>();
        root.put("moi", moi);
        root.put("client", client);
        root.put("facture", facture);

        Template temp;
        try {
            temp = cfg.getTemplate("template1.tpl");
            StringWriter sw = new StringWriter();
            temp.process(root, sw);
            return sw.toString();
        } catch (MalformedTemplateNameException ex) {
            Logger.getLogger(FreeMarkerHelper.class.getName()).log(Level.INFO, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FreeMarkerHelper.class.getName()).log(Level.INFO, null, ex);
        } catch (IOException | TemplateException ex) {
            Logger.getLogger(FreeMarkerHelper.class.getName()).log(Level.INFO, null, ex);
        }
        return null;
    }

}
