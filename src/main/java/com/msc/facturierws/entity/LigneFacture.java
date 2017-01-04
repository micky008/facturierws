package com.msc.facturierws.entity;

import com.msc.dao.daoproject.annotation.Id;
import com.msc.dao.daoproject.annotation.Name;
import com.msc.rest.tokenrestjersey.TokenEntity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author micky
 */
@Name(name = "ligneFacture")
@XmlRootElement(name = "LigneFacture")
public class LigneFacture extends TokenEntity {

    @Id
    private Integer id;

    private String idFacture;

    private String designation;
    private String reference;
    private Double quantite;
    private Double puHT;
    private Double tva;

    /**
     * @return the designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * @param designation the designation to set
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the quantite
     */
    public Double getQuantite() {
        return quantite;
    }

    /**
     * @param quantite the quantite to set
     */
    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    /**
     * @return the puHT
     */
    public Double getPuHT() {
        return puHT;
    }

    /**
     * @param puHT the puHT to set
     */
    public void setPuHT(Double puHT) {
        this.puHT = puHT;
    }

    /**
     * @return the tva
     */
    public Double getTva() {
        return tva;
    }

    /**
     * @param tva the tva to set
     */
    public void setTva(Double tva) {
        this.tva = tva;
    }

    /**
     * @return the idFacture
     */
    public String getIdFacture() {
        return idFacture;
    }

    /**
     * @param idFacture the idFacture to set
     */
    public void setIdFacture(String idFacture) {
        this.idFacture = idFacture;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
