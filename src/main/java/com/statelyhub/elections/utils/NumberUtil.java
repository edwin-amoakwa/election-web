/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.utils;

import com.stately.common.formating.NumberFormattingUtils;

/**
 *
 * @author edwin
 */
public class NumberUtil {
    
    public static double pct(double a, double b)
    {
        
        if(b == 0)
        {
            return 0;
        }
        
//        double pct = (a / b) * 100;
        double pct = (a / b) * 1.00;
    
        return NumberFormattingUtils.formatDoubleNum(pct,4);
        
    }
}
