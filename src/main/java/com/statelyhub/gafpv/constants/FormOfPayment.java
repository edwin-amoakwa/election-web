/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.statelyhub.gafpv.constants;

import com.stately.common.api.MessageResolvable;

/**
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum FormOfPayment implements MessageResolvable
{

    CASH("CASH","CASH"),
    MTN_MOBILE_MONEY("MTN_MOBILE_MONEY","MTN_MOBILE_MONEY"),
    TIGO_CASH("TIGO_CASH","TIGO_CASH"),
    VODAFON_CASH("VODAFON_CASH","VODAFON_CASH"),
    AIRTEL_MONEY("AIRTEL_MONEY","AIRTEL_MONEY"),
    BANK_TRANSFER("BANK_TRANSFER","BANK_TRANSFER");

    private final String code;
    private final String label;
    
    private FormOfPayment(String code, String label)
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
