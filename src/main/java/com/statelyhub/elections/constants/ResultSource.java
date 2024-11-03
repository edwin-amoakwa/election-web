/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.statelyhub.elections.constants;

import com.stately.common.api.MessageResolvable;

/**
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum ResultSource implements MessageResolvable
{

    COALLATED("COALLATED","COALLATED"),
    OFFICIAL("OFFICIAL","OFFICIAL");

    private final String code;
    private final String label;
    
    private ResultSource(String code, String label)
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
