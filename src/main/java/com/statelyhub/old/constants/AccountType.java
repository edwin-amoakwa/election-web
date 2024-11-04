/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.statelyhub.old.constants;

import com.stately.common.api.MessageResolvable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum AccountType implements MessageResolvable
{

    BANK("BANK","Bank", AccountCategory.ASSETS),
    CASH("CASH","Cash", AccountCategory.ASSETS),
    PETTY_CASH("Petty Cash","Petty Cash", AccountCategory.ASSETS),
    CHEQUE("Cheque","Cheque", AccountCategory.ASSETS),
    INVESTMENT("INVESTMENT","Investments", AccountCategory.ASSETS),
//    STOCK("CASH","Stock", AccountCategory.ASSETS),    
    CURRENT_ASSET("CASH","Current Asset", AccountCategory.ASSETS),
    FIXED_ASSET("FIXED_ASSET","Fixed Assets", AccountCategory.ASSETS),
    INVENTORY_ASSET("INVENTORY_ASSET","Inventory Asset",AccountCategory.ASSETS),
    OTHER_ASSET("Other","Other Asset", AccountCategory.ASSETS),
    RECEIVABLE("RECEIVABLE","Accounts Receivable", AccountCategory.ASSETS),
        
    INCOME("INCOME","Income", AccountCategory.REVENUE),
    OTHER_INCOME("INCOME","Other Income", AccountCategory.REVENUE),
    
    CAPITAL("CAPITAL","Capital", AccountCategory.EQUITY),
    EQUITY("EQUITY","Equity", AccountCategory.EQUITY),
    
    EXPENSE("EXPENSE","Expense", AccountCategory.EXPENSE),
    OTHER_EXPENSE("EXPENSE","Other Expense", AccountCategory.EXPENSE),
    COST_OF_GOODS_SOLD("COST_OF_GOODS_SOLD","Cost of Goods Sold", AccountCategory.EXPENSE),
        
    CURRENT_LIABILITY("LIABILITY","Current Liability", AccountCategory.LIABILITY),
    LONG_TERM_LIABILITY("LIABILITY","Long Term Liability", AccountCategory.LIABILITY),
    OTHER_LIABILITY("LIABILITY","Other Liabilities", AccountCategory.LIABILITY),
    PAYABLE("PAYABLE","Accounts Payable", AccountCategory.LIABILITY);
//    CONTINGENT("CONTINGENT","CONTINGENT", AccountCategory.LIABILITY);
    
    private final String code;
    private final String label;
    private final AccountCategory accountCategory;
    
    public static final List<AccountType> bankCash = Arrays.asList(BANK, CASH);
    public static final List<AccountType> bankAndCashEquivlent = Arrays.asList(BANK, CASH, PETTY_CASH, CHEQUE);
    
    public static final List<AccountType> allAccountsList = new LinkedList<>();
    
    public static final List<AccountType> receivablesAndLiabilities = new LinkedList<>();
    
    public static final List<AccountType> billable = Arrays.asList(EXPENSE,
            OTHER_EXPENSE,OTHER_LIABILITY,CURRENT_ASSET,OTHER_ASSET,FIXED_ASSET,INVENTORY_ASSET,
            CURRENT_LIABILITY,LONG_TERM_LIABILITY, OTHER_LIABILITY, COST_OF_GOODS_SOLD);
    
    public static final List<AccountType> income = Arrays.asList(INCOME,
            OTHER_INCOME);
    
    public static final List<AccountType> liabilities =  Arrays.asList(CURRENT_LIABILITY,
                    LONG_TERM_LIABILITY, OTHER_LIABILITY,PAYABLE);
    
    public static final List<AccountType> depositables = new LinkedList<>();
    
    public static final List<AccountType> transferables =  new LinkedList<>();
    
    public static final List<AccountType> receivables = Arrays.asList(RECEIVABLE);
    
    static{
        allAccountsList.addAll(Arrays.asList(AccountType.values()));
        allAccountsList.remove(PAYABLE);
        allAccountsList.remove(RECEIVABLE);
//        allAccountsList.remove(CONTINGENT);
        
        receivablesAndLiabilities.addAll(receivables);
        receivablesAndLiabilities.addAll(liabilities);
        
//        transferables.addAll(bankCash);
        transferables.addAll(getByCategory(AccountCategory.ASSETS));
        
        depositables.addAll( Arrays.asList(CURRENT_LIABILITY,
                    LONG_TERM_LIABILITY, OTHER_LIABILITY));
        depositables.addAll(getByCategory(AccountCategory.EQUITY));
    }
    
    public static List<AccountType> getByCategory(AccountCategory category)
    {
        return Arrays.asList(AccountType.values())
                .stream()
                .filter((AccountType t) -> t.getAccountCategory() == category)
                .collect(Collectors.toList());
    }
    
    public static List<AccountType> getIncomeAccounts()
    {
        return Arrays.asList(AccountType.values())
                .stream()
                .filter(AccountType::isIncome).collect(Collectors.toList());
    }
    
    public static List<AccountType> getExpenseAccountsLit()
    {
        return Arrays.asList(AccountType.values())
                .stream()
                .filter(AccountType::isExpense).collect(Collectors.toList());
    }
    
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
    
    public static AccountType resolve(String accountType)
    {
        if(accountType == null)
        {
            return null;
        }
        
        for (AccountType value : AccountType.values())
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
    
    private AccountType(String code, String label, AccountCategory accountCategory)
    {
        this.code = code;
        this.label = label;
        this.accountCategory = accountCategory;
    }

    public AccountCategory getAccountCategory()
    {
        return accountCategory;
    }
    
    public boolean isIncome()
    {
        return accountCategory == AccountCategory.REVENUE;
    }
    
    public boolean isExpense()
    {
        return accountCategory == AccountCategory.EXPENSE;
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
