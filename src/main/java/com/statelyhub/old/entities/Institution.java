/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.entities;

import com.stately.modules.jpa2.UniqueEntityModel2;
import com.statelyhub.old.constants.AmtInWords;
import com.statelyhub.old.constants.AuthorisationRequirement;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "institutions")
public class Institution extends UniqueEntityModel2 implements Serializable
{
    @Column(name = "institution_id")
    private String institutionId;
    
    @Column(name = "institution_name")
    private String institutionName;
    
    @Column(name = "approve_by_label")
    private String approveByLabel;
    
    @Column(name = "amount_in_words")
    @Enumerated(EnumType.STRING)
    private AmtInWords amtInWords;
    
    @Column(name = "authorisation_requirement")
    @Enumerated(EnumType.STRING)
    private AuthorisationRequirement authorisationRequirement =  AuthorisationRequirement.REQUIRED;

    public String getInstitutionId()
    {
        return institutionId;
    }

    public void setInstitutionId(String institutionId)
    {
        this.institutionId = institutionId;
    }

    public String getInstitutionName()
    {
        return institutionName;
    }

    public void setInstitutionName(String institutionName)
    {
        this.institutionName = institutionName;
    }

    public AmtInWords getAmtInWords()
    {
        return amtInWords;
    }

    public void setAmtInWords(AmtInWords amtInWords)
    {
        this.amtInWords = amtInWords;
    }

    public AuthorisationRequirement getAuthorisationRequirement()
    {
        return authorisationRequirement;
    }

    public void setAuthorisationRequirement(AuthorisationRequirement authorisationRequirement)
    {
        this.authorisationRequirement = authorisationRequirement;
    }

    public String getApproveByLabel()
    {
        return approveByLabel;
    }

    public void setApproveByLabel(String approveByLabel)
    {
        this.approveByLabel = approveByLabel;
    }
    
    

    @Override
    public String toString()
    {
        return institutionName;
    }

    
    
}
