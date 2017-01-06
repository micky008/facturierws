package com.msc.facturierws.dao;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.facturierws.dao.specif.DAOSpecif;
import com.msc.facturierws.dao.specif.DAOSpecifGeneric;
import com.msc.facturierws.entity.Client;
import java.sql.ResultSet;
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
        FactureDao fdao = (FactureDao) DAOSpecif.getInstance(FactureDao.class, this.sc);
        String sql = "SELECT c.* from " + getTableName() + " c, " + fdao.getTableName() + " f where f.id_client=c.id and f.no_facture='"+ noFacture+"'" ;
        ResultSet rs = sendSql(sql);
        Client res = fillObject(rs);
        rs.close();
        return res;
    }

}
