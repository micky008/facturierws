package com.msc.facturierws.dao.specif;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.dao.daoproject.generic.GenericDaoImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author micky
 */
public class DAOSpecif extends DAO {

    private static Map<Class, Object> instances = new HashMap<>();

    public synchronized static Object getInstance(Class<? extends GenericDaoImpl<?>> clazz, SecurityContext sc) {
        try {
            if (instances.containsKey(clazz)) {
                ((DAOSpecifGeneric) instances.get(clazz)).setSecurityContext(sc);
                return instances.get(clazz);
            }
            instances.put(clazz, clazz.newInstance());
            ((DAOSpecifGeneric) instances.get(clazz)).setSecurityContext(sc);
            return instances.get(clazz);

        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
