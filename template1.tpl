<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <table style="width: 50%;">
            <!--entete entreprise et client-->
            <tr>
                <td style="border: 1px solid black; width: 50%">
                    <table>
                        <tr><td><b>${moi.nom} ${moi.prenom}</b></td></tr>
                        <tr><td>Siren : ${moi.siren}</td></tr>
                        <tr><td>${moi.adresse}<br/>${moi.codePostal} ${moi.ville}</td></tr>
                        <tr><td>${moi.telephone}</td></tr>
                    </table>
                </td>
                <td style="border: 1px solid black; width: 50%;">
                    <table style="margin:auto;">
                        <#if client.getIsEntreprise() == true>
                        <tr><td><b>${client.raisonSociale} ${client.formeJuridique}</b></td></tr>
                        <tr><td>Siren : ${client.siren}</td></tr>
                        <#else>
                        <tr><td><b>${client.nom} ${client.prenom}</b></td></tr>
                        </#if>                        
                        <tr><td>${client.adresse}<br/>${client.codePostal} ${client.ville}</td></tr>
                    </table>
                </td>
            </tr>
            <tr><td style="text-align: right" colspan="2">Date : ${facture.dateDuJour?date}</td></tr>
            <tr><td style="text-align: right" colspan="2"><b>Facture no: ${facture.getNoFacture()}</b></td></tr>
            <tr><td colspan="2">&nbsp;</td></tr>
            <tr><td colspan="2">&nbsp;</td></tr>
            <tr>
                <td colspan="2">
                    <table style="width: 100%; text-align: center;" cellspacing="0">
                        <tr >
                            <th style="border-bottom: solid 1px black; ">Type de formation</th><th style="border-bottom: solid 1px black">Temps (en heure)</th><th style="border-bottom: solid 1px black">Prix/heure HT</th><th style="border-bottom: solid 1px black">Total HT</th></tr>
                        <#assign total = 0>
                        <#list facture.lignes as ligne>
                        <tr style="text-align: center">
                            <td>${ligne.designation}</td><td>${ligne.quantite}</td><td>${ligne.puHt}</td><td style="border-left: 1px solid black; border-right: 1px solid black;">${ligne.quantite * ligne.puHt}</td>                            
                            <#assign total += (ligne.quantite * ligne.puHt)>
                        </tr>
                        <tr><td colspan="3">&nbsp;</td><td style="border-left: 1px solid black; border-right: 1px solid black;">&nbsp;</td></tr>
                        </#list>
                        <tr><td colspan="2">&nbsp;</td><td style="text-align: center"><h2>TOTAL</h2></td><td style="border: 1px solid black; text-align: center">${total} Euros</td> </tr>
                        <tr><td colspan="4">&nbsp;</td></tr>
                        <tr><td colspan="4">&nbsp;</td></tr>
                        <#list moi.getMentionLegalesList() as ml>
                        <tr><td style="text-align: left;" colspan="4">${ml}</td> </tr>
                        </#list>
                    </table>
                </td>
            </tr>
            <tr><td colspan="2">&nbsp;</td></tr>
            <tr><td colspan="2">&nbsp;</td></tr>
            <tr><td style="text-align: left; text-decoration: underline; font-style: italic " >Coordonnées bancaires</td> </tr>
            <tr><td style="text-align: left;" >${moi.titulairecompte}</td> </tr>
            <tr><td style="text-align: left;" >${moi.iban}</td> </tr>
            <tr><td style="text-align: left;" >${moi.bic}</td> </tr>
        </table>
    </body>
</html>
