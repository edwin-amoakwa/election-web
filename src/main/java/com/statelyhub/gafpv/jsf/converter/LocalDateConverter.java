/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.calendar.Calendar;

/**
 *
 * @author Edwin
 */
@Named
@ApplicationScoped
@FacesConverter(forClass = LocalDate.class)
public class LocalDateConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(extractPattern(component, context));
        try
        {
            return LocalDate.parse(value, formatter);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null || (value instanceof String && StringUtils.isBlank((String) value)))
        {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(extractPattern(component, context));
        return formatter.format((LocalDate) value);
    }

    private String extractPattern(UIComponent component, FacesContext context)
    {
        // try to get infos from calendar component
        if (component instanceof Calendar)
        {
            Calendar calendarComponent = (Calendar) component;
//            System.out.println(".... pattern : " + calendarComponent.getPattern());
            return calendarComponent.getPattern();
        }

        return "dd/MM/yyyy";
//        return null;
    }
}
