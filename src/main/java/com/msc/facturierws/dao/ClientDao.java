package com.msc.facturierws.dao;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.facturierws.dao.specif.DAOSpecifGeneric;
import com.msc.facturierws.entity.Client;

/**
 *
 * @author Micky
 */
public class ClientDao extends DAOSpecifGeneric<Client> {
    
    public ClientDao() {
        super(DAO.getConnection());
    }


}
