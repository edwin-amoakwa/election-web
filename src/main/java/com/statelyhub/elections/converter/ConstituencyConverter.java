/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.converter;

;
import com.statelyhub.elections.entities.Constituency;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author Edwin
 */

@Named
@FacesConverter(forClass=Constituency.class)
public class ConstituencyConverter extends SelectItemsConverter
{
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
         try
        {
            Constituency entity = (Constituency) value;
            
            if(entity != null)
            {
                if(component.getClientId().contains("name"))
                {
                    return entity.toString();
                }
                else
                {
                    return entity.getId();
                }
            }
        } catch (Exception e)
        {
           
        }
        
        return null;
    }
}
