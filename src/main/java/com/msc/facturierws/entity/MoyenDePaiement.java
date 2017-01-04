package com.msc.facturierws.entity;

//
import java.io.Serializable;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "moyen_de_paiement")
@XmlEnum
@Produces(MediaType.APPLICATION_JSON)
public enum MoyenDePaiement implements Serializable {
    @XmlEnumValue(value = "Espece")
    ESPECE,
    @XmlEnumValue(value = "Cheque")
    CHEQUE,
    @XmlEnumValue(value = "Carte")
    CARTE,
    @XmlEnumValue(value = "Virement")
    VIREMENT,
    @XmlEnumValue(value = "Autre")
    AUTRE
}
