package com.msc.facturierws.test;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author micky
 */
public class HtmlToPdfTest {

    public HtmlToPdfTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setPassword method, of class Password.
     */
//    @Ignore
//    public void postTemplate() {
//        try {
//            CloseableHttpResponse response2 = null;
//            URL url = null;
//
//            url = HtmlToPdfTest.class.getResource("/facture.html");
//            CloseableHttpClient httpclient = HttpClients.createDefault();
//            HttpPost httpPost = new HttpPost("http://freehtmltopdf.com");
//
//            String template = IOUtils.toString(url.toURI(), Charset.defaultCharset());
//
//            List<NameValuePair> nvps = new ArrayList<>();
//            nvps.add(new BasicNameValuePair("convert", ""));
//            nvps.add(new BasicNameValuePair("html", template));
//            nvps.add(new BasicNameValuePair("baseurl", "http://www.myhost.com"));
//            nvps.add(new BasicNameValuePair("enablejs", "0"));
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//
//            //set header
//            Header h1 = new BasicHeader("Content-type", "application/x-www-form-urlencoded");
//            httpPost.addHeader(h1);
//            Header[] h = httpPost.getAllHeaders();
//            System.out.println(Arrays.toString(h));
//            response2 = httpclient.execute(httpPost);
//
//            System.out.println(response2.getStatusLine());
//            HttpEntity entity2 = response2.getEntity();
//            InputStream is = entity2.getContent();
//            FileOutputStream fos = new FileOutputStream("factureTest.pdf");
//            IOUtils.copy(is, fos);
//            IOUtils.closeQuietly(fos);
//            EntityUtils.consume(entity2);
//            response2.close();
//            assertNotNull(url);
//        } catch (URISyntaxException | IOException ex) {
//            Logger.getLogger(HtmlToPdfTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Test
    public void test2() {

        try {
            URL urlTpl = HtmlToPdfTest.class.getResource("/facture.html");
            String template = IOUtils.toString(urlTpl.toURI(), Charset.forName("UTF-8"));

            String url = "http://micky.ovh/testpdf/";
                URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            List<NameValuePair> nvps = new ArrayList<>();
            
            nvps.add(new BasicNameValuePair("content", Base64.encode(template.getBytes(Charset.forName("UTF-8"))).replace("+", "%2B")));//.replace("'", "\\'").replace("\"", "\\\"")));
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

           // int responseCode = con.getResponseCode();
           // System.out.println("\nSending 'POST' request to URL : " + url);
           // System.out.println("Post parameters : " + urlParameters);
           // System.out.println("Response Code : " + responseCode);

            //StringWriter sw = new StringWriter();
            // WriterOutputStream fos = new WriterOutputStream(sw, Charset.forName("UTF-8"));
            FileOutputStream fos = new FileOutputStream("factureTest.pdf");
            IOUtils.copy(con.getInputStream(), fos);
            IOUtils.closeQuietly(fos);

            //     Logger.getLogger(HtmlToPdfTest.class.getName()).log(Level.INFO, sw.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(HtmlToPdfTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HtmlToPdfTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HtmlToPdfTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(HtmlToPdfTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
