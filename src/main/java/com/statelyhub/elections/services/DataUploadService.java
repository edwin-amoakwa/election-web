/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.services;

import com.stately.modules.jpa2.QryBuilder;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultSource;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.DistrictAssembly;
import com.statelyhub.elections.entities.Election;
import com.statelyhub.elections.entities.ElectionContestant;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStation;
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
public class DataUploadService {

    @Inject
    private CrudService crudService;

    @Inject
    private ElectionService electionService;

    @Asynchronous
//    public void process(Election election,Region region, Constituency constituency, String stationCode, String stationName)
    public void process(ConstituencyElection constituencyElection, String stationCode, String stationName) {

        PollingStation pollingStation = pollingStation(constituencyElection.getConstituency(), stationCode, stationName);

        ElectionPollingStation eps = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                .addObjectParam(ElectionPollingStation._constituency, constituencyElection.getConstituency())
                .addObjectParam(ElectionPollingStation._election, constituencyElection.getElection())
                .addObjectParam(ElectionPollingStation._pollingStation, pollingStation)
                .getSingleResult(ElectionPollingStation.class);

        if (eps == null) {
            eps = new ElectionPollingStation();
            eps.setElection(constituencyElection.getElection());
            eps.setConstituency(constituencyElection.getConstituency());
            eps.setPollingStation(pollingStation);
           eps.setConstituencyElection(constituencyElection);
            eps.setResultStatus(ResultStatus.PENDING);

            crudService.save(eps);
        }

    }

    public ConstituencyElection initConsElection(Constituency constituency, Region region, Election election, ElectionType electionType) {
        ConstituencyElection ce = QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                .addObjectParam(ConstituencyElection._constituency, constituency)
                .addObjectParam(ConstituencyElection._election, election)
                .addObjectParam(ConstituencyElection._region, region)
                //                .addObjectParam(ConstituencyElection._electionType, electionType)
                .getSingleResult(ConstituencyElection.class);

        if (ce == null) {
            ce = new ConstituencyElection();
            ce.setConstituency(constituency);
            ce.setElection(election);
            ce.setRegion(region);
            ce.setResultStatus(ResultStatus.PENDING);
            ce.setResultSource(ResultSource.SUBMITTED);
            crudService.save(ce);
        }

        electionService.getResultSet(ce, ElectionType.PRESIDENTIAL);
        electionService.getResultSet(ce, ElectionType.PARLIAMENTARY);

        return ce;
    }



    public Region region(String regionName) {
        Region region = QryBuilder.get(crudService.getEm(), Region.class)
                .addObjectParam(Region._regionName, regionName.trim())
                .getSingleResult(Region.class);

        if (region == null) {
            region = new Region();
            region.setRegionName(regionName);
            crudService.save(region);
        }

        return region;
    }

    public DistrictAssembly district(Region region, String districtName) {
        DistrictAssembly assembly = QryBuilder.get(crudService.getEm(), DistrictAssembly.class)
                .addObjectParam(DistrictAssembly._region, region)
                .addObjectParam(DistrictAssembly._stationName, districtName.trim())
                .getSingleResult(DistrictAssembly.class);

        if (assembly == null) {
            assembly = new DistrictAssembly();
            assembly.setAssemblyName(districtName);
            assembly.setRegion(region);
            crudService.save(assembly);
        }

        return assembly;
    }

    public Constituency constituency(Region region, DistrictAssembly assembly, String consistuencyName) {
        consistuencyName = consistuencyName.trim();
        Constituency constituency = QryBuilder.get(crudService.getEm(), Constituency.class)
                .addObjectParam(Constituency._region, region)
                .addObjectParam(Constituency._constituencyName, consistuencyName)
                .getSingleResult(Constituency.class);

        if (constituency == null) {
            constituency = new Constituency();
            constituency.setConstituencyName(consistuencyName);
            constituency.setRegion(region);
            constituency.setDistrict(assembly);
            crudService.save(constituency);

        }

        return constituency;
    }

    public PollingStation pollingStation(Constituency constituency, String stationCode, String stationName) {
        PollingStation station = QryBuilder.get(crudService.getEm(), PollingStation.class)
                //                .addObjectParam(PollingStation._constituency, constituency)
                .addObjectParam(PollingStation._stationCode, stationCode.trim())
                .getSingleResult(PollingStation.class);

        if (station == null) {
            station = new PollingStation();
            station.setStationCode(stationCode.trim());
            station.setStationName(stationName);
            station.setConstituency(constituency);
            crudService.save(station);
        }

        return station;
    }

    public void deleteConsituency(Constituency constituency) {
        try {

            QryBuilder.get(crudService.getEm(), PollingStationResult.class)
                    .addObjectParam(PollingStationResult._electionPollingStation_constituency, constituency).delete();

            QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                    .addObjectParam(ElectionPollingStation._constituency, constituency).delete();

            QryBuilder.get(crudService.getEm(), ElectionContestant.class)
                    .addObjectParam(ElectionContestant._constituencyElection_constituency, constituency).delete();

            QryBuilder.get(crudService.getEm(), ConstituencyElection.class)
                    .addObjectParam(ConstituencyElection._constituency, constituency).delete();

            crudService.delete(constituency);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRegion(Region region) {
        try {
            List<Constituency> constituencysList = QryBuilder.get(crudService.getEm(), Constituency.class)
                    .addObjectParam(Constituency._region, region).buildQry().getResultList();

            for (Constituency constituency : constituencysList) {
                deleteConsituency(constituency);
            }

            crudService.delete(region);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
