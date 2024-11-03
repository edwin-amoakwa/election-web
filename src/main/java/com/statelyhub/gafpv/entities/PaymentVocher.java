/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.entities;

import com.stately.common.constants.DebitCredit;
import com.statelyhub.gafpv.constants.FormOfPayment;
import com.stately.modules.jpa2.DateRecord3;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "payment_voucher")
public class PaymentVocher extends DateRecord3
{    
    public static final String _paymentNo = "paymentNo";
    @Column(name = "payment_no")
    private String paymentNo;
    
    public static final String _bill = "bill";
    public static final String _bill_voucherCurrency = _bill + "." + Bill._voucherCurrency;
    public static final String _bill_department = _bill + "." + Bill._department;
    public static final String _bill_supplierName = _bill + "." + Bill._supplierName;
    public static final String _bill_invoiceNo = _bill + "." + Bill._invoiceNo;
    @JoinColumn(name = "bill")
    @ManyToOne
    private Bill bill;
    
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
        
    @Column(name = "amount_in_words")
    private String amountInWords;
    
//    public static final String _withholdingTax = "withholdingTax";
//    @Column(name = "withholding_tax_amount")
//    private double witholdingTaxAmount;
    
    public static final String _pvStatus = "pvStatus";
    @Column(name = "pv_status")
    @Enumerated(EnumType.STRING)
    private BillStatus pvStatus;
    
    public static final String _formOfPayment = "formOfPayment";
    @Column(name = "form_of_payment")
    @Enumerated(EnumType.STRING)
    private FormOfPayment formOfPayment;

//    public static final String _paymentDetail = "paymentDetail";
//    @Column(name = "payment_detail")
//    private String paymentDetail;
    
        
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
    
    
//    public static final String _voucherCurrency = "voucherCurrency";
//    @Column(name = "voucher_currency")
//    private String voucherCurrency;
//    
    
    
    public boolean isApproved()
    {
        return pvStatus == BillStatus.APPROVED;
    }
    
   
    

    

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

    

    public BillStatus getPvStatus()
    {
        return pvStatus;
    }

    public void setPvStatus(BillStatus pvStatus)
    {
        this.pvStatus = pvStatus;
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

    public String getAmountInWords()
    {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords)
    {
        this.amountInWords = amountInWords;
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

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString()
    {
        return bill + "";
    }
    
    
        public GeneralLedger getExpense()
    {
        GeneralLedger ledger = new GeneralLedger();
        ledger.setBatch(this.getId());
        ledger.setAmount(paymentAmount);
        ledger.setUnit(getUnit());
        ledger.setCredit(paymentAmount);
        ledger.setDebitCredit(DebitCredit.CREDIT);
        ledger.setAccount(getBankAccount().getLedgerAccount());
        ledger.setValueDate(getValueDate());
        ledger.setNarration("Payment: " + bill.getPaymentDetail());
//        ledger.set
        return ledger;
    }
    
    public GeneralLedger getPayable()
    {
        GeneralLedger ledger = new GeneralLedger();
        ledger.setBatch(this.getId());
        ledger.setAmount(paymentAmount);
        ledger.setUnit(getUnit());
        ledger.setDebit(-paymentAmount);
        ledger.setDebitCredit(DebitCredit.DEBIT);
        
        ledger.setValueDate(getValueDate());
        ledger.setNarration("Payment: " + bill.getPaymentDetail());
        return ledger;
    }
    
    
}
