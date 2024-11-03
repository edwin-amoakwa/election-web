/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.reports;


import com.stately.common.formating.NumberFormattingUtils;
import com.stately.common.formating.NumberToWords;
import com.statelyhub.gafpv.detail.PvDetail;
import com.statelyhub.gafpv.entities.Bill;
import com.statelyhub.gafpv.service.CrudService;
import com.stately.modules.jasperreporting.JasperReportManager;
import com.stately.modules.jasperreporting.ReportDesignFileType;
import com.stately.modules.jasperreporting.ReportOutputEnvironment;
import com.stately.modules.jasperreporting.ReportOutputFileType;
import com.statelyhub.gafpv.entities.Institution;
import com.statelyhub.gafpv.constants.AmtInWords;
import com.statelyhub.gafpv.constants.AuthorisationRequirement;
import com.statelyhub.gafpv.detail.BillDetail;
import com.statelyhub.gafpv.entities.PaymentVocher;
import com.statelyhub.gafpv.jsf.UserSession;

import java.awt.Image;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@RequestScoped
public class ReportManager extends JasperReportManager implements Serializable
{

    @Inject private CrudService crudService;
    @Inject private UserSession userSession;

    private static final Logger LOGGER = Logger.getLogger(ReportManager.class.getName());

    private static final ReportManager reportManager = new ReportManager();

    public ReportManager()
    {

//        init();
    }

//    public static ReportManager instance()
//    {
//        reportManager.init();
//        return reportManager;
//    }

//    public void addMap(Map reportParameters)
//    {
//        for (Object key : reportParameters.keySet())
//        {
//            addParam(key.toString(), reportParameters.get(key));
//        }
//    }

    @PostConstruct
    private void init()
    {

        setReportEnvironment(ReportOutputEnvironment.WEB_APPLICATION);
        setReportFileType(ReportDesignFileType.INPUTSTREAM);
        setReportOutput(ReportOutputFileType.PDF);

        try
        {

            Image institutionLogo = null;
            try
            {

//                icaghLogo = ImageResource.getResourceImage(EpvImages.image);
                institutionLogo = ImageIO.read(ReportManager.class.getResource(EpvImages.image));

            } catch (Exception e)
            {
//                e.printStackTrace();
            }

            String contactDetails
                    = "P.O. Box 4268, Accra-Ghana "
                    + "Tel:: 0544336701, 0277801422  "
                    + "Fax: 669594 "
                    + "E-mail: info@icagh.com, icaghana@gmail.com";

//            System.out.println("adding image ... " + icaghLogo);
            addParam("institutionLogo", institutionLogo);
            addParam("contactDetails", contactDetails);
            
            addParam("institutionName",  "Ghana Armed Forces");
            addParam("unitName", userSession.getInstitution() + "");
            
            
           
            
//              addParam("reportHeaderLandscape", (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream(ReportFiles.report_header_landscape)));
//            addParam("reportHeaderPortrait", (JasperReport) JRLoader.loadObject(this.getClass().getResourceAsStream(ReportFiles.report_header_portrait)));
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        try
            {
                 addParam("approveByLabel", userSession.getInstitution().getApproveByLabel() + "");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    
    

    public void reportPaymentVoucherList(List<PaymentVocher> paymentVouchersList)
    {
        try
        {
            

            List<PvDetail> detailsList = convertPvList(paymentVouchersList);
            
            addParam("reportTitle", "Payment Voucher" );
            showReport(detailsList, ReportFiles.payment_voucher);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error reporting summary registration", e);
        }
    }
    
    
    public void reportPendingBills(List<Bill> paymentVouchersList)
    {
        try
        {
            

            List<BillDetail> detailsList = convertBillsList(paymentVouchersList);
            
            addParam("reportTitle", "Pending Bills" );
            showReport(detailsList, ReportFiles.bills_list);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error reporting summary registration", e);
        }
    }


    

    public void reportList(List<PaymentVocher> paymentVouchersList)
    {
        try
        {
            
            

            List<PvDetail> detailsList = convertPvList(paymentVouchersList);
            
            
            addParam("reportTitle", "Payment Voucher" );
            showReport(detailsList, ReportFiles.vouchesr_list);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error reporting summary registration", e);
        }
    }
    

    public void reportBankList(List<PaymentVocher> paymentVouchersList)
    {
        try
        {
            
            

            List<PvDetail> detailsList = convertPvList(paymentVouchersList);
            System.out.println(detailsList);
            for (PvDetail pvDetail : detailsList)
            {
                System.out.println(pvDetail.getSupplierName());
            }
            
            addParam("reportTitle", "Bank List" );
            showReport(detailsList, ReportFiles.vouchers_bank_list);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error reporting summary registration", e);
        }
    }

    public void printWitholdingTax(List<PaymentVocher> paymentVouchersList)
    {
        try
        {
            
            
            double amount = paymentVouchersList.stream()
                    .mapToDouble(PaymentVocher::getPaymentAmount)
                    .sum();

            List<PvDetail> detailsList = convertPvList(paymentVouchersList);
            
            String msg = "Please find attached a GCB cheque number 630970"
                    + " for the sum of "
                    + NumberToWords.getInstance().convertToWords(amount)
                    +"<b> (GHS "+NumberFormattingUtils.getFormatedAmount(amount) +" </b> "
                    + "being witholding tax dedeuction for the mont of December 2020";
            
            
            String msg2 = "The tax deduction is in respect of Goods supplied to "
                    + "the Ghana Armed Forces by the under-mentioned suppliers";
            
            String letterDetails = "<ol> "
                    + "<li>"+ msg +"</li>"
                    + "<li>"+ msg2 +"</li>"
                    + "</ol>";
            
            letterDetails = "<html>"+letterDetails+"</html>";
            
            addParam("amountDetails", msg);
            addParam("otherDetails", msg2);
//            addParam("letterDetails", letterDetails);
//            addParam("reportTitle", "Witholding Tax" );
            showReport(detailsList, ReportFiles.witholding_tax_report);
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error reporting summary registration", e);
        }
    }

    public List<PvDetail> convertPvList(List<PaymentVocher> paymentVouchersList)
    {
        List<PvDetail> detailsList = new LinkedList<>();
        try
        {
            if(paymentVouchersList == null)
            {
                return Collections.EMPTY_LIST;
            }
            
            for (PaymentVocher voucher : paymentVouchersList)
            {
                
                Bill bill =voucher.getBill();
                
                PvDetail detail = new PvDetail();
                
                if (voucher.getUnit() != null)
                {
                    if (voucher.getUnit().getAuthorisationRequirement() == AuthorisationRequirement.NOT_REQUIRED)
                    {
                        detail.setApproved(true);
                    } else
                    {
                        detail.setApproved(voucher.isApproved());
                    }
                } else
                {
                     detail.setApproved(true);
                }
                
                
                
                
//                
                
                if(voucher.getUnit() != null)
                {
                    detail.setUnitName(voucher.getUnit().getInstitutionName());
                }
                
//                detail.setInstitutionName("GHS-Disease Control Unit");

//                detail.setInvoiceIssueDate(LocalDate.now());
//                detail.setInvoiceIssueDate(LocalDate.now());
                
                if(voucher.getFundingSource() != null)
                {
                    detail.setFundingSource(voucher.getFundingSource().getSourceName());
                }
                
                if(voucher.getBankAccount()!= null)
                {
                    String bankDetail = voucher.getBankAccount().getBankName() 
                            + " - " + voucher.getBankAccount().getAccountNo();
                
//                    detail.setBankAccount(pv.getBankAccount().getAccountNo());
                    
                    detail.setBankAccount(bankDetail);
                }
                
//                if(pv.getSupplier() != null)
//                {
//                    detail.setSupplierName(pv.getSupplier().getSupplierName());
//                    detail.setSupplierAddress(pv.getSupplier().getSupplierAddress());
//                }
                    detail.setSupplierName(voucher.getBill().getSupplierName());
                    detail.setSupplierAddress(voucher.getBill().getSupplierAddress());
                
                if(voucher.getBill().getDepartment()!= null)
                {
                    detail.setDepartmentName(voucher.getBill().getDepartment().getDepartmentName());
                }
                
//                if(voucher.getAccount()!= null)
//                {
//                    String account = 
//                            voucher.getAccount().getAccountNo()
//                            +" - " + pv.getAccount().getAccountName();
                
                detail.setPvId(voucher.getBill().getBillNo());
                detail.setPreparedDate(voucher.getPreparedDate());
                detail.setLpoNo(voucher.getBill().getLpoNo());
                detail.setSraNo(voucher.getBill().getSraNo());
                detail.setInvoiceNo(voucher.getBill().getInvoiceNo());
                
                detail.setChequeWrittenDate(voucher.getChequeWrittenDate());
                
                detail.setLpoDate(bill.getLpoDate());
                detail.setSraDate(bill.getSraDate());
                
                detail.setChequeNo(voucher.getChequeNo());
                detail.setChequeIssuedDate(voucher.getChequeIssuedDate());
                detail.setPaymentDetail(voucher.getBill().getPaymentDetail());
                detail.setVoucherCurrency(voucher.getBill().getVoucherCurrency());
                
                detail.setAuthorisedDate(voucher.getAuthorisedDate());
                detail.setApprovedDate(voucher.getApprovedDate());
                                
                if(voucher.getPreparedBy() != null)
                {
                    detail.setPreparedByName(voucher.getPreparedBy().getAccountName());
                }
                
                if(voucher.getVerifiedBy()!= null)
                {
                    detail.setVerifiedByName(voucher.getVerifiedBy().getAccountName());
                }
                
                if(voucher.getReviewedBy()!= null)
                {
                    detail.setReviewedByName(voucher.getReviewedBy().getAccountName());
                }
                
                if(voucher.getApprovedBy()!= null)
                {
                    detail.setApprovedByName(voucher.getApprovedBy().getAccountName());
                }
                
                detail.setVatRate(bill.getVatRate());
                detail.setTourismLevyRate(bill.getTourismLevyRate());
                detail.setWitholdingTaxRate(bill.getWithholdingTaxRate());
                detail.setTourismLevyAmount(bill.getTourismLevy());
                detail.setWhtVat(bill.getWhtVat());
                
                detail.setGrossAmount(bill.getGrossAmount());

                detail.setInvoiceAmount(bill.getInvoiceAmount());
                detail.setVatAmount(bill.getVatAmount());
                detail.setWitholdingTaxAmount(bill.getWitholdingTaxAmount());
                detail.setOtherCharges(bill.getOtherCharges());
                detail.setAmountPayable(bill.getAmountPayable());
                
                detail.setNhisRate(bill.getNhisRate());
                detail.setNhisAmount(bill.getNhisAmount());
                                
                double amountToConvert = detail.getAmountPayable();
                
                AmtInWords amtInWords = Optional.ofNullable(voucher.getUnit())
                        .map(Institution::getAmtInWords).orElse(AmtInWords.NET_PAYMENT);
                
                if(amtInWords == AmtInWords.GROSS_AMOUNT)
                {
                    amountToConvert = bill.getGrossAmount();
                }
                else if(amtInWords == AmtInWords.INVOICE_AMOUNT)
                {
                    amountToConvert = bill.getInvoiceAmount();
                }
                
                String amtToWords = NumberToWords.getInstance()
                        .convertToWords(amountToConvert);
                                
                detail.setAmountInWords(amtToWords);
                
                detailsList.add(detail);
                
            }
            
            
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error reporting summary registration", e);
        }
        
        return detailsList;
    }

    
    
 public List<BillDetail> convertBillsList(List<Bill> paymentVouchersList)
    {
        List<BillDetail> detailsList = new LinkedList<>();
        try
        {
            if(paymentVouchersList == null)
            {
                return Collections.EMPTY_LIST;
            }
            
            for (Bill bill : paymentVouchersList)
            {
                
//                Bill bill =voucher.getBill();
                
                BillDetail detail = new BillDetail();
                
                detail.setValueDate(bill.getValueDate());
                
                if (bill.getUnit() != null)
                {
                    if (bill.getUnit().getAuthorisationRequirement() == AuthorisationRequirement.NOT_REQUIRED)
                    {
                        detail.setApproved(true);
                    } else
                    {
                        detail.setApproved(bill.isApproved());
                    }
                } else
                {
                     detail.setApproved(true);
                }
                
                
                detail.setAmountPaid(bill.getAmountPaid());
                detail.setAmountOutstanding(bill.getAmountOutstanding());
                
                
//                
                
                if(bill.getUnit() != null)
                {
                    detail.setUnitName(bill.getUnit().getInstitutionName());
                }
                
//                detail.setInstitutionName("GHS-Disease Control Unit");

//                detail.setInvoiceIssueDate(LocalDate.now());
//                detail.setInvoiceIssueDate(LocalDate.now());
                
//                if(voucher.getFundingSource() != null)
//                {
//                    detail.setFundingSource(voucher.getFundingSource().getSourceName());
//                }
                
//                if(voucher.getBankAccount()!= null)
//                {
//                    String bankDetail = voucher.getBankAccount().getBankName() 
//                            + " - " + voucher.getBankAccount().getAccountNo();
//                
////                    detail.setBankAccount(pv.getBankAccount().getAccountNo());
//                    
//                    detail.setBankAccount(bankDetail);
//                }
                
//                if(pv.getSupplier() != null)
//                {
//                    detail.setSupplierName(pv.getSupplier().getSupplierName());
//                    detail.setSupplierAddress(pv.getSupplier().getSupplierAddress());
//                }
                    detail.setSupplierName(bill.getSupplierName());
                    detail.setSupplierAddress(bill.getSupplierAddress());
                
                if(bill.getDepartment()!= null)
                {
                    detail.setDepartmentName(bill.getDepartment().getDepartmentName());
                }
                
//                if(voucher.getAccount()!= null)
//                {
//                    String account = 
//                            voucher.getAccount().getAccountNo()
//                            +" - " + pv.getAccount().getAccountName();
                
                detail.setPvId(bill.getBillNo());
                detail.setPreparedDate(bill.getPreparedDate());
                detail.setLpoNo(bill.getLpoNo());
                detail.setSraNo(bill.getSraNo());
                detail.setInvoiceNo(bill.getInvoiceNo());
                
                detail.setChequeWrittenDate(bill.getChequeWrittenDate());
                
                detail.setLpoDate(bill.getLpoDate());
                detail.setSraDate(bill.getSraDate());
                
//                detail.setChequeNo(bill.getChequeNo());
//                detail.setChequeIssuedDate(bill.getChequeIssuedDate());
                detail.setPaymentDetail(bill.getPaymentDetail());
                detail.setVoucherCurrency(bill.getVoucherCurrency());
                
                detail.setAuthorisedDate(bill.getAuthorisedDate());
                detail.setApprovedDate(bill.getApprovedDate());
                                
//                if(voucher.getPreparedBy() != null)
//                {
//                    detail.setPreparedByName(voucher.getPreparedBy().getAccountName());
//                }
//                
//                if(voucher.getVerifiedBy()!= null)
//                {
//                    detail.setVerifiedByName(voucher.getVerifiedBy().getAccountName());
//                }
//                
//                if(voucher.getReviewedBy()!= null)
//                {
//                    detail.setReviewedByName(voucher.getReviewedBy().getAccountName());
//                }
//                
//                if(voucher.getApprovedBy()!= null)
//                {
//                    detail.setApprovedByName(voucher.getApprovedBy().getAccountName());
//                }
                
                detail.setVatRate(bill.getVatRate());
                detail.setTourismLevyRate(bill.getTourismLevyRate());
                detail.setWitholdingTaxRate(bill.getWithholdingTaxRate());
                detail.setTourismLevyAmount(bill.getTourismLevy());
                detail.setWhtVat(bill.getWhtVat());
                
                detail.setGrossAmount(bill.getGrossAmount());

                detail.setInvoiceAmount(bill.getInvoiceAmount());
                detail.setVatAmount(bill.getVatAmount());
                detail.setWitholdingTaxAmount(bill.getWitholdingTaxAmount());
                detail.setOtherCharges(bill.getOtherCharges());
                detail.setAmountPayable(bill.getAmountPayable());
                
                detail.setNhisRate(bill.getNhisRate());
                detail.setNhisAmount(bill.getNhisAmount());
                                
                double amountToConvert = detail.getAmountPayable();
                
//                AmtInWords amtInWords = Optional.ofNullable(voucher.getUnit())
//                        .map(Institution::getAmtInWords).orElse(AmtInWords.NET_PAYMENT);
                
//                if(amtInWords == AmtInWords.GROSS_AMOUNT)
//                {
//                    amountToConvert = bill.getGrossAmount();
//                }
//                else if(amtInWords == AmtInWords.INVOICE_AMOUNT)
//                {
//                    amountToConvert = bill.getInvoiceAmount();
//                }
                
                String amtToWords = NumberToWords.getInstance()
                        .convertToWords(amountToConvert);
                                
                detail.setAmountInWords(amtToWords);
                
                detailsList.add(detail);
                
            }
            
            
        } catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error reporting summary registration", e);
        }
        
        return detailsList;
    }

    
    
    

}
