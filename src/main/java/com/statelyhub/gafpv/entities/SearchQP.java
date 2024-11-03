/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

import com.stately.common.model.LocalDateRange;
import com.statelyhub.gafpv.constants.FormOfPayment;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author Edwin
 */

public class SearchQP// extends DateRecord3
{    
    
    private LocalDateRange dateRange = new LocalDateRange();
    
       private Supplier supplier;
       
    private String paymentNo;
    private String invoiceNo;
    private String billNo;
    private Department department;
    private String SupplierName;
     private BillStatus billStatus;
    
    
    public static final String _fundingSource = "fundingSource";
    @JoinColumn(name = "funding_source")
    @ManyToOne
    private FundingSource fundingSource;
    
    public static final String _createdBy = "createdBy";
    @JoinColumn(name = "prepared_by")
    @ManyToOne
    private UserAccount preparedBy;
    
    public static final String _verifiedBy = "verifiedBy";
    @JoinColumn(name = "verified_by")
    @ManyToOne
    private UserAccount verifiedBy;
    
    public static final String _reviewedBy = "reviewedBy";
    @JoinColumn(name = "reviewed_by")
    @ManyToOne
    private UserAccount reviewedBy;
    
    public static final String _approvedBy = "approvedBy";
    @JoinColumn(name = "approved_by")
    @ManyToOne
    private UserAccount approvedBy;
    
    public static final String _paymentAmount = "paymentAmount";
    @Column(name = "payment_amount")
    private double paymentAmount;
    
    @Column(name = "cheque_issued_date")
    private LocalDate chequeIssuedDate;
        
    public static final String _chequeNo = "chequeNo";
    @Column(name = "cheque_no")
    private String chequeNo;
        
    
    
    public static final String _pvStatus = "pvStatus";
    @Column(name = "pv_status")
    @Enumerated(EnumType.STRING)
   
    
    public static final String _formOfPayment = "formOfPayment";
    @Column(name = "form_of_payment")
    @Enumerated(EnumType.STRING)
    private FormOfPayment formOfPayment;

    public static final String _paymentDetail = "paymentDetail";
    @Column(name = "payment_detail")
    private String paymentDetail;
    
        
    public static final String _bankAccount = "bankAccount";
    @JoinColumn(name = "bank_account")
    @ManyToOne
    private BankAccount bankAccount;
    
        
    public static final String _unit = "unit";
    @JoinColumn(name = "unit")
    @ManyToOne
    private Institution unit;
    
    public static final String _preparedDate = "preparedDate";
    @Column(name = "prepared_date")
    private LocalDate preparedDate;
        
    public static final String _authorisedDate = "authorisedDate";
    @Column(name = "authorised_date")
    private LocalDate authorisedDate;
        
    public static final String _approvedDate = "approvedDate";
    @Column(name = "approved_date")
    private LocalDate approvedDate;
    
    public static final String _receivedStatus = "receivedStatus";
    @Column(name = "received_status")
    @Enumerated(EnumType.STRING)
    private ReceivedStatus receivedStatus = ReceivedStatus.PENDING;
    
    @Column(name = "received_by")
    private String receivedBy;
           
    public static final String _givenOutBy = "givenOutBy";
    @JoinColumn(name = "given_out_by")
    @ManyToOne
    private UserAccount givenOutBy;    
    
    @Column(name = "received_date")
    private LocalDate receivedDate;
        
    @Column(name = "cheque_written_date")
    private LocalDate chequeWrittenDate;
    
    
    public static final String _voucherCurrency = "voucherCurrency";
    @Column(name = "voucher_currency")
    private String voucherCurrency;
    
    
    
    

    

    public FundingSource getFundingSource()
    {
        return fundingSource;
    }

    public void setFundingSource(FundingSource fundingSource)
    {
        this.fundingSource = fundingSource;
    }

    public UserAccount getPreparedBy()
    {
        return preparedBy;
    }

    public void setPreparedBy(UserAccount preparedBy)
    {
        this.preparedBy = preparedBy;
    }

    
    public FormOfPayment getFormOfPayment()
    {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment)
    {
        this.formOfPayment = formOfPayment;
    }

    public String getPaymentDetail()
    {
        return paymentDetail;
    }

    public void setPaymentDetail(String paymentDetail)
    {
        this.paymentDetail = paymentDetail;
    }

    
    

    public UserAccount getVerifiedBy()
    {
        return verifiedBy;
    }

    public void setVerifiedBy(UserAccount verifiedBy)
    {
        this.verifiedBy = verifiedBy;
    }

    public UserAccount getReviewedBy()
    {
        return reviewedBy;
    }

    public void setReviewedBy(UserAccount reviewedBy)
    {
        this.reviewedBy = reviewedBy;
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

    
    

    public String getReceivedBy()
    {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy)
    {
        this.receivedBy = receivedBy;
    }

    public UserAccount getGivenOutBy()
    {
        return givenOutBy;
    }

    public void setGivenOutBy(UserAccount givenOutBy)
    {
        this.givenOutBy = givenOutBy;
    }

    public LocalDate getReceivedDate()
    {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate)
    {
        this.receivedDate = receivedDate;
    }

    


    public UserAccount getApprovedBy()
    {
        return approvedBy;
    }

    public void setApprovedBy(UserAccount approvedBy)
    {
        this.approvedBy = approvedBy;
    }

   
    

    public Institution getUnit()
    {
        return unit;
    }

    public void setUnit(Institution unit)
    {
        this.unit = unit;
    }


    

    public LocalDate getPreparedDate()
    {
        return preparedDate;
    }

    public void setPreparedDate(LocalDate preparedDate)
    {
        this.preparedDate = preparedDate;
    }

   
    
    public BankAccount getBankAccount()
    {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    
    public ReceivedStatus getReceivedStatus()
    {
        return receivedStatus;
    }

    public void setReceivedStatus(ReceivedStatus receivedStatus)
    {
        this.receivedStatus = receivedStatus;
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

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }
    

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(LocalDateRange dateRange) {
        this.dateRange = dateRange;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String SupplierName) {
        this.SupplierName = SupplierName;
    }

    public BillStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(BillStatus billStatus) {
        this.billStatus = billStatus;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

   
    
    
}
