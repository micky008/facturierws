package com.msc.facturierws.dao;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.facturierws.dao.specif.DAOSpecifGeneric;
import com.msc.facturierws.entity.MonEntreprise;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author micky
 */
public class MonEntrepriseDAO extends DAOSpecifGeneric<MonEntreprise> {

    public MonEntrepriseDAO() {
        super(DAO.getConnection());
    }

    public Boolean isExiste() throws SQLException {
        String sql = "select count(*) from " + getTableName();
        ResultSet r = this.sendSql(sql);
        r.next();
        int res = r.getInt(1);
        r.close();
        return new Boolean(res > 0);
    }

}
