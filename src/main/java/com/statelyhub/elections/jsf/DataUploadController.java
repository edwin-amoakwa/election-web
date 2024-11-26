/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.statelyhub.elections.jsf;

import com.stately.common.formating.ObjectValue;
import com.stately.common.utils.StringUtil;
import com.stately.modules.excel.ExcelDataLoader;
import com.stately.modules.excel.ExcelExporter;
import com.stately.modules.jpa2.QryBuilder;
import com.stately.modules.web.jsf.JsfMsg;
import com.statelyhub.elections.constants.ElectionType;
import com.statelyhub.elections.constants.ResultStatus;
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.ConstituencyElection;
import com.statelyhub.elections.entities.DistrictAssembly;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStation;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.model.UploadContainer;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.DataUploadService;
import com.statelyhub.elections.services.UpdateStatsService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author edwin
 */
@SessionScoped
@Named(value = "dataUploadController")
public class DataUploadController implements Serializable {

    private @Inject
    CrudService crudService;

    private @Inject
    UserSession userSession;

    @Inject
    private DataUploadService dataUploadService;

    @Inject
    private UpdateStatsService updateStatsService;

    private UploadedFile uploadFile;

    private List<ElectionPollingStation> stationsList = new LinkedList<>();
    private List<UploadContainer> containerList = new LinkedList<>();

    public void handleFileUpload() {
        try {

//            QryBuilder.get(crudService.getEm(), ElectionPollingStation.class).delete();
//            QryBuilder.get(crudService.getEm(), PollingStation.class).delete();
//            QryBuilder.get(crudService.getEm(), Constituency.class).delete();
//
//            QryBuilder.get(crudService.getEm(), DistrictAssembly.class).delete();
//            QryBuilder.get(crudService.getEm(), Region.class).delete();
            File bankFile = new File(ExcelExporter.TEMP_DIR, uploadFile.getFileName());
            FileUtils.writeByteArrayToFile(bankFile, uploadFile.getContent());
            List<Object[]> uploadList = ExcelDataLoader.read(bankFile.getAbsolutePath()).getWorkBookData();

            System.out.println("total rows ... " + uploadList.size());

            uploadList.remove(0);

            int index = 0;
            for (Object[] resultRow : uploadList) {
                index++;
                System.out.println("index ..... " + index + " >>>>>>> ---- " + resultRow.length);
                StringUtil.printArrayHorizontally(resultRow);
                StringUtil.printArray(resultRow);
                if (resultRow.length < 4) {
                    continue;
                }

                String counter = ObjectValue.getStringValue(resultRow[0]);
                String stationCode = ObjectValue.getStringValue(resultRow[1]);
                String stationName = ObjectValue.getStringValue(resultRow[2]);
                String consistuencyName = ObjectValue.getStringValue(resultRow[3]);
                String districtName = ObjectValue.getStringValue(resultRow[4]).trim();
                String regionName = ObjectValue.getStringValue(resultRow[5]).trim();

                System.out.println(index + " .... " + counter + " --- " + stationCode + " ---- " + stationName + " --- " + consistuencyName + "..regionName." + regionName);

                if (StringUtil.isNullOrEmpty(regionName) || StringUtil.isNullOrEmpty(stationCode)
                        || StringUtil.isNullOrEmpty(consistuencyName)) {
                    continue;
                }

                if (stationCode.length() < 5) {
                    continue;
                }

                if (regionName.length() < 3) {
                    continue;
                }

                if (regionName.contains("0")) {
                    continue;
                }

                if (consistuencyName.contains("1.0")) {
                    continue;
                }

                if (regionName.contains("Region")) {
                    continue;
                }

                Region region = dataUploadService.region(regionName);

//                
                DistrictAssembly assembly = dataUploadService.district(region, districtName);
                Constituency constituency = dataUploadService.constituency(region, assembly, consistuencyName);

                ConstituencyElection presidential = dataUploadService.initConsElection(constituency, region, userSession.getElectionUR());
//                ConstituencyElection parliamentary = dataUploadService.initConsElection(constituency, region, userSession.getElectionUR(), ElectionType.PARLIAMENTARY);

                dataUploadService.process(presidential, stationCode, stationName);
//                dataUploadService.process(parliamentary, stationCode, stationName);

//                PollingStation pollingStation = dataUploadService.pollingStation(constituency, stationCode, stationName);
//
//                ElectionPollingStation eps = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
//                        .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
//                        .addObjectParam(ElectionPollingStation._pollingStation, pollingStation)
//                        .getSingleResult(ElectionPollingStation.class);
//
//                if (eps == null) {
//                    eps = new ElectionPollingStation();
//                    eps.setElection(userSession.getElectionUR());
//                    eps.setConstituency(constituency);
//                    eps.setPollingStation(pollingStation);
//
//                    crudService.save(eps);
//                }
//                
//                dataUploadService.initConsElection(constituency, region, userSession.getElectionUR());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadByConstituency() {
        try {

            containerList = new LinkedList<>();

//            QryBuilder.get(crudService.getEm(), ElectionPollingStation.class).delete();
//            QryBuilder.get(crudService.getEm(), PollingStation.class).delete();
//            QryBuilder.get(crudService.getEm(), Constituency.class).delete();
//
//            QryBuilder.get(crudService.getEm(), DistrictAssembly.class).delete();
//            QryBuilder.get(crudService.getEm(), Region.class).delete();
            File bankFile = new File(ExcelExporter.TEMP_DIR, uploadFile.getFileName());
            FileUtils.writeByteArrayToFile(bankFile, uploadFile.getContent());
            List<Object[]> uploadList = ExcelDataLoader.read(bankFile.getAbsolutePath()).getWorkBookData();

            System.out.println("total rows ... " + uploadList.size());

            uploadList.remove(0);

            int index = 0;
            for (Object[] resultRow : uploadList) {
                index++;
                System.out.println("index ..... " + index + " >>>>>>> ---- " + resultRow.length);
                StringUtil.printArrayHorizontally(resultRow);
                StringUtil.printArray(resultRow);
                if (resultRow.length < 6) {
                    continue;
                }

                String counter = ObjectValue.getStringValue(resultRow[0]);
                String stationCode = ObjectValue.getStringValue(resultRow[1]);
                String stationName = ObjectValue.getStringValue(resultRow[2]);
                
                int positin = 3;
                
                while (positin < resultRow.length) {
                    String value = ObjectValue.getStringValue(resultRow[positin]);
                    
                    System.out.println("checking ... " +value + " ... " + positin);
                    
                    if (value.matches(".*[a-zA-Z].*")) {
                        
                        break;
                        
                    } else {
                        stationName = (stationName + " " + value.trim()).trim();
                        positin++;
                    }
                }
                
                
                String consistuencyName = ObjectValue.getStringValue(resultRow[positin]);
                String districtName = ObjectValue.getStringValue(resultRow[positin + 1]).trim();
                String regionName = ObjectValue.getStringValue(resultRow[positin + 2]).trim();

                System.out.println(index + " .... " + counter + " --- " + stationCode + " ---- " + stationName + " --- " + consistuencyName + "..regionName." + regionName);

                if (StringUtil.isNullOrEmpty(regionName) || StringUtil.isNullOrEmpty(stationCode)
                        || StringUtil.isNullOrEmpty(consistuencyName)) {
                    continue;
                }

                if (stationCode.length() < 5) {
                    continue;
                }

                if (regionName.length() < 3) {
                    continue;
                }

                if (regionName.contains("0")) {
                    continue;
                }

                if (consistuencyName.contains("1.0")) {
                    continue;
                }

                if (regionName.contains("Region")) {
                    continue;
                }

                UploadContainer container = new UploadContainer();
                container.setRegionName(regionName);
                container.setConstitencyName(consistuencyName);
                container.setDisctrictName(districtName);
                container.setPollingStationCode(stationCode);
                container.setStationName(stationName);

                containerList.add(container);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUploadData() {

        Set<ConstituencyElection> constituencyElectionsList = new LinkedHashSet<>();

        for (UploadContainer uploadContainer : containerList) {

            Region region = dataUploadService.region(uploadContainer.getRegionName());

//                
            DistrictAssembly assembly = dataUploadService.district(region, uploadContainer.getDisctrictName());
            Constituency constituency = dataUploadService.constituency(region, assembly, uploadContainer.getConstitencyName());

//            ConstituencyElection presidential = dataUploadService.initConsElection(constituency, region, userSession.getElectionUR(), ElectionType.PRESIDENTIAL);
            ConstituencyElection constituencyElection = dataUploadService.initConsElection(constituency, region, userSession.getElectionUR());

            constituencyElectionsList.add(constituencyElection);

            PollingStation pollingStation = dataUploadService.pollingStation(constituency, uploadContainer.getPollingStationCode(), uploadContainer.getStationName());

            ElectionPollingStation eps = QryBuilder.get(crudService.getEm(), ElectionPollingStation.class)
                    .addObjectParam(ElectionPollingStation._constituency, constituency)
                    .addObjectParam(ElectionPollingStation._election, userSession.getElectionUR())
                    .addObjectParam(ElectionPollingStation._pollingStation, pollingStation)
                    .getSingleResult(ElectionPollingStation.class);

            if (eps == null) {
                eps = new ElectionPollingStation();
                eps.setElection(userSession.getElectionUR());
                eps.setConstituency(constituency);
                eps.setPollingStation(pollingStation);
                eps.setConstituencyElection(constituencyElection);
                eps.setResultStatus(ResultStatus.PENDING);

                crudService.save(eps);
            }

        }

        for (ConstituencyElection constituencyElection : constituencyElectionsList) {
            updateStatsService.initIaliseDefaultContesttants(constituencyElection, userSession.getElectionUR());
        }

        JsfMsg.info("Completed");
    }

    public UploadedFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadedFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public List<ElectionPollingStation> getStationsList() {
        return stationsList;
    }

    public List<UploadContainer> getContainerList() {
        return containerList;
    }

}
