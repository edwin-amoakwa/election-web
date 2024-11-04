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
public enum SupplierType implements MessageResolvable
{

    SPECIFIC("BANK","Bank"),
    CASH("CASH","Cash");
//    CONTINGENT("CONTINGENT","CONTINGENT", AccountCategory.LIABILITY);
    
    private final String code;
    private final String label;
   
    
    
//    public static AccountType resolve(String classType) 
//    {
//        try {
//            if(classType == null)
//            {
//                return null;
//            }
//            return AccountType.valueOf(classType.toUpperCase());
//
//        } catch (Exception e) {
//        }
//        return null;
//    }
    
    public static SupplierType resolve(String accountType)
    {
        if(accountType == null)
        {
            return null;
        }
        
        for (SupplierType value : SupplierType.values())
        {
            if(value.name().equalsIgnoreCase(accountType)
                    || value.getLabel().equalsIgnoreCase(accountType)
                    || value.getCode().equalsIgnoreCase(accountType))
            {
                return value;
            }
        }
        
        return null;
    }
    
    private SupplierType(String code, String label)
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
