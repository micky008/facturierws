package com.msc.facturierws.dao;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.facturierws.dao.specif.DAOSpecifGeneric;
import com.msc.facturierws.entity.Client;
import java.sql.SQLException;

/**
 *
 * @author Micky
 */
public class ClientDao extends DAOSpecifGeneric<Client> {

    public ClientDao() {
        super(DAO.getConnection());
    }

    public Client getClientByNoFacture(String noFacture) throws SQLException {
        this.secureList.add(noFacture);
        return this.securePreparedSelectOnce("where id_facture = ? ", null);
    }

}
