package com.msc.facturierws.controller;

import com.msc.facturierws.dao.ClientDao;
import com.msc.facturierws.dao.FactureDao;
import com.msc.facturierws.dao.MonEntrepriseDAO;
import com.msc.facturierws.dao.specif.DAOSpecif;
import com.msc.facturierws.entity.Facture;
import com.msc.facturierws.helper.FreeMarkerHelper;
import com.msc.facturierws.helper.RecivedPDF;
import com.msc.rest.tokenrestjersey.Secured;
import com.msc.rest.tokenrestjersey.Token;
import com.msc.rest.tokenrestjersey.TokenController;
import com.msc.rest.tokenrestjersey.TokenHelper;
import com.msc.rest.tokenrestjersey.helper.Helper;
import com.msc.rest.tokenrestjersey.helper.ListHelper;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author micky
 */
@Path("/facture")
@Produces(MediaType.APPLICATION_JSON)
@Resource
public class FactureController extends TokenController {
    
    @GET
    @Path("/search/{id}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Facture getFactureById(@PathParam("id") String noFacture, @Context SecurityContext sc) {
        try {
            FactureDao fdao = (FactureDao) DAOSpecif.getInstance(FactureDao.class, sc);
            return fdao.getFactureByNoFacture(noFacture);
        } catch (SQLException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GET
    @Path("/number/{date}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Helper<Integer> getLastNumber(@PathParam("date") String date, @Context SecurityContext sc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            FactureDao fdao = (FactureDao) DAOSpecif.getInstance(FactureDao.class, sc);
            Helper<Integer> hi = new Helper<>();
            hi.setMyObject(fdao.getLastCount(sdf.parse(date)));
            hi.setToken(TokenHelper.newToken(sc));
            return hi;
        } catch (SQLException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GET
    @Path("/search/{noFacture}/{date}/{idClient}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public ListHelper<Facture> getFactureBySearch(@PathParam("noFacture") String noFacture, @PathParam("date") String dateStr, @PathParam("idClient") Integer idClient, @Context SecurityContext sc) {
        try {
            FactureDao fdao = (FactureDao) DAOSpecif.getInstance(FactureDao.class, sc);
            Date date = null;
            if (noFacture.equals("null")) {
                noFacture = null;
            }
            if (!dateStr.equals("19700101")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                date = sdf.parse(dateStr);
            }
            List<Facture> lf = fdao.getFactureBySearch(noFacture, date, idClient);
            ListHelper<Facture> lhf = new ListHelper<>();
            lhf.setList(lf);
            lhf.setToken(TokenHelper.newToken(sc));
            return lhf;
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @GET
    @Path("/ferme/{noFacture}")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Token fermeFacture(@PathParam("noFacture") String noFacture, @Context SecurityContext sc) {
        try {
            FactureDao fdao = (FactureDao) DAOSpecif.getInstance(FactureDao.class, sc);
            fdao.fermeFacture(noFacture);
            return TokenHelper.newToken(sc);
        } catch (SQLException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @POST
    @Path("/insert/")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Token insertFacture(@BeanParam Facture facture, @Context SecurityContext sc) {
        try {
            facture.generateLines();
            FactureDao fdao = (FactureDao) DAOSpecif.getInstance(FactureDao.class, sc);
            fdao.insert(facture);
            return TokenHelper.newToken(sc);
        } catch (SQLException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @POST
    @Path("/update/")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Token updateFacture(@BeanParam Facture facture, @Context SecurityContext sc) {
        try {
            facture.generateLines();
            FactureDao fdao = (FactureDao) DAOSpecif.getInstance(FactureDao.class, sc);
            fdao.update(facture);
            return TokenHelper.newToken(sc);
        } catch (SQLException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @POST
    @Path("/print")
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Helper<String> printFacture(@BeanParam Facture facture, @Context SecurityContext sc) {
        try {
            facture.generateLines();
            MonEntrepriseDAO medao = (MonEntrepriseDAO) DAOSpecif.getInstance(MonEntrepriseDAO.class, sc);
            ClientDao cdao = (ClientDao) DAOSpecif.getInstance(ClientDao.class, sc);
            String templateFull = FreeMarkerHelper.convert(facture, cdao.getClientByNoFacture(facture.getNoFacture()), medao.getObjectById(1));
            InputStream is = RecivedPDF.getPDF(facture, templateFull);
            String res = new String(Base64.encodeBase64(IOUtils.toByteArray(is)));
            Helper<String> hi = new Helper<>();
            hi.setMyObject(res);
            hi.setToken(TokenHelper.newToken(sc));
            return hi;
        } catch (SQLException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FactureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
