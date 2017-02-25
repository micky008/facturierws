<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		<style type="text/css">
				.tdal{
					text-align: left;
				}

				.tdac{
					text-align: center;
				}

				.tdar{
					text-align: right;
				}

				.thbd{
					border-bottom: solid 1px black;
				}

				.td2br{
					border-left: 1px solid black;
					border-right: 1px solid black;
				}

				.nom{
					text-align: left;
					text-decoration: underline; 
					font-style: italic ;
				}

				.bd{
					border : 1px solid black;
				}

				.mauto{
					margin: auto;
				}

				th h2{
					font-weight: bolder;
				}
				tr{
					height: 20px;
				}
	</style>
    </head>
    <body>
        <table>
            <!--entete entreprise et client-->
            <tr>
                <td class="bd ">
                    <table>
                        <tr><td><b>${moi.nom} ${moi.prenom}</b></td></tr>
                        <tr><td>Siren : ${moi.siren}</td></tr>
                        <tr><td>${moi.adresse}<br/>${moi.codePostal} ${moi.ville}</td></tr>
                        <tr><td>${moi.telephone}</td></tr>
                    </table>
                </td>
                <td class="bd">
                    <table class="mauto">
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
            <tr><td class="tdar"  colspan="2">Date : ${facture.dateDuJour?date}</td></tr>
            <tr><td class="tdar"  colspan="2"><b>Facture no: ${facture.getNoFacture()}</b></td></tr>
            <tr><td colspan="2">&nbsp;</td></tr>
            <tr><td colspan="2">&nbsp;</td></tr>
            <tr>
                <td colspan="2">
                    <table cellspacing="0">
                        <tr class="tdac">
                            <th class="thbd" >Type de formation</th><th class="thbd">Temps (en heure)</th><th class="thbd">Prix/heure HT</th><th class="thbd">Total HT</th></tr>
                        <#assign total = 0>
                        <#list facture.lignes as ligne>
                        <tr class="tdac">
                            <td>${ligne.designation}</td><td>${ligne.quantite}</td><td>${ligne.puHt}</td><td class="td2br">${(ligne.quantite * ligne.puHt)?string["####.##"]}</td>                            
                            <#assign total += (ligne.quantite * ligne.puHt)>
                        </tr>
                        <tr><td colspan="3">&nbsp;</td><td class="td2br">&nbsp;</td></tr>
                        </#list>
                        <tr class="tdac"><td colspan="2">&nbsp;</td><td ><h2>TOTAL</h2></td><td class="bd">${total?string["####.##"]} Euros</td> </tr>
                        <tr><td colspan="4">&nbsp;</td></tr>
                        <tr><td colspan="4">&nbsp;</td></tr>
                        <#list moi.getMentionLegalesList() as ml>
                        <tr><td  class="tdal" colspan="4">${ml}</td> </tr>
                        </#list>
                    </table>
                </td>
            </tr>
            <tr><td colspan="2">&nbsp;</td></tr>
            <tr><td colspan="2">&nbsp;</td></tr>
            <tr><td class="nom"  >Coordonnées bancaires</td> </tr>
            <tr><td class="tdal" >${moi.titulairecompte}</td> </tr>
            <tr><td class="tdal" >${moi.iban}</td> </tr>
            <tr><td class="tdal" >${moi.bic}</td> </tr>
        </table>
    </body>
</html>
