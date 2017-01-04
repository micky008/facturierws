package com.msc.facturierws.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.msc.rest.tokenrestjersey.helper.Helper;
import com.msc.rest.tokenrestjersey.helper.ListHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 *
 * @author micky
 */
public class GsonJerseyCustomMessageBody implements MessageBodyWriter<Helper> {

    private Gson gson;

    //Customize the gson behavior here
    private Gson getGson() {
        if (gson == null) {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.create();
        }
        return gson;
    }

    @Override
    public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3) {
        return arg0 == Helper.class || arg0 == ListHelper.class;
    }

    @Override
    public long getSize(Helper arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
        return -1;
    }

    @Override
    public void writeTo(Helper arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4, MultivaluedMap<String, Object> arg5, OutputStream arg6) throws IOException, WebApplicationException {

        String res = getGson().toJson(arg0, arg2);
        arg6.write(res.getBytes());

    }

}
