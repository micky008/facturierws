/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msc.facturierws.dao.specif;

import com.msc.dao.daoproject.generic.GenericDaoImpl;
import com.msc.facturierws.entity.MoyenDePaiement;
import com.msc.rest.tokenrestjersey.Token;
import com.msc.rest.tokenrestjersey.TokenHelper;
import com.msc.rest.tokenrestjersey.helper.ListHelper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.SecurityContext;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 *
 * @author micky
 * @param <T>
 */
public abstract class DAOSpecifGeneric<T> extends GenericDaoImpl<T> {

    protected SecurityContext sc;

    public DAOSpecifGeneric(Connection con) {
        super(con);
    }

    public void setSecurityContext(SecurityContext sc) {
        this.sc = sc;
    }

    @Override
    protected Field[] getFields() {
        Field[] allFields = super.getFields();
        Field[] allFieldsClean = new Field[allFields.length - 1];
        int i = 0;
        for (Field f : allFields) {
            if (f.getType() == Token.class) {
                continue;
            }
            allFieldsClean[i++] = f;
        }
        return allFieldsClean;
    }

    @Override
    public T fillObject(ResultSet rs) throws SQLException {
        T obj = super.fillObject(rs);

        if (obj != null && sc != null) {
            Method m = MethodUtils.getAccessibleMethod(clazz, "setToken", Token.class);
            try {
                m.invoke(obj, TokenHelper.newToken(sc));
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(GenericDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(DAOSpecifGeneric.class.getName()).log(Level.SEVERE, null, ex);
            }
            return obj;
        }
        return null;
    }

    protected ListHelper<T> fillObjectsWithToken(ResultSet rs) throws SQLException {
        List<T> objs = super.fillObjects(rs);
        ListHelper lh = new ListHelper();
        lh.setList(objs);
        lh.setToken(TokenHelper.newToken(sc));
        return lh;
    }

    @Override
    protected Object convertFillObjectCustom(Class<?> clazz, Object res) {
        if (clazz == MoyenDePaiement.class) {
            String type = (String) res;
            return MoyenDePaiement.valueOf(type);
        }
        return null;
    }

    @Override
    protected String convertLogicCustom(Class<?> type, Object o) {
        if (type == MoyenDePaiement.class) {
            return "'"+((MoyenDePaiement) o).toString()+"'";
        }
        return null;
    }
}
