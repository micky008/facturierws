package com.msc.facturierws.entity;

//
import com.msc.dao.daoproject.annotation.Id;
import com.msc.dao.daoproject.annotation.Name;
import com.msc.rest.tokenrestjersey.TokenEntity;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Client.java
//  @ Date : 13/11/2016
//  @ Author : 
//
//
@Name(name = "clients")
@XmlRootElement(name = "Client")
@Produces(MediaType.APPLICATION_JSON)
public class Client extends TokenEntity {

    @Id
    private Integer id;

    @FormParam("nom")
    private String nom;
    @FormParam("prenom")
    private String prenom;
    @FormParam("raisonSociale")
    private String raisonSociale;
    @FormParam("adresse")
    private String adresse;
    @FormParam("codePostal")
    private String codePostal;
    @FormParam("ville")
    private String ville;
    @FormParam("email")
    private String email;
    @FormParam("telephone")
    private String telephone;
    @FormParam("tva")
    private String tva;
    @FormParam("isEntreprise")
    private Boolean isEntreprise;
    @FormParam("formeJuridique")
    private String formeJuridique;
    @FormParam("siren")
    private String siren;

    /**
     * @return the tva
     */
    public String getTva() {
        return tva;
    }

    /**
     * @param tva the tva to set
     */
    public void setTva(String tva) {
        this.tva = tva;
    }

    /**
     * @return the isEntreprise
     */
    public Boolean getIsEntreprise() {
        return isEntreprise;
    }

    /**
     * @param isEntreprise the isEntreprise to set
     */
    public void setIsEntreprise(Boolean isEntreprise) {
        this.isEntreprise = isEntreprise;
    }

    /**
     * @return the idClient
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param idClient the idClient to set
     */
    public void setId(Integer idClient) {
        this.id = idClient;
    }

    /**
     * @return the formeJuridique
     */
    public String getFormeJuridique() {
        return formeJuridique;
    }

    /**
     * @param formeJuridique the formeJuridique to set
     */
    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    /**
     * @return the siren
     */
    public String getSiren() {
        return siren;
    }

    /**
     * @param siren the siren to set
     */
    public void setSiren(String siren) {
        this.siren = siren;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.setId((Integer) id);
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the raisonSociale
     */
    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**
     * @param raisonSociale the raisonSociale to set
     */
    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @param codePostal the codePostal to set
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * @return the ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * @param ville the ville to set
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {

        return isEntreprise ? raisonSociale + " " + formeJuridique : nom + " " + prenom;

    }

}
