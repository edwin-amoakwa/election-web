/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.common.formating.ObjectValue;
import com.stately.common.utils.StringUtil;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.constants.SubmissionStatus;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.PollingStationResultSet;
import com.statelyhub.elections.entities.ResultSubmission;
import com.statelyhub.elections.model.ElectionTypeDashboard;
import com.statelyhub.elections.utils.NumberUtil;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author edwin
 */
@Stateless
public class DashboardService {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Inject
    private ElectionResultService electionResultService;

    public ElectionTypeDashboard dashboard(ElectionType electionType, ConstituencyElection constituencyElection) {
        ElectionTypeDashboard dashboard = new ElectionTypeDashboard();
        dashboard.setElectionType(electionType);

        if (constituencyElection != null) {
            dashboard.setTotalRegisteredVoters(constituencyElection.getVotersCount());
            dashboard.setTotalPollingStation(constituencyElection.getPollingStationCount());
        }

        List<Object[]> pollSummary = QryBuilder.get(crudService.getEm(), PollingStationResultSet.class)
                .addReturnField("e." + PollingStationResultSet._resultStatus)
                .addReturnField("COUNT(e." + PollingStationResultSet._id + ")")
                .addObjectParam(PollingStationResultSet._electionPollingStation_constituencyElection, constituencyElection)
                .addObjectParam(PollingStationResultSet._electionType, electionType)
                .addGroupBy(PollingStationResultSet._resultStatus)
                .printQryInfo()
                .buildQry().getResultList();
        System.out.println(".... " + pollSummary);
        for (Object[] result : pollSummary) {
            ResultStatus status = (ResultStatus) result[0];
            int value = ObjectValue.get_intValue(result[1]);

            if (status == ResultStatus.PENDING) {
                dashboard.setPollingStationPending(value);
            } else if (status == ResultStatus.FINALISED) {
                dashboard.setPollingStationCompleted(value);
            }
        }

        Object[] pollingStats = QryBuilder.get(crudService.getEm(), PollingStationResultSet.class)
                //                .addReturnField("e." + PollingStationResultSet._resultStatus)
                .addReturnField("SUM(e." + PollingStationResultSet._validVotes + ")")
                .addReturnField("SUM(e." + PollingStationResultSet._rejectedBallots + ")")
                .addReturnField("SUM(e." + PollingStationResultSet._spoiltBallots + ")")
                .addObjectParam(PollingStationResultSet._electionPollingStation_constituencyElection, constituencyElection)
                .addObjectParam(PollingStationResultSet._electionType, electionType)
                .addGroupBy(PollingStationResultSet._resultStatus).getSingleResult(Object[].class);

        if (pollingStats != null) {
            StringUtil.printArray(pollingStats);
            dashboard.setValidVotes(ObjectValue.get_intValue(pollingStats[0]));
            dashboard.setRejectedBallot(ObjectValue.get_intValue(pollingStats[1]));
            dashboard.setSpoiltVotes(ObjectValue.get_intValue(pollingStats[2]));
        }

        dashboard.setPollingStationPct(NumberUtil.pct(dashboard.getPollingStationCompleted(), dashboard.getTotalPollingStation()));

//        System.out.println("... submission");
        List<Object[]> resultList = QryBuilder.get(crudService.getEm(), ResultSubmission.class)
                .addReturnField("e." + ResultSubmission._submissionStatus)
                .addReturnField("COUNT(e." + ResultSubmission._id + ")")
                .addObjectParam(ResultSubmission._constituencyElection, constituencyElection)
                .addGroupBy(ResultSubmission._submissionStatus)
                .buildQry().getResultList();

        int total = 0;

//        System.out.println(".... submisstions");
        StringUtil.printObjectListArray(resultList);
        for (Object[] result : resultList) {
            SubmissionStatus status = (SubmissionStatus) result[0];
            int value = ObjectValue.get_intValue(result[1]);
            
            System.out.println(electionType + " -- subs ..... " + status + " ... " + value);

            total += value;

            if (status == SubmissionStatus.ACCEPTED) {
                dashboard.setTotalProcessed(total);
            }
            else if (status == SubmissionStatus.PENDING) {
                dashboard.setUnprocessedSubmission(total);
            }
            else if (status == SubmissionStatus.OPEN) {
                dashboard.setPendingSubmission(total);
            }

        }

        dashboard.setContestantsList(electionService.consititueny(constituencyElection, electionType));

        return dashboard;
    }

}
