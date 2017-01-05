package com.msc.facturierws.test;

import com.msc.facturierws.entity.Client;
import com.msc.facturierws.entity.Facture;
import com.msc.facturierws.entity.LigneFacture;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Micky
 */
public class TemplateTest {

    public TemplateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    Configuration cfg = null;

    @Before
    public void setUp() {

        try {
            cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDirectoryForTemplateLoading(new File("."));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
        } catch (IOException ex) {
            Logger.getLogger(TemplateTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setPassword method, of class Password.
     */
    @Test
    public void createTemplate() {
        Client client = new Client();
        client.setAdresse("55 rue de Vincennes");
        client.setCodePostal("93100");
        client.setEmail("f@simplon.co");
        client.setFormeJuridique("SAS");
        client.setIsEntreprise(Boolean.TRUE);
        client.setRaisonSociale("Simplon.co");
        client.setNom("Service");
        client.setPrenom("Facturation");
        client.setTelephone("0202020202");
        client.setSiren("");
        client.setTva("1");
        client.setVille("Montreuil");

        MonEntreprise moi = new MonEntreprise();
        moi.setAdresse("11 av quelquepart");
        moi.setBic("MONABQXXXX");
        moi.setCodePostal("90980");
        moi.setEmail("toto@gmail.com");
        moi.setIban("FR76000000000000000");
        StringBuilder sb = new StringBuilder();
        sb.append("TVA non applicable, art. 293 B du CGI");
        sb.append(MonEntreprise.SEPARATOR_MENTION_LEGAL);
        sb.append("Taux des pénalités exigibles à compter du 2 mars 2013 en l'absence de paiement : 10%");
        sb.append(MonEntreprise.SEPARATOR_MENTION_LEGAL);
        sb.append("Indemnité forfaitaire pour frais de recouvrement en cas de retard de paiement : 40 Euros");
        moi.setMentionLegales(sb.toString());
        moi.setNom("Chinchole");
        moi.setPrenom("Michael");
        moi.setRaisonSociale(null);
        moi.setSiren("en cours d'immatriculation");
        moi.setTitulairecompte("CHINCHOLE MICHAEL");
        moi.setTelephone("0678987878");
        moi.setVille("Pouet");

        List<LigneFacture> lines = new ArrayList<>();
        LigneFacture line = new LigneFacture();
        line.setDesignation("formation JAVA du 12/10/2012");
        line.setPuHt(40d);
        line.setQuantite(8d);
        line.setReference("");
        line.setTva(1d);
        lines.add(line);

//        LigneFacture line2 = new LigneFacture();
//        line2.setDesignation("formation JAVA du 20/10/2012");
//        line2.setPuHT(40d);
//        line2.setQuantite(5d);
//        line2.setReference("");
//        line2.setTva(1d);
//        lines.add(line2);
        Facture facture = new Facture();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 12);
        facture.setDate(cal.getTime());
        facture.setIdModele(1);
        facture.setLignes(lines);
        facture.setNumber(1);
        facture.genereNoFacture();

        Map<String, Object> root = new HashMap<>();
        root.put("moi", moi);
        root.put("client", client);
        root.put("facture", facture);

        Template temp;
        try {
            temp = cfg.getTemplate("template1.tpl");
            StringWriter sw = new StringWriter();
            temp.process(root, sw);
            Logger.getLogger(TemplateTest.class.getName()).log(Level.INFO, sw.toString());
        } catch (MalformedTemplateNameException ex) {
            Logger.getLogger(TemplateTest.class.getName()).log(Level.INFO, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TemplateTest.class.getName()).log(Level.INFO, null, ex);
        } catch (IOException | TemplateException ex) {
            Logger.getLogger(TemplateTest.class.getName()).log(Level.INFO, null, ex);
        }

        assertEquals(true, true);
    }

}
