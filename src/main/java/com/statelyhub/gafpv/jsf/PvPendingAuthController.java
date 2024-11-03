/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;


import com.statelyhub.gafpv.service.CrudService;
import com.stately.common.model.LocalDateRange;
import com.stately.common.utils.DateTimeUtils;
import com.stately.modules.excel.ExcelExporter;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.gafpv.detail.PvDetail;
import com.statelyhub.gafpv.entities.BillStatus;
import com.statelyhub.gafpv.entities.PaymentVocher;
import com.statelyhub.gafpv.entities.SearchQP;
import java.io.Serializable;
import java.util.List;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.statelyhub.gafpv.reports.ReportManager;
import com.statelyhub.gafpv.service.LedgerService;
import com.statelyhub.gafpv.service.VoucherService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import org.omnifaces.util.Faces;

/**
 *
 * @author Edwin
 */
@Named(value = "pvPendingAuthController")
@SessionScoped
public class PvPendingAuthController implements Serializable
{

    @Inject
    private UserSession userSession;
    @Inject
    private CrudService crudService;
    @Inject
    private ReportManager reportManager;
    
        @Inject
    private VoucherService voucherService;
        
                @Inject
    private LedgerService ledgerService;

    private LocalDateRange dateRange = new LocalDateRange();

    private List<PaymentVocher> vouchersList;

    private SearchQP voucherQP = new SearchQP();
    private PaymentVocher selectedVoucher;
    

    public void close()
    {
        selectedVoucher = null;
    }

    
    public void verifyPv()
    {
        try
        {
            if (selectedVoucher.getApprovedBy() != null)
            {
                if (selectedVoucher.getApprovedBy().equals(userSession.getAccountUR()))
                {
                    JsfMsg.error("Same person cannot authorise twice");
//                    return;
                }
            }

            selectedVoucher.setVerifiedBy(userSession.getAccountUR());
            selectedVoucher.setAuthorisedDate(LocalDate.now());
            crudService.save(selectedVoucher);
            JsfMsg.msg(true);
            
             updateStatus();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void approveBy()
    {
        try
        {
            if (selectedVoucher.getVerifiedBy() != null)
            {
                if (selectedVoucher.getVerifiedBy().equals(userSession.getAccountUR()))
                {
                    JsfMsg.error("Same person cannot authorise twice");
//                    return;
                }
            }

            selectedVoucher.setApprovedBy(userSession.getAccountUR());
            selectedVoucher.setApprovedDate(LocalDate.now());
            crudService.save(selectedVoucher);
            JsfMsg.msg(true);
            
            updateStatus();
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateStatus()
    {
        try
        {
            if (selectedVoucher == null)
            {
                return;
            }

            if (selectedVoucher.getVerifiedBy() == null
                    || selectedVoucher.getApprovedBy() == null)
            {
                return;
            }

            selectedVoucher.setPvStatus(BillStatus.APPROVED);
            selectedVoucher.setValueDate(LocalDate.now());
            
            voucherService.evaluateAndSave(selectedVoucher.getBill());
            
            ledgerService.post(selectedVoucher);
            

            crudService.save(selectedVoucher);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void printPv()
    {
        try
        {
            reportManager.reportList(Arrays.asList(selectedVoucher));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void printPvList()
    {
        try
        {
            reportManager.reportList(vouchersList);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void printSelectedPvList()
    {
        try
        {
            reportManager.reportBankList(vouchersList);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void exportToExcel()
    {
        try
        {
            List<PvDetail> detailsList = reportManager.convertPvList(vouchersList);

            String reportFileName = DateTimeUtils.formatDate(new Date(), "ddMMyyyy")
                    + "-EPV" + ".xls";

            String tempFolder = System.getProperty("java.io.tmpdir");

            ExcelExporter excelExporter = new ExcelExporter(ExcelExporter.TEMP_DIR, reportFileName, true);

            excelExporter.addSheetData(detailsList, PvDetail.class, true).finalise();
//        contractNoteExporter.addSheetData(affiliateSearch.getAffiliateList(), Affiliate.class, true).finalise();

            try
            {
//            File file = excelExporter.getExportFile();
//            Faces.sendFile(file, true);
                Faces.sendFile(excelExporter.getFileStream(), reportFileName, true);

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void viewPaymentVoucher(PaymentVocher paymentVoucher)
    {
        this.selectedVoucher = paymentVoucher;
    }

    public void print(PaymentVocher paymentVoucher)
    {
        reportManager.reportPaymentVoucherList(Arrays.asList(paymentVoucher));
    }

    public void savePaymentVoucher()
    {
        try
        {
            crudService.save(selectedVoucher);

            close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<PaymentVocher> getVouchersList()
    {
        vouchersList = QryBuilder.get(crudService.getEm(), PaymentVocher.class)
                .addObjectParam(PaymentVocher._unit, userSession.getInstitution())
                .addObjectParam(PaymentVocher._pvStatus, BillStatus.PENDING)
                .orderByAsc(PaymentVocher._preparedDate)
                .buildQry().getResultList();
        return vouchersList;
    }

    public LocalDateRange getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(LocalDateRange dateRange)
    {
        this.dateRange = dateRange;
    }

    public SearchQP getVoucherQP()
    {
        return voucherQP;
    }

    public void setVoucherQP(SearchQP voucherQP)
    {
        this.voucherQP = voucherQP;
    }

    public PaymentVocher getSelectedVoucher()
    {
        return selectedVoucher;
    }
    
}
