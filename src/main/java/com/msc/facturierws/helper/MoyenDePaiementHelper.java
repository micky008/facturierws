package com.msc.facturierws.helper;

import com.msc.facturierws.entity.MoyenDePaiement;
import com.msc.rest.tokenrestjersey.TokenEntity;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Micky
 */
@XmlRootElement(name = "type")
@Produces(MediaType.APPLICATION_JSON)
public class MoyenDePaiementHelper extends TokenEntity {

    private MoyenDePaiement type;

    public MoyenDePaiementHelper() {
    }

    public MoyenDePaiementHelper(MoyenDePaiement t) {
        this.type = t;
    }

    /**
     * @return the type
     */
    public MoyenDePaiement getMoyenDePaiement() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setMoyenDePaiement(MoyenDePaiement type) {
        this.type = type;
    }

}
