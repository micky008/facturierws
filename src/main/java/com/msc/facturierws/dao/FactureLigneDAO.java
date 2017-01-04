package com.msc.facturierws.dao;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.facturierws.dao.specif.DAOSpecifGeneric;
import com.msc.facturierws.entity.LigneFacture;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author micky
 */
class FactureLigneDAO extends DAOSpecifGeneric<LigneFacture> {

    public FactureLigneDAO() {
        super(DAO.getConnection());
    }

    public List<LigneFacture> getLigneByFacture(Integer idFacture) throws SQLException {
        return preparedSelectMulti("where id_facture=" + idFacture);
    }

}
