/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.detail;

import java.time.LocalDate;

/**
 *
 * @author Edwin
 */
public class PvDetail
{
    private LocalDate invoiceIssueDate;
    private LocalDate preparedDate;
    
    private String empty = "";
    private String bankAccount = "";
    
    private String supplierName;
    private String supplierAddress;
    
    private String departmentName;
    private String pvId;
    private String fundingSource;
    private String unitName;
    
    private String preparedByTitle;
    private String preparedByName;
    
    private String verifiedByTitle;
    private String verifiedByName;
    
    private String approvedByTitle;
    private String approvedByName;
    
    private String reviewedByTitle;
    private String reviewedByName;
    
    private String lpoNo;
    private LocalDate lpoDate;
    private String sraNo;
    private LocalDate sraDate;
    
    private String invoiceNo;
    private String paymentDetail;
    
    
    private String account;
    private LocalDate chequeIssuedDate;
    private LocalDate chequeWrittenDate;
    private String chequeNo;
    
    private String amountInWords;
    private double amountPayable;
    
    private double vatAmount;
    private double tourismLevyAmount;
    private double witholdingTaxAmount;
    private double grossAmount;
    private double invoiceAmount;
    
    private double vatRate;
    private double witholdingTaxRate;
    private double tourismLevyRate;
    private double whtVat;
    private double otherCharges;
    
    
    private double nhisRate;
    private double nhisAmount;
    
    private boolean approved;
    
    private String voucherCurrency;
    
    private LocalDate authorisedDate;
    
    private LocalDate approvedDate;
    
    
    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getPvId()
    {
        return pvId;
    }

    public void setPvId(String pvId)
    {
        this.pvId = pvId;
    }

    public String getFundingSource()
    {
        return fundingSource;
    }

    public void setFundingSource(String fundingSource)
    {
        this.fundingSource = fundingSource;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getLpoNo()
    {
        return lpoNo;
    }

    public void setLpoNo(String lpoNo)
    {
        this.lpoNo = lpoNo;
    }

    public LocalDate getLpoDate()
    {
        return lpoDate;
    }

    public void setLpoDate(LocalDate lpoDate)
    {
        this.lpoDate = lpoDate;
    }

    public String getSraNo()
    {
        return sraNo;
    }

    public void setSraNo(String sraNo)
    {
        this.sraNo = sraNo;
    }

    public LocalDate getSraDate()
    {
        return sraDate;
    }

    public void setSraDate(LocalDate sraDate)
    {
        this.sraDate = sraDate;
    }

    public String getInvoiceNo()
    {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo)
    {
        this.invoiceNo = invoiceNo;
    }

    public String getPaymentDetail()
    {
        return paymentDetail;
    }

    public void setPaymentDetail(String paymentDetail)
    {
        this.paymentDetail = paymentDetail;
    }

    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public LocalDate getChequeIssuedDate()
    {
        return chequeIssuedDate;
    }

    public void setChequeIssuedDate(LocalDate chequeIssuedDate)
    {
        this.chequeIssuedDate = chequeIssuedDate;
    }

    public String getChequeNo()
    {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo)
    {
        this.chequeNo = chequeNo;
    }

    public String getAmountInWords()
    {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords)
    {
        this.amountInWords = amountInWords;
    }

    public String getPreparedByTitle()
    {
        return preparedByTitle;
    }

    public void setPreparedByTitle(String preparedByTitle)
    {
        this.preparedByTitle = preparedByTitle;
    }

    public String getPreparedByName()
    {
        return preparedByName;
    }

    public void setPreparedByName(String preparedByName)
    {
        this.preparedByName = preparedByName;
    }

    public String getVerifiedByTitle()
    {
        return verifiedByTitle;
    }

    public void setVerifiedByTitle(String verifiedByTitle)
    {
        this.verifiedByTitle = verifiedByTitle;
    }

    public String getVerifiedByName()
    {
        return verifiedByName;
    }

    public void setVerifiedByName(String verifiedByName)
    {
        this.verifiedByName = verifiedByName;
    }

    public String getApprovedByTitle()
    {
        return approvedByTitle;
    }

    public void setApprovedByTitle(String approvedByTitle)
    {
        this.approvedByTitle = approvedByTitle;
    }

    public String getApprovedByName()
    {
        return approvedByName;
    }

    public void setApprovedByName(String approvedByName)
    {
        this.approvedByName = approvedByName;
    }

    public String getReviewedByTitle()
    {
        return reviewedByTitle;
    }

    public void setReviewedByTitle(String reviewedByTitle)
    {
        this.reviewedByTitle = reviewedByTitle;
    }

    public String getReviewedByName()
    {
        return reviewedByName;
    }

    public void setReviewedByName(String reviewedByName)
    {
        this.reviewedByName = reviewedByName;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress()
    {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress)
    {
        this.supplierAddress = supplierAddress;
    }

    public String getEmpty()
    {
        return empty;
    }

    public void setEmpty(String empty)
    {
        this.empty = empty;
    }

    public double getAmountPayable()
    {
        return amountPayable;
    }

    public void setAmountPayable(double amountPayable)
    {
        this.amountPayable = amountPayable;
    }

    public double getVatAmount()
    {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount)
    {
        this.vatAmount = vatAmount;
    }

    public LocalDate getInvoiceIssueDate()
    {
        return invoiceIssueDate;
    }

    public void setInvoiceIssueDate(LocalDate invoiceIssueDate)
    {
        this.invoiceIssueDate = invoiceIssueDate;
    }

    public double getTourismLevyAmount()
    {
        return tourismLevyAmount;
    }

    public void setTourismLevyAmount(double tourismLevyAmount)
    {
        this.tourismLevyAmount = tourismLevyAmount;
    }

    public double getWitholdingTaxAmount()
    {
        return witholdingTaxAmount;
    }

    public void setWitholdingTaxAmount(double witholdingTaxAmount)
    {
        this.witholdingTaxAmount = witholdingTaxAmount;
    }

    public double getGrossAmount()
    {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount)
    {
        this.grossAmount = grossAmount;
    }

    public double getInvoiceAmount()
    {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }

    public double getVatRate()
    {
        return vatRate;
    }

    public void setVatRate(double vatRate)
    {
        this.vatRate = vatRate;
    }

    public double getWitholdingTaxRate()
    {
        return witholdingTaxRate;
    }

    public void setWitholdingTaxRate(double witholdingTaxRate)
    {
        this.witholdingTaxRate = witholdingTaxRate;
    }

    public double getTourismLevyRate()
    {
        return tourismLevyRate;
    }

    public void setTourismLevyRate(double tourismLevyRate)
    {
        this.tourismLevyRate = tourismLevyRate;
    }

    public boolean isApproved()
    {
        return approved;
    }

    public void setApproved(boolean approved)
    {
        this.approved = approved;
    }

    public LocalDate getPreparedDate()
    {
        return preparedDate;
    }

    public void setPreparedDate(LocalDate preparedDate)
    {
        this.preparedDate = preparedDate;
    }

    public double getWhtVat()
    {
        return whtVat;
    }

    public void setWhtVat(double whtVat)
    {
        this.whtVat = whtVat;
    }

    public String getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    public double getOtherCharges()
    {
        return otherCharges;
    }

    public void setOtherCharges(double otherCharges)
    {
        this.otherCharges = otherCharges;
    }

    public double getNhisRate()
    {
        return nhisRate;
    }

    public void setNhisRate(double nhisRate)
    {
        this.nhisRate = nhisRate;
    }

    public double getNhisAmount()
    {
        return nhisAmount;
    }

    public void setNhisAmount(double nhisAmount)
    {
        this.nhisAmount = nhisAmount;
    }

    public LocalDate getChequeWrittenDate()
    {
        return chequeWrittenDate;
    }

    public void setChequeWrittenDate(LocalDate chequeWrittenDate)
    {
        this.chequeWrittenDate = chequeWrittenDate;
    }

    public String getVoucherCurrency()
    {
        return voucherCurrency;
    }

    public void setVoucherCurrency(String voucherCurrency)
    {
        this.voucherCurrency = voucherCurrency;
    }

    public LocalDate getAuthorisedDate()
    {
        return authorisedDate;
    }

    public void setAuthorisedDate(LocalDate authorisedDate)
    {
        this.authorisedDate = authorisedDate;
    }

    public LocalDate getApprovedDate()
    {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate)
    {
        this.approvedDate = approvedDate;
    }
    
    
    
}
