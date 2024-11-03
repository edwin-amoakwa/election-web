/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;


import com.statelyhub.gafpv.service.CrudService;
import com.stately.common.model.LocalDateRange;
import com.stately.common.utils.DateTimeUtils;
import com.stately.common.utils.StringUtil;
import com.stately.modules.excel.ExcelExporter;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.gafpv.detail.PvDetail;
import com.statelyhub.gafpv.entities.PaymentVocher;
import com.statelyhub.gafpv.entities.SearchQP;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.statelyhub.gafpv.reports.ReportManager;
import java.util.Date;
import java.util.LinkedList;
import org.omnifaces.util.Faces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Edwin
 */
@Named(value = "pvHistoryController")
@SessionScoped
public class PvHistoryController implements Serializable
{
    @Inject private UserSession userSession;
    @Inject private CrudService crudService;
    @Inject private ReportManager reportManager;
    
    private LocalDateRange dateRange = new LocalDateRange();
    
    
    private List<PaymentVocher> vouchersList;
    private List<PaymentVocher> selectedVouchersList = new LinkedList<>();
    private SearchQP voucherQP = new SearchQP();
    private PaymentVocher selectedVoucher;// = new Bill();
    private int selectedIndex;
    
    
    public void close()
    {
        selectedVoucher = null;
    }
    
    public void updatePvStatus()
    {
        try
        {
            selectedVoucher.setLastModifiedBy(userSession.getAccountUR().getAccountName());
            crudService.save(selectedVoucher);
            JsfMsg.msg(true);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void clearSelectedList()
    {
        selectedVouchersList.clear();
    }
    
    public void removeFromSelectedList(PaymentVocher paymentVoucher)
    {
        if(paymentVoucher == null)
        {
            return;
        }
        selectedVouchersList.remove(paymentVoucher);
    }
    
    
    public void addToSelectedList(PaymentVocher paymentVoucher)
    {
        if(paymentVoucher == null)
        {
            return;
        }
        
        if(!selectedVouchersList.contains(paymentVoucher))
        {
            selectedVouchersList.add(paymentVoucher);
            
            JsfMsg.info(paymentVoucher + " added to selected List");
        }
    }
    
    public void selectAllResult()
    {
        if(vouchersList == null)
        {
            return;
        }
        
        for (PaymentVocher paymentVoucher : vouchersList)
        {
            addToSelectedList(paymentVoucher);
        }
    }
    
    public void tabChangeListener(TabChangeEvent event)
    {
          try
          {
//              System.out.println(".. " + event.getTab().getId());
//              System.out.println(".. " + event.getTab().getTitle());
//              System.out.println(".. " + event.getComponent().getChildren().size());
              selectedIndex = ((TabView) event.getComponent()).getChildren().indexOf(event.getTab());
//          
              System.out.println("tabed chaged ..." + selectedIndex);
//              
          } catch (Exception e)
          {
              e.printStackTrace();
          }

    }
    
    
    public void printPv()
    {
        try
        {
            reportManager.reportPaymentVoucherList(Arrays.asList(selectedVoucher));
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
            reportManager.reportBankList(selectedVouchersList);
        } catch (Exception e)
        {
            e.printStackTrace();
        }        
    }
    
    public void printWitholdingTax()
    {
        try
        {
            reportManager.printWitholdingTax(selectedVouchersList);
        } catch (Exception e)
        {
            e.printStackTrace();
        }        
    }
    public void exportToExcel()
    {
        try
        {
            List<PvDetail> detailsList =  reportManager.convertPvList(vouchersList);
            
             String reportFileName = DateTimeUtils.formatDate(new Date(), "ddMMyyyy")
                + "-EPV" + ".xls";
        
        String tempFolder = System.getProperty("java.io.tmpdir");

        ExcelExporter excelExporter = new ExcelExporter(tempFolder, reportFileName, true);
        
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
    
    public void searchVoucher()
    {
        QryBuilder builder = new QryBuilder(crudService.getEm(), PaymentVocher.class);
        builder.addObjectParam(PaymentVocher._unit, userSession.getInstitution());
        builder.addDateRange(dateRange, PaymentVocher._valueDate);
        
        if (voucherQP.getSupplier() != null)
        {
//            builder.addObjectParam(PaymentVocher._supplier, voucherQP.getSupplier());
        }

        if(!StringUtil.isNullOrEmpty(voucherQP.getPaymentNo()))
        {
            builder.addStringQryParam(PaymentVocher._paymentNo, voucherQP.getBillNo(), QryBuilder.ComparismCriteria.LIKE);
        }
        
        
          if(!StringUtil.isNullOrEmpty(voucherQP.getInvoiceNo()))
        {
            builder.addStringQryParam(PaymentVocher._bill_invoiceNo, voucherQP.getBillNo(), QryBuilder.ComparismCriteria.LIKE);
        }

        if(!StringUtil.isNullOrEmpty(voucherQP.getSupplierName()))
        {
            builder.addStringQryParam(PaymentVocher._bill_supplierName, voucherQP.getSupplierName(), QryBuilder.ComparismCriteria.LIKE);
        }

        if(!StringUtil.isNullOrEmpty(voucherQP.getChequeNo()))
        {
            builder.addStringQryParam(PaymentVocher._chequeNo, voucherQP.getChequeNo(), QryBuilder.ComparismCriteria.LIKE);
        }
        

        if(!StringUtil.isNullOrEmpty(voucherQP.getVoucherCurrency()))
        {
            builder.addStringQryParam(PaymentVocher._bill_voucherCurrency, voucherQP.getVoucherCurrency(), QryBuilder.ComparismCriteria.EQUAL);
        }
        
        if (voucherQP.getDepartment() != null)
        {
            builder.addObjectParam(PaymentVocher._bill_department, voucherQP.getDepartment());
        }
        
//        if (voucherQP.getBankAccount()!= null)
//        {
//            builder.addObjectParam(Bill._bankAccount, voucherQP.getBankAccount());
//        }

        if (voucherQP.getBillStatus() != null)
        {
            builder.addObjectParam(PaymentVocher._pvStatus, voucherQP.getBillStatus());
        }

        builder.orderByAsc(PaymentVocher._valueDate);
        
        vouchersList = builder.buildQry().getResultList();
        System.out.println(" .... " + vouchersList.size());
    }
    
    public void savePaymentVoucher()
    {
        try
        {
//            if(voucherQP.getValueDate() == null)
//            {
//                voucherQP.setValueDate(LocalDate.now());
//            }
//            
//            idService.paymentVoucher(voucherQP);
            crudService.save(selectedVoucher);
            
            close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<PaymentVocher> getVouchersList()
    {
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

    public List<PaymentVocher> getSelectedVouchersList()
    {
        return selectedVouchersList;
    }

    public int getSelectedIndex()
    {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex)
    {
        this.selectedIndex = selectedIndex;
    }
    
    
    
}
