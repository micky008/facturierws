package com.msc.facturierws.controller;

import com.msc.rest.tokenrestjersey.Secured;
import com.msc.rest.tokenrestjersey.TokenController;
import com.msc.rest.tokenrestjersey.TokenHelper;
import com.msc.rest.tokenrestjersey.helper.ListHelper;
import com.msc.facturierws.dao.ClientDao;
import com.msc.facturierws.dao.specif.DAOSpecif;
import com.msc.facturierws.entity.Client;
import com.msc.rest.tokenrestjersey.Token;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Micky
 */
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
@Resource
public class ClientController extends TokenController {

    @GET
    @Path("/all")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public ListHelper<Client> getAllClients(@Context SecurityContext securityContext) {
        try {
            ClientDao cdao = (ClientDao) DAOSpecif.getInstance(ClientDao.class, securityContext);
            ListHelper<Client> te = new ListHelper<>();
            te.setList(cdao.getAll());
            te.setToken(TokenHelper.newToken(securityContext));
            return te;
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insert")
    @Secured
    public Token insertClient(@BeanParam Client client, @Context SecurityContext securityContext) {

        try {         
            ClientDao cdao = (ClientDao) DAOSpecif.getInstance(ClientDao.class, securityContext);
            cdao.insert(client);    
            return TokenHelper.newToken(securityContext);
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    @Secured
    public Token updateClient(@BeanParam Client client, @Context SecurityContext securityContext) {
        try {            
            ClientDao cdao = (ClientDao) DAOSpecif.getInstance(ClientDao.class, securityContext);
            cdao.update(client);
            return TokenHelper.newToken(securityContext);
       } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    
}
