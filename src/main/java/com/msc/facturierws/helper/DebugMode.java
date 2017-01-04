package com.msc.facturierws.helper;

import com.msc.dao.daoproject.generic.DAO;
import com.msc.rest.tokenrestjersey.TokenFilter;
import com.msc.rest.tokenrestjersey.TokenService;

/**
 *
 * @author Micky
 */
public class DebugMode {

    public static void setDebugMode(boolean debug) {
        DAO.setDebugMode(debug);
        TokenService.DEBUG_MODE = debug;
        TokenFilter.debugMode = debug;
    }

}
