/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.common.formating.NumberFormattingUtils;
import com.stately.common.formating.ObjectValue;
import com.stately.common.utils.StringUtil;
import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.PartyType;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.PartyElection;
import com.statelyhub.elections.entities.PoliticalParty;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.entities.Result;
import com.statelyhub.elections.model.ElectionTypeResult;
import com.statelyhub.elections.model.PresidentialResult;
import com.statelyhub.elections.services.AppConfigService;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.ElectionResultService;
import com.statelyhub.elections.services.ElectionService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author edwin
 */
//@SessionScoped
@RequestScoped
@Named(value = "nationalViewController")
public class NationalViewController implements Serializable {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Inject
    private AppConfigService appConfigService;

    @Inject
    private ElectionResultService electionResultService;

    private List<ElectionTypeResult> constituencyResultList;

    private List<ConstituencyElection> completedConstituencyElectionList;
    private List<ConstituencyElection> pendingConstituencyElectionList;

    private ConstituencyElection selectedConstituencyElection;

    private Region selectedRegion;

    private List<PartyElection> partyElectionsList;

    private List<PresidentialResult> mainResultList;
    private List<PresidentialResult> parliamenSummaryList;

//    @PostConstruct
//    public void init() {
//        partyElectionsList = QryBuilder.get(crudService.getEm(), PartyElection.class).buildQry().getResultList();
//    }
    
    @PostConstruct
      public void updateNationalStats() {
        mainResultList = presiddentail(ElectionType.PRESIDENTIAL);
        parliamenSummaryList = parliament(ElectionType.PARLIAMENTARY);

    }

    public void loadConstituency() {
        List<ConstituencyElection> list = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParamWhenNotNull(ConstituencyElection._region, selectedRegion)
                .orderByDesc(ConstituencyElection._constituency_constituencyName)
                .buildQry().getResultList();

        completedConstituencyElectionList = new LinkedList<>();
        pendingConstituencyElectionList = new LinkedList<>();

        for (ConstituencyElection eps : list) {
            if (eps.getResultStatus() == ResultStatus.FINALISED) {
                completedConstituencyElectionList.add(eps);
            } else {
                pendingConstituencyElectionList.add(eps);
            }

        }

    }

    public void loadConstituencyResult(ConstituencyElection constituencyElection) {
        this.selectedConstituencyElection = constituencyElection;
        constituencyResultList = electionResultService.constituency(selectedConstituencyElection);
    }

    public void updateConstituecyFigures() {
        electionResultService.runConstituency(selectedConstituencyElection);
    }

  

    public List<PresidentialResult> presiddentail(ElectionType electionType) {
       List<PresidentialResult> list = new LinkedList<>();
        List<Object[]> presidentialList = QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                .addReturnField("e." + ElectionContestant._party)
                .addReturnField("SUM(e." + ElectionContestant._acceptedResult + ")")
                .addObjectParam(ElectionContestant._electionType, electionType)
                .addGroupBy(ElectionContestant._party)
                .buildQry().getResultList();

        StringUtil.printObjectListArray(presidentialList);

        for (Object[] objects : presidentialList) {
            PoliticalParty party = (PoliticalParty) objects[0];

            PresidentialResult result = new PresidentialResult();
            result.setPartyName(party.getPartyName());
            result.setPresidentialVotes(ObjectValue.get_intValue(objects[1]));

            list.add(result);
        }

        double totalPresidential = list.stream().mapToInt(PresidentialResult::getPresidentialVotes).sum();
        System.out.println("...... " + totalPresidential);
        for (PresidentialResult presidentialResult : list) {
            double pct = presidentialResult.getPresidentialVotes() / totalPresidential;
 
            pct = pct * 100;
            presidentialResult.setPresidentialPct(NumberFormattingUtils.formatDecimalNumberTo_2(pct));
        }

        for (PresidentialResult compare : list) {

            int counter = 0;
            for (PresidentialResult submitted : list) {
                if (submitted.getPresidentialVotes() > compare.getPresidentialVotes()) {
                    counter++;
                }
            }
            compare.setPosition(counter + 1);
        }

             Collections.sort(list, Comparator.comparing(PresidentialResult::getPosition));
        return list;
    }

    
    
    
    
    public List<PresidentialResult> parliament(ElectionType electionType) {
        List<PresidentialResult> list  = new LinkedList<>();
        List<Object[]> presidentialList = QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                .addReturnField("e." + ElectionContestant._party)
                .addReturnField("SUM(e." + ElectionContestant._won + ")")
                .addReturnField("SUM(e." + ElectionContestant._acceptedResult + ")")
                .addObjectParam(ElectionContestant._electionType, electionType)
                .addObjectParam(ElectionContestant._candidateType, PartyType.POLITICAL_PARTY)
                .addGroupBy(ElectionContestant._party)
                .buildQry().getResultList();

        StringUtil.printObjectListArray(presidentialList);

        for (Object[] objects : presidentialList) {
            PoliticalParty party = (PoliticalParty) objects[0];

            PresidentialResult result = new PresidentialResult();
            result.setPartyName(party.getPartyName());
            result.setSeatCount(ObjectValue.get_intValue(objects[1]));
            result.setPresidentialVotes(ObjectValue.get_intValue(objects[2]));

            list.add(result);
        }

        double totalPresidential = list.stream().mapToInt(PresidentialResult::getSeatCount).sum();
        
        for (PresidentialResult presidentialResult : list) {
            double pct = presidentialResult.getSeatCount() / totalPresidential;
            pct = pct * 100;
            presidentialResult.setPresidentialPct(NumberFormattingUtils.formatDecimalNumberTo_2(pct));
        }

        for (PresidentialResult compare : list) {
            int counter = 0;
            for (PresidentialResult submitted : list) {
                if (submitted.getSeatCount() > compare.getSeatCount()) {
                    counter++;
                }
            }
            compare.setPosition(counter + 1);
        }

         Collections.sort(list, Comparator.comparing(PresidentialResult::getSeatCount));
        return list;
    }

    public void updateNationalStats(List<PresidentialResult> mainResultList) {

        double totalPresidential = mainResultList.stream().mapToInt(PresidentialResult::getPresidentialVotes).sum();
        for (PresidentialResult presidentialResult : mainResultList) {
            double pct = presidentialResult.getPresidentialVotes() / totalPresidential;
            pct = pct * 100;
            presidentialResult.setPresidentialPct(NumberFormattingUtils.formatDecimalNumberTo_2(pct));
        }

        for (PresidentialResult compare : mainResultList) {

            int counter = 0;
            for (PresidentialResult submitted : mainResultList) {
                if (submitted.getPresidentialVotes() > compare.getPresidentialVotes()) {
                    counter++;
                }
            }
            compare.setPosition(counter + 1);
        }

    }

    public Region getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(Region selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public List<ConstituencyElection> getCompletedConstituencyElectionList() {
        return completedConstituencyElectionList;
    }

    public List<ConstituencyElection> getPendingConstituencyElectionList() {
        return pendingConstituencyElectionList;
    }

    public List<ElectionTypeResult> getConstituencyResultList() {
        return constituencyResultList;
    }

    public ConstituencyElection getSelectedConstituencyElection() {
        return selectedConstituencyElection;
    }

    public List<PresidentialResult> getMainResultList() {
        return mainResultList;
    }

    public List<PresidentialResult> getParliamenSummaryList() {
        return parliamenSummaryList;
    }

}
