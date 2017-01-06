package com.msc.facturierws.entity;

//
import com.msc.dao.daoproject.annotation.Id;
import com.msc.dao.daoproject.annotation.Name;
import com.msc.dao.daoproject.annotation.StaticField;
import com.msc.rest.tokenrestjersey.TokenEntity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Facture.java
//  @ Date : 13/11/2016
//  @ Author : 
//
//
@Name(name = "factures")
@XmlRootElement(name = "Facture")
@Produces(MediaType.APPLICATION_JSON)
public class Facture extends TokenEntity {

    @Id
    @FormParam("noFacture")
    private String noFacture;

    @FormParam("idClient")
    private Integer idClient;
    @FormParam("idModele")
    private Integer idModele;
    @FormParam("dateDuJour")
    private Date dateDuJour;
    @FormParam("dateRegle")
    private Date dateRegle;
    @FormParam("isFinish")
    private Boolean isFinish;
    @FormParam("isRegle")
    private Boolean isRegle;
    @FormParam("commentRegle")
    private MoyenDePaiement commentRegle;
    @FormParam("resteAPayer")
    private Double resteAPayer;
    @FormParam("autreMoyenDePaiment")
    private String autreMoyenDePaiment;

    @StaticField
    @FormParam("lignesStr")
    private String lignesStr;
    @StaticField
    private List<LigneFacture> lignes;
    @StaticField
    private Client client;
    @StaticField
    private Integer number;

    /**
     * @return the idClient
     */
    public Integer getIdClient() {
        return idClient;
    }

    /**
     * @param idClient the idClient to set
     */
    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    /**
     * @return the idModele
     */
    public Integer getIdModele() {
        return idModele;
    }

    /**
     * @param idModele the idModele to set
     */
    public void setIdModele(Integer idModele) {
        this.idModele = idModele;
    }

    /**
     * @return the dateDuJour
     */
    public Date getDateDuJour() {
        return dateDuJour;
    }

    /**
     * @param date the dateDuJour to set
     */
    public void setDateDuJour(Date date) {
        this.dateDuJour = date;
    }

    /**
     * @return the isFinish
     */
    public Boolean getIsFinish() {
        return isFinish;
    }

    /**
     * @param isFinish the isFinish to set
     */
    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * @return the isRegle
     */
    public Boolean getIsRegle() {
        return isRegle;
    }

    /**
     * @param isRegle the isRegle to set
     */
    public void setIsRegle(Boolean isRegle) {
        this.isRegle = isRegle;
    }

    /**
     * @return the dateRegle
     */
    public Date getDateRegle() {
        return dateRegle;
    }

    /**
     * @param dateRegle the dateRegle to set
     */
    public void setDateRegle(Date dateRegle) {
        this.dateRegle = dateRegle;
    }

    /**
     * @return the commentRegle
     */
    public MoyenDePaiement getCommentRegle() {
        return commentRegle;
    }

    /**
     * @param commentRegle the commentRegle to set
     */
    public void setCommentRegle(MoyenDePaiement commentRegle) {
        this.commentRegle = commentRegle;
    }

    /**
     * @return the resteAPayer
     */
    public Double getResteAPayer() {
        return resteAPayer;
    }

    /**
     * @param resteAPayer the resteAPayer to set
     */
    public void setResteAPayer(Double resteAPayer) {
        this.resteAPayer = resteAPayer;
    }

    /**
     * @return the autreMoyenDePaiment
     */
    public String getAutreMoyenDePaiment() {
        return autreMoyenDePaiment;
    }

    /**
     * @param autreMoyenDePaiment the autreMoyenDePaiment to set
     */
    public void setAutreMoyenDePaiment(String autreMoyenDePaiment) {
        this.autreMoyenDePaiment = autreMoyenDePaiment;
    }

    /**
     * @return the lignes
     */
    public List<LigneFacture> getLignes() {
        return lignes;
    }

    /**
     * @param lignes the lignes to set
     */
    public void setLignes(List<LigneFacture> lignes) {
        this.lignes = lignes;
    }

    public String getNoFacture() {
        return this.noFacture;
    }

    /**
     * @param noFacture the noFacture to set
     */
    public void setNoFacture(String noFacture) {
        this.noFacture = noFacture;
    }

    public void genereNoFacture() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        noFacture = "F" + sdf.format(dateDuJour) + number;
    }

    /**
     * pards de la liste pour en faire une string
     */
    public void generateLinesStr() {
        if (lignes == null || lignes.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (LigneFacture lf : lignes) {
            sb.append(lf.getDesignation());
            sb.append(";");
            sb.append(lf.getQuantite());
            sb.append(";");
            sb.append(lf.getPuHt());
            sb.append("#");
        }
        sb = sb.delete(sb.length() - 1, sb.length());
        this.lignesStr = sb.toString();
    }

    /**
     * Par de linesStr pour faire la liste
     */
    public void generateLines() {
        if (lignesStr == null || lignesStr.isEmpty()) {
            return;
        }
        String[] tmps = lignesStr.split("\\#");
        this.lignes = new ArrayList<>(tmps.length);
        LigneFacture lf = null;
        for (String ltmps : tmps) {
            String[] ltmp = ltmps.split("\\;");
            lf = new LigneFacture();
            lf.setNoFacture(noFacture);
            lf.setDesignation(ltmp[0]);
            lf.setQuantite(Double.parseDouble(ltmp[1]));
            lf.setPuHt(Double.parseDouble(ltmp[2]));
            lf.setTva(1d);
            lf.setReference("");

            this.lignes.add(lf);
        }
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    public void setNumber(int no) {
        this.number = no;
    }

    public Double getMontant() {
        Double total = 0d;
        for (LigneFacture lfacture : lignes) {
            total += lfacture.getQuantite() * lfacture.getPuHt();
        }
        return total;
    }

    @Override
    public String toString() {
        return client.toString() + " - " + noFacture + " - " + getMontant() + " - " + isRegle;
    }

    public boolean equals(Facture facture) {
        if (facture == null) {
            return false;
        }

        boolean exactLines = true;
        if (lignes == null && facture.getLignes() == null) {
            exactLines = true;
        } else if (lignes != null && facture.getLignes() == null) {
            exactLines = false;
        } else if (lignes == null && facture.getLignes() != null) {
            exactLines = false;
        } else if (lignes.size() != facture.getLignes().size()) {
            exactLines = false;
        } else {
            Iterator<LigneFacture> itFacture = facture.lignes.iterator();
            Iterator<LigneFacture> itSelf = lignes.iterator();
            boolean[] tab = new boolean[lignes.size()];
            int i = 0;
            while (itFacture.hasNext()) {
                itSelf.hasNext();
                tab[i++] = (itFacture.next().equals(itSelf.next()));
            }
            for (boolean t : tab) {
                if (!t) {
                    exactLines = false;
                    break;
                }
            }
        }

        return (facture.autreMoyenDePaiment.equals(autreMoyenDePaiment)
                && facture.commentRegle.equals(commentRegle)
                && facture.dateDuJour.equals(dateDuJour)
                && facture.dateRegle.equals(dateRegle)
                && facture.idClient.equals(idClient)
                && facture.idModele.equals(idModele)
                && facture.isFinish.equals(isFinish)
                && facture.isRegle.equals(isRegle)
                && facture.noFacture.equals(noFacture)
                && facture.resteAPayer.equals(resteAPayer)
                && exactLines);
    }

    public boolean haveLigne() {
        return lignes != null && !lignes.isEmpty();

    }

}
