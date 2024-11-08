/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.PartyType;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PartyElection;
import com.statelyhub.elections.entities.PoliticalParty;
import com.statelyhub.elections.entities.PollingStationResult;
import com.statelyhub.elections.entities.Region;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 *
 * @author edwin
 */
@Stateless
public class UpdateStatsService {

    @Inject
    private CrudService crudService;

    @Inject
    private DataUploadService dataUploadService;

    public void update(Election election) {

        List<Region> regionsList = QryBuilder.get(crudService.getEm(), Region.class)
                .orderByAsc(Region._regionName)
                .buildQry().getResultList();

        for (Region region : regionsList) {

            int consituency = QryBuilder.get(crudService.getEm(), Constituency.class)
                    .addObjectParam(Constituency._region, region).count();

            int polling = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                    .addObjectParam(ElectionPollingStation._constituency_region, region).count();

            region.setConstituencyCount(consituency);
            region.setPollingStationCount(polling);

            crudService.save(region);
        }

//        List<Constituency> constituencysList= QryBuilder.get(crudService.getEm(), Constituency.class).buildQry().getResultList();
        List<ConstituencyElection> cesList = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParam(ConstituencyElection._election, election)
                .buildQry().getResultList();

        for (ConstituencyElection constituencyElection : cesList) {

            int polling = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                    .addObjectParam(ElectionPollingStation._constituency, constituencyElection.getConstituency())
                    .addObjectParam(ElectionPollingStation._election, constituencyElection.getElection())
                    .count();

            constituencyElection.setPollingStationCount(polling);
            crudService.save(constituencyElection);

        }

    }

//    @Asynchronous
//    public void initIaliseDefaultContesttants(Election election) {
//        List<ConstituencyElection> constituencys = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
//                .addObjectParam(ConstituencyElection._election, election).buildQry().getResultList();
//        
//        
//        List<PartyElection> partyElectionsList = QryBuilder.get(crudService.getEm(), PartyElection.class)
//                .addObjectParam(PartyElection._election, election).printQryInfo().buildQry().getResultList();
//        
//        for (ConstituencyElection constituency : constituencys) 
//        {
//            for (PartyElection partyElection : partyElectionsList) {
//                initConstituencyContestants(constituency, partyElection.getParty(), ElectionType.PRESIDENTIAL);
//                initConstituencyContestants(constituency, partyElection.getParty(), ElectionType.PARLIAMENTARY);
//            }
//            
//        }
//    }
    @Asynchronous
    public void initConstituencyContestants(ConstituencyElection constituency, PartyElection partyElection, ElectionType electionType) {

//        System.out.println("--doing "+electionType+" for "+party.getInitials()+" : party.getPartyType() = "+party.getPartyType()+" : constituency = "+constituency.getConstituency().getConstituencyName());
        if (partyElection.getParty().getPartyType() == PartyType.INDEPENDENT_CANDIDATE && electionType == ElectionType.PARLIAMENTARY) {
            //independent parties will not be added to eah consituency
            return;
        }
//        System.out.println("--here here passed");

        ElectionContestant contestant = QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                .addObjectParam(ElectionContestant._party, partyElection.getParty())
                .addObjectParam(ElectionContestant._electionType, electionType)
                .addObjectParam(ElectionContestant._constituencyElection, constituency)
                .getSingleResult(ElectionContestant.class);

        if (contestant == null) 
        {
            contestant = new ElectionContestant();
            contestant.setParty(partyElection.getParty());
            contestant.setConstituencyElection(constituency);
            contestant.setElectionType(electionType);
            contestant.setViewOrder(partyElection.getViewOrder());
//            contestant.setResultStatus(ResultStatus.PENDING);
//            crudService.save(contestant);
        } 
        
        try 
        {
            System.out.println("contestant.getParty() = "+contestant.getParty());
            if(contestant.getParty() == null)
            {
                contestant.setCandidateType(PartyType.INDEPENDENT_CANDIDATE);
            }
            else if(contestant.getParty().isPoliticalParty())
            {
                contestant.setCandidateType(PartyType.POLITICAL_PARTY);
            }
            else
            {
                contestant.setCandidateType(PartyType.INDEPENDENT_CANDIDATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        crudService.save(contestant);
//        System.out.println("......... " + contestant);

        addContestant(constituency, contestant);

    }

    public void initPollingStationContestants(ElectionContestant contestant, ElectionPollingStation eps) {
        PollingStationResult stationResult = QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                .addObjectParam(PollingStationResult._electionContestant, contestant)
                .addObjectParam(PollingStationResult._electionPollingStation, eps)
                .getSingleResult(PollingStationResult.class);

        if (stationResult == null) {
            stationResult = new PollingStationResult();
            stationResult.setElection(eps.getElection());
            stationResult.setConstituencyElection(contestant.getConstituencyElection());
            stationResult.setElectionContestant(contestant);
            stationResult.setElectionPollingStation(eps);
            stationResult.setElectionType(contestant.getElectionType());
            stationResult.setResultStatus(ResultStatus.PENDING);
            stationResult.setViewOrder(contestant.getViewOrder());
            crudService.save(stationResult);
        }

//        System.out.println("..... " + stationResult);
    }
    
    public void addContestant(ConstituencyElection constituencyElection, ElectionContestant electionContestant)
    {
                List<ElectionPollingStation> epsesList = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._constituencyElection, constituencyElection).buildQry().getResultList();

        for (ElectionPollingStation electionPollingStation : epsesList) {
            initPollingStationContestants(electionContestant, electionPollingStation);
        }
    }

}
