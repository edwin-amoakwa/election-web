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
import com.statelyhub.elections.entities.Constituency;
import com.statelyhub.elections.entities.DistrictAssembly;
import com.statelyhub.elections.entities.ElectionPollingStation;
import com.statelyhub.elections.entities.PollingStation;
import com.statelyhub.elections.entities.Region;
import com.statelyhub.elections.services.CrudService;
import com.statelyhub.elections.services.DataUploadService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
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
    
    @Inject private DataUploadService dataUploadService;

    private UploadedFile uploadFile;

    private List<ElectionPollingStation> stationsList = new LinkedList<>();

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

                if (StringUtil.isNullOrEmpty(regionName) || StringUtil.isNullOrEmpty(stationCode)) {
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
                   
                         
                   if (regionName.contains("Region")) {
                    continue;
                }


                Region region = dataUploadService.region(regionName);
                
//                if(true)
//                {
//                    continue;
//                }
//                
                DistrictAssembly assembly = dataUploadService.district(region, districtName);
                Constituency constituency = dataUploadService.constituency(region, assembly, consistuencyName);
                
                dataUploadService.process(userSession.getElectionUR(), region, constituency, stationCode, stationName);
                
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
    


    public UploadedFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadedFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public List<ElectionPollingStation> getStationsList() {
        return stationsList;
    }

}
