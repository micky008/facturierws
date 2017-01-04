package com.msc.facturierws.main;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.rest.tokenrestjersey.TokenFilter;
import com.msc.rest.tokenrestjersey.TokenGarbage;
import com.msc.facturierws.controller.ClientController;
import com.msc.facturierws.controller.FactureController;
import com.msc.facturierws.controller.LoginController;
import com.msc.facturierws.controller.MonEntrepriseController;
import com.msc.facturierws.helper.DateParamConverterProvider;
import com.msc.facturierws.helper.DebugMode;
import com.msc.facturierws.helper.GsonJerseyCustomMessageBody;
import com.msc.facturierws.security.Password;
import com.msc.rest.tokenrestjersey.helper.Helper;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Micky
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length == 0) {
            System.out.println("ou se trouve config.properties");
            System.out.println("de base il est dans ../conf/");
            System.exit(0);
        }
        FileReader fr = new FileReader(args[0]);
        Properties prop = new Properties();
        prop.load(fr);
        fr.close();
        Password p = new Password();
        p.setKey(prop.getProperty("bdd.key"));
        p.setPassword(prop.getProperty("bdd.password"));
        String password = p.getConvertedClearPassword();
        prop.setProperty("bdd.password", password);
        DebugMode.setDebugMode(Boolean.valueOf(prop.getProperty("debug.mode")));
        DAO.initConnection(prop);
        String domain = prop.getProperty("ws.domain");
        if (!domain.endsWith("/")) {
            domain = domain + "/";
        }
        int port = Integer.parseInt(prop.getProperty("ws.port"));
        URI baseUri = UriBuilder.fromUri(domain).port(port).build(new Object[0]);
        Set<Class<?>> clazzs = new HashSet();
        clazzs.add(MultiPartFeature.class); //a garder !!! permet de faire de l'upload
        clazzs.add(TokenFilter.class);
        clazzs.add(DateParamConverterProvider.class);
        clazzs.add(ClientController.class);
        clazzs.add(MonEntrepriseController.class);
        clazzs.add(LoginController.class);
        clazzs.add(FactureController.class);

        ResourceConfig userResources = new ResourceConfig(clazzs);
        userResources.register(DeclarativeLinkingFeature.class);
        userResources.registerClasses(GsonJerseyCustomMessageBody.class);
        TokenGarbage sb = new TokenGarbage();
        //Thread t = new Thread(sb);
        //t.start();
        JdkHttpServerFactory.createHttpServer(baseUri, userResources, true);
    }

}
