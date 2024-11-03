/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.statelyhub.gafpv.entities.*;
import com.statelyhub.gafpv.constants.FormOfPayment;
import com.stately.modules.jpa2.UniqueEntityModel3;
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
@Table(name = "elections")
public class Election extends UniqueEntityModel3 {

    public static final String _pvId = "billNo";
    @Column(name = "bill_no")
    private String billNo;

    public static final String _department = "department";
    @JoinColumn(name = "department")
    @ManyToOne
    private Department department;

    public static final String _supplier = "supplier";
    @JoinColumn(name = "supplier")
    @ManyToOne
    private Supplier supplier;

    public static final String _supplierName = "supplierName";
    @Column(name = "supplier_name")
    private String supplierName;

    public static final String _supplierAddress = "supplierAddress";
    @Column(name = "supplier_address")
    private String supplierAddress;

    public static final String _invoiceNo = "invoiceNo";
    @Column(name = "invoice_no")
    private String invoiceNo;

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

    public static final String _amount = "amount";
    @Column(name = "invoice_amount")
    private double invoiceAmount;

    public static final String _nhisRate = "nhisRate";
    @Column(name = "nhis_rate")
    private double nhisRate;

    public static final String _nhisAmount = "nhisAmount";
    @Column(name = "nhis_amount")
    private double nhisAmount;

    public static final String _vatAmount = "vatAmount";
    @Column(name = "vat_amount")
    private double vatAmount;

    @Column(name = "withholding_tax_rate")
    private double withholdingTaxRate;

    @Column(name = "wht_vat")
    private double whtVat;

    @Column(name = "other_charges")
    private double otherCharges;

    @Column(name = "wht_vat_rate")
    private double whtVatRate;

    @Column(name = "tourism_levy")
    private double tourismLevy;

    @Column(name = "amount_payable")
    private double amountPayable;

    public static final String _amountPaid = "amountPaid";
    @Column(name = "amount_paid")
    private double amountPaid;

    public static final String _amountOutstanding = "amountOutstanding";
    @Column(name = "amount_outstanding")
    private double amountOutstanding;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "lpo_no")
    private String lpoNo;

    @Column(name = "lpo_date")
    private LocalDate lpoDate;

    @Column(name = "sra_no")
    private String sraNo;

    @Column(name = "sra_date")
    private LocalDate sraDate;

    public static final String _withholdingTax = "withholdingTax";
    @Column(name = "withholding_tax_amount")
    private double witholdingTaxAmount;

    public static final String _billStatus = "billStatus";
    @Column(name = "bill_status")
    @Enumerated(EnumType.STRING)
    private BillStatus billStatus;

    public static final String _paymentStatus = "paymentStatus";
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;

    public static final String _formOfPayment = "formOfPayment";
    @Column(name = "form_of_payment")
    @Enumerated(EnumType.STRING)
    private FormOfPayment formOfPayment;

    public static final String _paymentDetail = "paymentDetail";
    @Column(name = "payment_detail")
    private String paymentDetail;

    public static final String _expenseAccount = "expenseAccount";
    @JoinColumn(name = "expense_account")
    @ManyToOne
    private LedgerAccount expenseAccount;

    @Column(name = "vat_rate")
    private double vatRate;

    @Column(name = "tourism_levy_rate")
    private double tourismLevyRate = 0;

    @Column(name = "gross_amount")
    private double grossAmount = 0;

    @Column(name = "tourism_invoice")
    private boolean tourismInvoice;

    public static final String _archived = "archived";
    @Column(name = "archived")
    private boolean archived;

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

    public boolean isApproved() {
        return billStatus == BillStatus.APPROVED;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public FundingSource getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(FundingSource fundingSource) {
        this.fundingSource = fundingSource;
    }

    public UserAccount getPreparedBy() {
        return preparedBy;
    }

    public void setPreparedBy(UserAccount preparedBy) {
        this.preparedBy = preparedBy;
    }

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public void setFormOfPayment(FormOfPayment formOfPayment) {
        this.formOfPayment = formOfPayment;
    }

    public String getPaymentDetail() {
        return paymentDetail;
    }

    public void setPaymentDetail(String paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public double getWitholdingTaxAmount() {
        return witholdingTaxAmount;
    }

    public void setWitholdingTaxAmount(double witholdingTaxAmount) {
        this.witholdingTaxAmount = witholdingTaxAmount;
    }

    public BillStatus getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(BillStatus billStatus) {
        this.billStatus = billStatus;
    }

    public UserAccount getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(UserAccount verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public UserAccount getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(UserAccount reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getWithholdingTaxRate() {
        return withholdingTaxRate;
    }

    public void setWithholdingTaxRate(double withholdingTaxRate) {
        this.withholdingTaxRate = withholdingTaxRate;
    }

    public double getTourismLevy() {
        return tourismLevy;
    }

    public void setTourismLevy(double tourismLevy) {
        this.tourismLevy = tourismLevy;
    }

    public double getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(double amountPayable) {
        this.amountPayable = amountPayable;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getLpoNo() {
        return lpoNo;
    }

    public void setLpoNo(String lpoNo) {
        this.lpoNo = lpoNo;
    }

    public LocalDate getLpoDate() {
        return lpoDate;
    }

    public void setLpoDate(LocalDate lpoDate) {
        this.lpoDate = lpoDate;
    }

    public String getSraNo() {
        return sraNo;
    }

    public void setSraNo(String sraNo) {
        this.sraNo = sraNo;
    }

    public LocalDate getSraDate() {
        return sraDate;
    }

    public void setSraDate(LocalDate sraDate) {
        this.sraDate = sraDate;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public UserAccount getGivenOutBy() {
        return givenOutBy;
    }

    public void setGivenOutBy(UserAccount givenOutBy) {
        this.givenOutBy = givenOutBy;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

//    public double getTourismLevyAmount()
//    {
//        return tourismLevyAmount;
//    }
//
//    public void setTourismLevyAmount(double tourismLevyAmount)
//    {
//        this.tourismLevyAmount = tourismLevyAmount;
//    }
    public double getTourismLevyRate() {
        return tourismLevyRate;
    }

    public void setTourismLevyRate(double tourismLevyRate) {
        this.tourismLevyRate = tourismLevyRate;
    }

    public boolean isTourismInvoice() {
        return tourismInvoice;
    }

    public void setTourismInvoice(boolean tourismInvoice) {
        this.tourismInvoice = tourismInvoice;
    }

    public UserAccount getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(UserAccount approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public Institution getUnit() {
        return unit;
    }

    public void setUnit(Institution unit) {
        this.unit = unit;
    }

    public double getWhtVat() {
        return whtVat;
    }

    public void setWhtVat(double whtVat) {
        this.whtVat = whtVat;
    }

    public double getWhtVatRate() {
        return whtVatRate;
    }

    public void setWhtVatRate(double whtVatRate) {
        this.whtVatRate = whtVatRate;
    }

    public LocalDate getPreparedDate() {
        return preparedDate;
    }

    public void setPreparedDate(LocalDate preparedDate) {
        this.preparedDate = preparedDate;
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public double getNhisRate() {
        return nhisRate;
    }

    public void setNhisRate(double nhisRate) {
        this.nhisRate = nhisRate;
    }

    public double getNhisAmount() {
        return nhisAmount;
    }

    public void setNhisAmount(double nhisAmount) {
        this.nhisAmount = nhisAmount;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public ReceivedStatus getReceivedStatus() {
        return receivedStatus;
    }

    public void setReceivedStatus(ReceivedStatus receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    public LocalDate getChequeWrittenDate() {
        return chequeWrittenDate;
    }

    public void setChequeWrittenDate(LocalDate chequeWrittenDate) {
        this.chequeWrittenDate = chequeWrittenDate;
    }

    public String getVoucherCurrency() {
        return voucherCurrency;
    }

    public void setVoucherCurrency(String voucherCurrency) {
        this.voucherCurrency = voucherCurrency;
    }

    public LocalDate getAuthorisedDate() {
        return authorisedDate;
    }

    public void setAuthorisedDate(LocalDate authorisedDate) {
        this.authorisedDate = authorisedDate;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return paymentDetail;
    }

    public LedgerAccount getExpenseAccount() {
        return expenseAccount;
    }

    public void setExpenseAccount(LedgerAccount expenseAccount) {
        this.expenseAccount = expenseAccount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getAmountOutstanding() {
        return amountOutstanding;
    }

    public void setAmountOutstanding(double amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    
}
