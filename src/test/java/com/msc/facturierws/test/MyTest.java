package com.msc.facturierws.test;

import com.msc.facturierws.security.Password;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Classe de test qui ne fais absolument pas parti du projet. Et juste la car
 * c'était le projet ouvert.
 *
 * @author micky
 */
public class MyTest {

    @Ignore
    public void calculeDannee() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d1 = sdf.parse("24/10/1961");

            long diff = System.currentTimeMillis() - d1.getTime();

            System.out.println(Math.rint((diff / (1000 * 60 * 60 * 24)) / 365));

        } catch (ParseException ex) {
            Logger.getLogger(MyTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testPasswordToCryptage() {
        Password p = new Password();
        p.setPassword("Password avec une maj au debut et * a la fin");
        p.setKey("idem que password");
        String[] passwords = p.getConvertedHexaPassword();
        System.out.println(passwords[0]);
        System.out.println(passwords[1]);
    }

}
