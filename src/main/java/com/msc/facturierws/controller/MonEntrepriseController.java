package com.msc.facturierws.controller;

import com.msc.facturierws.dao.MonEntrepriseDAO;
import com.msc.facturierws.dao.specif.DAOSpecif;
import com.msc.facturierws.entity.MonEntreprise;
import com.msc.facturierws.entity.MoyenDePaiement;
import com.msc.rest.tokenrestjersey.Secured;
import com.msc.rest.tokenrestjersey.Token;
import com.msc.rest.tokenrestjersey.TokenController;
import com.msc.rest.tokenrestjersey.TokenHelper;
import com.msc.rest.tokenrestjersey.helper.Helper;
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
 * @author micky
 */
@Path("/moi")
@Produces(MediaType.APPLICATION_JSON)
@Resource
public class MonEntrepriseController extends TokenController {

    @GET
    @Path("/moi")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public MonEntreprise getMoi(@Context SecurityContext securityContext) {
        try {
            MonEntrepriseDAO medao = (MonEntrepriseDAO) DAOSpecif.getInstance(MonEntrepriseDAO.class, securityContext);
            MonEntreprise me = medao.getObjectById(1);
            return me;
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("/existe")
    @Produces(MediaType.APPLICATION_JSON)
    public Helper<Boolean> haveMonEntreprise() {
        MonEntrepriseDAO medao = (MonEntrepriseDAO) DAOSpecif.getInstance(MonEntrepriseDAO.class, null);
        try {
            Helper<Boolean> bh = new Helper<>();
            bh.setMyObject(medao.isExiste());
            return bh;
        } catch (SQLException ex) {
            Logger.getLogger(MonEntrepriseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @POST
    @Path("/insert")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Token insert(@BeanParam MonEntreprise me, @Context SecurityContext securityContext) {
        try {
            MonEntrepriseDAO medao = (MonEntrepriseDAO) DAOSpecif.getInstance(MonEntrepriseDAO.class, securityContext);
            medao.insert(me);
            return (TokenHelper.newToken(securityContext));
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @POST
    @Path("/update")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Token update(@BeanParam MonEntreprise me, @Context SecurityContext securityContext) {
        try {
            MonEntrepriseDAO medao = (MonEntrepriseDAO) DAOSpecif.getInstance(MonEntrepriseDAO.class, securityContext);
            medao.update(me);
            return (TokenHelper.newToken(securityContext));
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("/mdp")
    @Produces(MediaType.APPLICATION_JSON)
    public Helper<String> getMoyenDePaiement() {

        StringBuilder sb = new StringBuilder();
        for (MoyenDePaiement mdp : MoyenDePaiement.values()) {
            sb.append(mdp.name());
            sb.append(MonEntreprise.SEPARATOR_MENTION_LEGAL);
        }
        sb = sb.delete(sb.length() - 1, sb.length());
        Helper<String> hs = new Helper<>();
        hs.setMyObject(sb.toString());
        return hs;
    }

}
