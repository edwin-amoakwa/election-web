/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.jsf;

import com.statelyhub.elections.entities.UserAccount;
import com.statelyhub.elections.services.CrudService;
import com.stately.common.security.SecurityHash;
import com.stately.modules.web.jsf.JsfMsg;
import com.stately.modules.web.jsf.JsfUtil;
import com.statelyhub.elections.constants.UserAccessLevel;
import com.statelyhub.old.service.AdminService;
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
    private List<UserAccount> userAccountsList;

    private UserAccount userAccount = new UserAccount();

    private UserAccessLevel accessLevel = new UserAccessLevel();

    private String newPassword = "";

    public void editUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void deleteAdminLogin(UserAccount userAccount) {
        try {
            crudService.delete(userAccount);
            userAccountsList.remove(userAccount);
        } catch (Exception e) {
        }
    }

    @PostConstruct
    public void clear() {
        userAccount = new UserAccount();

        newPassword = "";

//        if (!userSession.getAccountUR().isSuperUser()) {
//            userAccount.setInstitution(userSession.getAccountUR().getInstitution());
//        }

        JsfUtil.resetViewRoot();

    }

    public void saveUserAccount() {
//        if (userAccount.isSuperUser()) {
////            userAccount.setInstitution(null);
//        }

        if (crudService.save(userAccount) != null) {
            JsfMsg.msg(true);
            clear();
        } else {
            JsfMsg.msg(false);
        }
    }

    public void saveNewPassword() {
        try {
            userAccount.setUserPassword(SecurityHash.getInstance().shaHash(newPassword));
            crudService.save(userAccount);

            clear();

            JsfMsg.msg(true);
        } catch (Exception e) {
        }

    }

    public void removeStaffAccess(UserAccessLevel accessLevel) {
        crudService.delete(accessLevel, true);
    }

    public void addStaffAccessLevel() {
        accessLevel.setUserAccount(userAccount);
//        accessLevel.set(userSession.getCompanyUR());
//        accessLevel.setBranch(userSession.getBranchUR());
        crudService.save(accessLevel);
        accessLevel = new UserAccessLevel();

        JsfUtil.resetViewRoot();
    }

    public List<UserAccessLevel> getStaffAccessLevelList() {
        return adminService.staffAccessLevel(userAccount);
    }

    public List<UserAccount> getUserAccountsList() {
        userAccountsList = adminService.getUserAccountList(userSession.getAccountUR().getInstitution());
        return userAccountsList;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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
