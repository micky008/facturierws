package com.msc.facturierws.controller;

import com.msc.rest.tokenrestjersey.Token;
import com.msc.rest.tokenrestjersey.TokenController;
import com.msc.rest.tokenrestjersey.TokenHelper;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author micky
 */
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Resource
public class LoginController extends TokenController {

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Token connectPersonne() {
        Token t = TokenHelper.newToken(1);
        return t;
    }

}
