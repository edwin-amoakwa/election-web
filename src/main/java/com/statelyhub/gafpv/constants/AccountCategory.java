/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.statelyhub.gafpv.constants;

import com.stately.common.api.MessageResolvable;
import com.stately.common.constants.DebitCredit;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum AccountCategory implements MessageResolvable
{

    ASSETS("ASSET","Assets", 1,DebitCredit.DEBIT,DebitCredit.CREDIT),
    LIABILITY("LIABILITY","Liability",2,DebitCredit.CREDIT,DebitCredit.DEBIT),
    EQUITY("EQUITY","Equity",3,DebitCredit.CREDIT,DebitCredit.DEBIT),
    REVENUE("REVENUE","Revenue",4,DebitCredit.CREDIT,DebitCredit.DEBIT),
    EXPENSE("EXPENSE","Expense",5,DebitCredit.DEBIT,DebitCredit.CREDIT);
    
    

    private final String code;
    private final String label;
    private final int viewOrder;
    private final DebitCredit increaseBy;
    private final DebitCredit decreaseBy;
    
    private AccountCategory(String code, String label, int viewOrder,DebitCredit increaseBy,DebitCredit decreaseBy)
    {
        this.code = code;
        this.label = label;
        this.viewOrder = viewOrder;
        this.increaseBy = increaseBy;
        this.decreaseBy = decreaseBy;
    }
    
    public static AccountCategory resolve(String classType) {
        try {
            return AccountCategory.valueOf(classType.toUpperCase());

        } catch (Exception e) {
        }
        return null;
    }
    

    public String getCode()
    {
        return code;
    }

    public String getLabel()
    {
        return label;
    }

    public int getViewOrder()
    {
        return viewOrder;
    }

    public DebitCredit getIncreaseBy() {
        return increaseBy;
    }

    public DebitCredit getDecreaseBy() {
        return decreaseBy;
    }

    @Override
    public String toString()
    {
        return label;
    }
}
