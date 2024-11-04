/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.statelyhub.old.constants;

import com.stately.common.api.MessageResolvable;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum Role implements MessageResolvable
{    
    GUEST("RN","guest"),
    MEMBER("CL","member"),
    ADMIN("CL","admin");
    
    private final String code;
    private final String label;
    
//    public static final List<Status> accountTypes = new LinkedList<>();
//    static{
//        accountTypes.add(SAVINGS);
//        accountTypes.add(SUSU);
//    }
    
    private Role(String code, String label)
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
