package com.msc.facturierws.helper;

import com.msc.facturierws.entity.Facture;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author micky
 */
public class RecivedPDF {

    public static InputStream getPDF(Facture facture, String factureHTML) {

        try {
            String url = "http://localhost/testpdf/";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            List<NameValuePair> nvps = new ArrayList<>();

            nvps.add(new BasicNameValuePair("content", Base64.encodeBase64String(factureHTML.getBytes(Charset.forName("UTF-8"))).replace("+", "%2B")));//.replace("'", "\\'").replace("\"", "\\\"")));
            nvps.add(new BasicNameValuePair("filename", "facture.pdf"));

            String urlParameters = "";
            for (NameValuePair np : nvps) {
                urlParameters += np.getName() + "=" + np.getValue() + "&";
            }

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            return con.getInputStream();

        } catch (MalformedURLException ex) {
            Logger.getLogger(RecivedPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RecivedPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecivedPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
