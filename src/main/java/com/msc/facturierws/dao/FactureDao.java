package com.msc.facturierws.dao;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.facturierws.dao.specif.DAOSpecif;
import com.msc.facturierws.dao.specif.DAOSpecifGeneric;
import com.msc.facturierws.entity.Facture;
import com.msc.facturierws.entity.LigneFacture;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author micky
 */
public class FactureDao extends DAOSpecifGeneric<Facture> {

    public FactureDao() {
        super(DAO.getConnection());
    }

    public Facture getFactureByNoFacture(String noFacture) throws SQLException {
        FactureLigneDAO lfdao = (FactureLigneDAO) DAOSpecif.getInstance(FactureLigneDAO.class, null);
        Facture f = getObjectById(noFacture);
        f.setLignes(lfdao.getLigneByFacture(f.getNoFacture()));
        return f;
    }

    public List<Facture> getFactureBySearch(String noFacture, Date date, Integer idClient) throws SQLException {
        List<Facture> factures = null;
        if (noFacture != null) {
            secureList.add(noFacture);
            factures = securePreparedSelectMulti("where no_facture = ?", null);
        } else if (date != null) {
            factures = preparedSelectMulti("where date_du_jour=" + toDate(date));
        } else if (idClient != null && idClient >= 1) {
            factures = preparedSelectMulti("where id_client=" + idClient);
        }
        if (factures == null) {
            return null;
        }
        FactureLigneDAO lfdao = (FactureLigneDAO) DAOSpecif.getInstance(FactureLigneDAO.class, null);
        for (Facture f : factures) {
            f.setLignes(lfdao.getLigneByFacture(f.getNoFacture()));
            f.generateLinesStr();
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

    public void fermeFacture(String noFacture) throws SQLException {
        Facture oldFacture = getObjectById(noFacture);
        oldFacture.setIsFinish(true);
        super.update(oldFacture);
    }

    @Override
    public void update(Facture f) throws SQLException {
        Facture oldFacture = getFactureByNoFacture(f.getNoFacture());
        if (f.equals(oldFacture)) {
            return;
        }
        if (oldFacture.getIsFinish()) {
            return;
        }
        //todo delete ligne manquante et add si en plus.
        FactureLigneDAO lfdao = (FactureLigneDAO) DAOSpecif.getInstance(FactureLigneDAO.class, null);
        if (f.haveLigne() && oldFacture.haveLigne()) {
            List<LigneFacture> listLignef = null;
            if (f.getLignes().size() > oldFacture.getLignes().size()) {
                listLignef = getDifLinesToInsert(f.getLignes(), oldFacture.getLignes());
                lfdao.insert(listLignef);
            } else if (f.getLignes().size() < oldFacture.getLignes().size()) {
                listLignef = getDifLinesToDelete(f.getLignes(), oldFacture.getLignes());
                lfdao.delete(listLignef);
            } else {
                lfdao.update(f.getLignes());
            }
        }
        super.update(f);
    }

    //la new contien plus de ligne que old
    private List<LigneFacture> getDifLinesToInsert(List<LigneFacture> newFacture, List<LigneFacture> oldFacture) {
        List<LigneFacture> res = new ArrayList<>();
        int i = 0;
        for (LigneFacture lf : newFacture) {
            try {
                if (!lf.equals(oldFacture.get(i++))) {
                    res.add(lf);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                res.add(lf);
            }
        }
        return res;
    }

    //old contien + de ligne que new
    private List<LigneFacture> getDifLinesToDelete(List<LigneFacture> newFacture, List<LigneFacture> oldFacture) {
        List<LigneFacture> res = new ArrayList<>();
        int i = 0;
        for (LigneFacture lf : oldFacture) {
            try {
                if (!lf.equals(newFacture.get(i++))) {
                    res.add(lf);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                res.add(lf);
            }
        }
        return res;
    }

}
