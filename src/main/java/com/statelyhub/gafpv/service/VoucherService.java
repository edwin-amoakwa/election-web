/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.service;

import com.statelyhub.gafpv.entities.Bill;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.gafpv.entities.Institution;
import com.statelyhub.gafpv.entities.BillStatus;
import com.statelyhub.gafpv.entities.PaymentStatus;
import com.statelyhub.gafpv.entities.PaymentVocher;
import java.util.LinkedList;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Stateless
public class VoucherService {

    @Inject
    private CrudService crudService;

    public List<Bill> getPendingBills(Institution institution) {
        try {
            QryBuilder qryBuilder = new QryBuilder(crudService.getEm(), Bill.class);
            qryBuilder.addObjectParam(Bill._unit, institution);
//             qryBuilder.addObjectParam(Bill._archived, false);
            qryBuilder.addObjectParamNotEqual(Bill._billStatus, BillStatus.INVALID);
            qryBuilder.addObjectParamNotEqual(Bill._paymentStatus, PaymentStatus.FULLY_PAID);
            qryBuilder.orderByDesc(Bill._valueDate);
            qryBuilder.orderByDesc(Bill._createdDate);

            return qryBuilder.buildQry()
                    //                     .setFirstResult(0).setMaxResults(20)
                    .getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new LinkedList<>();
    }

    public List<PaymentVocher> get(Bill bill) {
        return QryBuilder.get(crudService.getEm(), PaymentVocher.class)
                .addObjectParam(PaymentVocher._bill, bill)
                .orderByAsc(PaymentVocher._createdDate)
                .buildQry().getResultList();
    }

    public void evaluateAndSave(Bill bill) {
        List<PaymentVocher> billVouchersList = get(bill);

        double totalPaid = billVouchersList.stream()
                .filter(item -> item.getPvStatus() == BillStatus.APPROVED)
                .mapToDouble(PaymentVocher::getPaymentAmount).sum();
        if (totalPaid == 0) {
            bill.setPaymentStatus(PaymentStatus.NOT_PAID);
        } else if (totalPaid == bill.getAmountPayable()) {
            bill.setPaymentStatus(PaymentStatus.FULLY_PAID);
        } else {
            bill.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
        }
        bill.setAmountPaid(totalPaid);
        bill.setAmountOutstanding(bill.getAmountPayable() - bill.getAmountPaid());
        crudService.saveEntity(bill);
    }

}
