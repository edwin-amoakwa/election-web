/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.statelyhub.gafpv.entities.Bill;
import com.statelyhub.gafpv.service.CrudService;
import com.stately.common.model.LocalDateRange;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.gafpv.entities.BillStatus;
import java.io.Serializable;
import java.util.List;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.statelyhub.gafpv.reports.ReportManager;
import java.time.LocalDate;

/**
 *
 * @author Edwin
 */
@Named(value = "billPendingAuthController")
@SessionScoped
public class BillPendingAuthController implements Serializable
{

    @Inject
    private UserSession userSession;
    @Inject
    private CrudService crudService;
    @Inject
    private ReportManager reportManager;

    private LocalDateRange dateRange = new LocalDateRange();

    private List<Bill> vouchersList;

    private Bill voucherQP = new Bill();
    private Bill selectedVoucher;
    

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
                    return;
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
                    return;
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

            selectedVoucher.setBillStatus(BillStatus.APPROVED);

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
//            reportManager.reportList(Arrays.asList(selectedVoucher));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void printPvList()
    {
        try
        {
//            reportManager.reportList(vouchersList);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void printSelectedPvList()
    {
        try
        {
//            reportManager.reportBankList(vouchersList);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void exportToExcel()
    {
        try
        {
//            List<PvDetail> detailsList = reportManager.convert(vouchersList);
//
//            String reportFileName = DateTimeUtils.formatDate(new Date(), "ddMMyyyy")
//                    + "-EPV" + ".xls";
//
//            String tempFolder = System.getProperty("java.io.tmpdir");
//
//            ExcelExporter excelExporter = new ExcelExporter(tempFolder, reportFileName, true);
//
//            excelExporter.addSheetData(detailsList, PvDetail.class, true).finalise();
////        contractNoteExporter.addSheetData(affiliateSearch.getAffiliateList(), Affiliate.class, true).finalise();
//
//            try
//            {
////            File file = excelExporter.getExportFile();
////            Faces.sendFile(file, true);
//                Faces.sendFile(excelExporter.getFileStream(), reportFileName, true);
//
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void viewPaymentVoucher(Bill paymentVoucher)
    {
        this.selectedVoucher = paymentVoucher;
    }

    public void print(Bill paymentVoucher)
    {
//        reportManager.reportPaymentVoucherList(Arrays.asList(paymentVoucher));
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

    public List<Bill> getVouchersList()
    {
        vouchersList = QryBuilder.get(crudService.getEm(), Bill.class)
                .addObjectParam(Bill._unit, userSession.getInstitution())
                .addObjectParam(Bill._billStatus, BillStatus.PENDING)
                .orderByAsc(Bill._preparedDate)
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

    public Bill getVoucherQP()
    {
        return voucherQP;
    }

    public void setVoucherQP(Bill voucherQP)
    {
        this.voucherQP = voucherQP;
    }

    public Bill getSelectedVoucher()
    {
        return selectedVoucher;
    }
    
}
