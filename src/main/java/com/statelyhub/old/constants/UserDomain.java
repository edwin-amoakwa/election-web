/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.statelyhub.old.constants;

import com.stately.common.api.MessageResolvable;

/**
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum UserDomain implements MessageResolvable
{

    POLLING_STATION("Admin","Admin"),
    ELECTORIAL_AREA("Viewer","Viewer"),
    ADMINISTRATIVE_AREA("Viewer","Viewer"),
    CONSTITUENCY("Inputter","Inputter"),
    REGION("Authoriser","Authoriser");

    private final String code;
    private final String label;
    
    private UserDomain(String code, String label)
    {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode()
    {
        return code;
    }

    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return label;
    }
    
    
    
    

}
