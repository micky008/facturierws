package com.msc.facturierws.dao;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.facturierws.dao.specif.DAOSpecif;
import com.msc.facturierws.dao.specif.DAOSpecifGeneric;
import com.msc.facturierws.entity.Facture;
import com.msc.facturierws.entity.LigneFacture;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ws.rs.PathParam;

/**
 *
 * @author micky
 */
public class FactureDao extends DAOSpecifGeneric<Facture> {

    public FactureDao() {
        super(DAO.getConnection());
    }

    public Facture getFactureById(Integer id) throws SQLException {
        FactureLigneDAO lfdao = (FactureLigneDAO) DAOSpecif.getInstance(FactureLigneDAO.class, null);
        Facture f = super.getObjectById(id);
        f.setLignes(lfdao.getLigneByFacture(id));
        return f;
    }

    public List<Facture> getFactureBySearch(String noFacture, Date date, Integer idClient) throws SQLException {
        List<Facture> factures = null;
        if (noFacture != null) {
            secureList.add(noFacture);
            factures = securePreparedSelectMulti("where no_facture = ?", null);
        } else if (date != null) {
            factures = preparedSelectMulti("where date_du_jour=" + toDate(date));
        } else if (idClient != null && idClient > 1) {
            factures = preparedSelectMulti("where id_client=" + idClient);
        }
        return factures;
    }

    public Integer getLastCount(Date date) throws SQLException {
        String sql = "select count(*) from " + getTableName() + " where date_du_jour >= " + toDate(date) + " and date_du_jour <= " + toDate(date);
        ResultSet rs = this.sendSql(sql);
        rs.next();
        int res = rs.getInt(1);
        rs.close();
        return res;
    }

    @Override
    public void insert(Facture f) throws SQLException {
        if (f.getLignes() != null && !f.getLignes().isEmpty()) {
            FactureLigneDAO lfdao = (FactureLigneDAO) DAOSpecif.getInstance(FactureLigneDAO.class, null);
            lfdao.insert(f.getLignes());
        }
        super.insert(f);
    }

    @Override
    public void update(Facture f) throws SQLException {
        if (f.getLignes() != null && !f.getLignes().isEmpty()) {
            FactureLigneDAO lfdao = (FactureLigneDAO) DAOSpecif.getInstance(FactureLigneDAO.class, null);
            lfdao.update(f.getLignes());
        }
        super.update(f);
    }
}
