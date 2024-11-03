/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.stately.common.collection.CollectionUtils;
import com.stately.common.data.FormView;
import com.statelyhub.gafpv.entities.BillStatus;
import com.statelyhub.gafpv.entities.Bill;
import com.statelyhub.gafpv.service.CrudService;
import com.statelyhub.gafpv.service.VoucherService;
import com.stately.common.model.LocalDateRange;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.gafpv.entities.PaymentVocher;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.statelyhub.gafpv.reports.ReportManager;
import com.statelyhub.gafpv.service.IdService;
import com.statelyhub.gafpv.service.LedgerService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;

/**
 *
 * @author Edwin
 */
@Named(value = "billController")
@SessionScoped
public class BillController implements Serializable {

    @Inject
    private IdService idService;
    @Inject
    private CrudService crudService;
    @Inject
    private VoucherService voucherService;
    @Inject
    private UserSession userSession;
    @Inject
    private ReportManager reportManager;
    
    @Inject private LedgerService ledgerService;

    private LocalDateRange dateRange = new LocalDateRange();

    private final FormView formView = FormView.listForm();

    private List<Bill> billsList;
    private Bill bill;
    private Bill selectedBill;

    private PaymentVocher paymentVocher;
    private List<PaymentVocher> billVouchersList;

    @PostConstruct
    public void init() {
        billsList = voucherService.getPendingBills(userSession.getAccountUR().getInstitution());
    }
    
    public void printPendingBills()
    {
          reportManager.reportPendingBills(billsList);
    }

    public void calculateAmount() {
        try {
            double vatAmount = 0;
            double nhisAmount = 0;

            bill.setWhtVat(0);

//            if(bill.getVatRate() != 0)
//            {
//                nhisAmount = bill.getInvoiceAmount()
//                    * bill.getVatRate() / 100.0;
//            }
            vatAmount = bill.getInvoiceAmount()
                    * bill.getVatRate() / 100.0;

//            if(bill.getVatRate() == 12.5)
//            {
            nhisAmount = bill.getInvoiceAmount()
                    * bill.getNhisRate() / 100.0;

            System.out.println("initial 5 = " + vatAmount);

//                bill.setNhisRate(bill.get);
            vatAmount = (bill.getInvoiceAmount() + nhisAmount)
                    * bill.getVatRate() / 100.0;

            System.out.println("vat amount : " + vatAmount);

            bill.setWhtVat(7);
//                bill.setWithholdingTaxRate(7.5);
//            }
//            else
//            {
//                
//            }

            System.out.println("initial 5 = " + vatAmount);

            double tourismAmount = 0;
            double withholderTax = 0;

            if (bill.isTourismInvoice()) {
                bill.setTourismLevyRate(1);

                tourismAmount = bill.getInvoiceAmount()
                        * bill.getTourismLevyRate() / 100.0;
            } else {
                bill.setTourismLevyRate(0);
                tourismAmount = 0;
            }

            withholderTax = bill.getInvoiceAmount()
                    * bill.getWithholdingTaxRate() / 100.0;

            if (bill.getVatRate() != 0) {
                double whtVat = vatAmount * bill.getWhtVatRate() / bill.getVatRate();
//            double whtVat = vatAmount * bill.getWhtVatRate() / 100.0;
                System.out.println(" ----- " + whtVat);
                bill.setWhtVat(whtVat);
            } else {

            }

            bill.setWitholdingTaxAmount(withholderTax);
            bill.setTourismLevy(tourismAmount);
            bill.setVatAmount(vatAmount);
            bill.setNhisAmount(nhisAmount);

            double grossAmount = bill.getInvoiceAmount()
                    + bill.getVatAmount()
                    + bill.getTourismLevy()
                    + bill.getNhisAmount()
                    + bill.getOtherCharges();

            bill.setGrossAmount(grossAmount);

            double payableAmount = bill.getGrossAmount()
                    - bill.getWitholdingTaxAmount()
                    - bill.getWhtVat();

            bill.setAmountPayable(payableAmount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePaymentVoucher() {
        
        idService.paymentVoucher(paymentVocher);
        paymentVocher.setValueDate(paymentVocher.getChequeWrittenDate());
        
        crudService.save(paymentVocher);
        billVouchersList = CollectionUtils.checkAdd(billVouchersList, paymentVocher);
        JsfMsg.successSave();

        initPaymentVocher();
    }

    public void viewPaymentVoucher(Bill bill) {
        this.selectedBill = bill;

        billVouchersList = QryBuilder.get(crudService.getEm(), PaymentVocher.class)
                .addObjectParam(PaymentVocher._bill, selectedBill)
                .orderByAsc(PaymentVocher._createdDate)
                .buildQry().getResultList();

        formView.restToDetailView();
        initPaymentVocher();
    }

    public void initPaymentVocher() {
        paymentVocher = new PaymentVocher();
        paymentVocher.setBill(selectedBill);
        paymentVocher.setUnit(selectedBill.getUnit());
        paymentVocher.setPvStatus(BillStatus.PENDING);
        paymentVocher.setValueDate(LocalDate.now());
        
    }

    public void close() {
        bill = null;
        formView.restToListView();
    }

    public void printPaymentVoucher(PaymentVocher voucher) {
        try {
            reportManager.reportPaymentVoucherList(Arrays.asList(voucher));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void editPaymentVoucher(PaymentVocher pv) {
        this.paymentVocher = pv;
    }

    public void deletePaymentVocher(PaymentVocher voucher) {

        try {
            crudService.delete(voucher);
            billVouchersList.remove(voucher);
            JsfMsg.msg(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editBill(Bill bill) {
        this.bill = bill;
        formView.restToCreateView();
    }

    public void deleteBill(Bill bill) {

        try {
            crudService.delete(bill);
            JsfMsg.msg(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAsInvalid(Bill bill) {
        try {
            bill.setBillStatus(BillStatus.INVALID);
            crudService.save(bill);
            
            ledgerService.unpost(bill.getId());

            JsfMsg.msg(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void archiveBill(Bill bill) {
        try {
            bill.setArchived(true);
            crudService.save(bill);

            JsfMsg.msg(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print(Bill paymentVoucher) {
//        reportManager.reportPaymentVoucherList(Arrays.asList(paymentVoucher));
    }

    public void initNewPV() {
        bill = new Bill();
        bill.setUnit(userSession.getAccountUR().getInstitution());
        bill.setBillStatus(BillStatus.PENDING);
        bill.setValueDate(LocalDate.now());
        bill.setPreparedBy(userSession.getAccountUR());
        bill.setId(crudService.generateId());

        formView.restToCreateView();
    }

    public void saveBill() {
        try {
//            if(bill.getValueDate() == null)
//            {
//                bill.setValueDate(LocalDate.now());
//            }

            calculateAmount();

//            if(bill.getBankAccount() != null)
//            {
//                bill.setAccount(bill.getBankAccount().getLedgerAccount());
//            }
            idService.paymentVoucher(bill);
               crudService.save(bill);
            voucherService.evaluateAndSave(bill);
//         

            ledgerService.post(bill);

            billsList = CollectionUtils.checkAdd(billsList, bill);
            
            JsfMsg.successSave();

//            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Bill> getBillsList() {
//        init();
        return billsList;
    }

    public LocalDateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(LocalDateRange dateRange) {
        this.dateRange = dateRange;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Bill getSelectedBill() {
        return selectedBill;
    }

    public void setSelectedBill(Bill selectedBill) {
        this.selectedBill = selectedBill;
    }

    public FormView getFormView() {
        return formView;
    }

    public PaymentVocher getPaymentVocher() {
        return paymentVocher;
    }

    public void setPaymentVocher(PaymentVocher paymentVocher) {
        this.paymentVocher = paymentVocher;
    }

    public List<PaymentVocher> getBillVouchersList() {
        return billVouchersList;
    }

}
