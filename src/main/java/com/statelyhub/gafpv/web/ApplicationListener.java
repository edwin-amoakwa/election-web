/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.web;


import com.statelyhub.gafpv.jsf.UserSession;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.inject.Inject;
import org.omnifaces.util.Faces;

/**
 *
 * @author Edwin
 */
public class ApplicationListener implements Serializable, PhaseListener 
{
    @Inject private UserSession userSession;

//    private final UserSession userSession = BeanManagerLookup.lookup(UserSession.class);
    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();

        String viewId = facesContext.getViewRoot().getViewId();

        if (userSession != null) 
        {
            if (viewId.contains("admin")) 
            {
                if (!userSession.isUserLogin()) {
                    try {
                        Faces.redirect(Pages.index);
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }
        }
        
        
        

    }

    @Override
    public void beforePhase(PhaseEvent event) {
//      System.out.println("going to : " + FacesContext.getCurrentInstance().getViewRoot().getViewId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
