/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.gafpv.jsf;

import com.statelyhub.gafpv.entities.UserAccount;
import com.statelyhub.gafpv.service.CrudService;
import com.stately.common.security.SecurityHash;
import com.stately.modules.web.jsf.JsfMsg;
import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.gafpv.constants.UserAccessLevel;
import com.statelyhub.gafpv.service.AdminService;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 *
 * @author Edwin
 */
@Named(value = "adminLoginController")
@SessionScoped
public class AdminLoginController implements Serializable {

    @Inject
    private CrudService crudService;
    @Inject
    private UserSession userSession;
    @Inject
    private AdminService adminService;
    private List<UserAccount> collectorsList;

    private UserAccount collector = new UserAccount();

    private UserAccessLevel accessLevel = new UserAccessLevel();

    private String newPassword = "";

    public void editCollector(UserAccount collector) {
        this.collector = collector;
    }

    public void deleteAdminLogin(UserAccount collector) {
        try {
            crudService.delete(collector);
            collectorsList.remove(collector);
        } catch (Exception e) {
        }
    }

    @PostConstruct
    public void clear() {
        collector = new UserAccount();

        newPassword = "";

        if (!userSession.getAccountUR().isSuperUser()) {
            collector.setInstitution(userSession.getAccountUR().getInstitution());
        }

        JsfUtil.resetViewRoot();

    }

    public void saveCollector() {
        if (collector.isSuperUser()) {
            collector.setInstitution(null);
        }

        if (crudService.save(collector) != null) {
            JsfMsg.msg(true);
            clear();
        } else {
            JsfMsg.msg(false);
        }
    }

    public void saveNewPassword() {
        try {
            collector.setUserPassword(SecurityHash.getInstance().shaHash(newPassword));
            crudService.save(collector);

            clear();

            JsfMsg.msg(true);
        } catch (Exception e) {
        }

    }

    public void removeStaffAccess(UserAccessLevel accessLevel) {
        crudService.delete(accessLevel, true);
    }

    public void addStaffAccessLevel() {
        accessLevel.setUserAccount(collector);
//        accessLevel.set(userSession.getCompanyUR());
//        accessLevel.setBranch(userSession.getBranchUR());
        crudService.save(accessLevel);
        accessLevel = new UserAccessLevel();

        JsfUtil.resetViewRoot();
    }

    public List<UserAccessLevel> getStaffAccessLevelList() {
        return adminService.staffAccessLevel(collector);
    }

    public List<UserAccount> getCollectorsList() {
        collectorsList = adminService.getFundingSourceList(userSession.getAccountUR().getInstitution());
        return collectorsList;
    }

    public UserAccount getCollector() {
        return collector;
    }

    public void setCollector(UserAccount collector) {
        this.collector = collector;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public UserAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(UserAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    
}
