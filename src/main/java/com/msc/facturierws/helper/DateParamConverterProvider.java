package com.msc.facturierws.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

@Provider
public class DateParamConverterProvider implements ParamConverterProvider {

    private static final String format = "yyyyMMdd";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(format);

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType,
            Type genericType,
            Annotation[] annotations) {

        if (rawType != Date.class) {
            return null;
        }

        return (ParamConverter<T>) new ParamConverter<Date>() {
            @Override
            public Date fromString(String value) {
                try {
                    if (value == null || value.isEmpty()){
                        return null;
                    }
                    return formatter.parse(value);
                } catch (Exception ex) {
                    throw new WebApplicationException("Bad formatted date", 400);
                }
            }

            @Override
            public String toString(Date date) {
                return new SimpleDateFormat(format).format(date);
            }
        };
    }
}
