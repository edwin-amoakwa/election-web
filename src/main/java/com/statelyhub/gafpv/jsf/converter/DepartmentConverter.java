/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf.converter;

;
import com.statelyhub.gafpv.entities.Department;
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
@FacesConverter(forClass=Department.class)
public class DepartmentConverter extends SelectItemsConverter
{
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
         try
        {
            Department entity = (Department) value;
            
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
