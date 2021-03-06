package com.msc.facturierws.entity;

import com.msc.dao.daoproject.annotation.Id;
import com.msc.dao.daoproject.annotation.Name;
import com.msc.rest.tokenrestjersey.TokenEntity;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author micky
 */
@Name(name = "ligneFacture")
@XmlRootElement(name = "LigneFacture")
public class LigneFacture extends TokenEntity {

    @Id
    private String noFacture;

    private String designation;
    private String reference;
    private Double quantite;
    private Double puHt;
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
    public Double getPuHt() {
        return puHt;
    }

    /**
     * @param puHT the puHT to set
     */
    public void setPuHt(Double puHT) {
        this.puHt = puHT;
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
    public String getNoFacture() {
        return noFacture;
    }

    /**
     * @param idFacture the idFacture to set
     */
    public void setNoFacture(String idFacture) {
        this.noFacture = idFacture;
    }

      @Override
    public boolean equals(Object f) {
        if (!(f instanceof LigneFacture) || f == null){
            return false;
        }
        LigneFacture lf = (LigneFacture)f;
        return (lf.designation.equals(designation) &&
                lf.noFacture.equals(noFacture) &&
                lf.puHt.equals(puHt) &&
                lf.quantite.equals(quantite) &&
                lf.reference.equals(reference) &&
                lf.tva.equals(tva));
                
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.noFacture);
        hash = 53 * hash + Objects.hashCode(this.designation);
        hash = 53 * hash + Objects.hashCode(this.reference);
        hash = 53 * hash + Objects.hashCode(this.quantite);
        hash = 53 * hash + Objects.hashCode(this.puHt);
        hash = 53 * hash + Objects.hashCode(this.tva);
        return hash;
    }

}
