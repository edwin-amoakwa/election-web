/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.old.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "funding_source")
public class FundingSource extends UniqueEntityModel3
{
    public static final String _sourceId = "sourceId";
    @Column(name = "source_id")
    private String sourceId;
    
    public static final String _sourceName = "sourceName";
    @Column(name = "source_name")
    private String sourceName;
    
     public static final String _institution = "institution";
    @JoinColumn(name = "institution")
    @ManyToOne
    private Institution institution;

    public String getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }

     @Override
    public String toString()
    {
        return sourceName;
    }

    public Institution getInstitution()
    {
        return institution;
    }

    public void setInstitution(Institution institution)
    {
        this.institution = institution;
    }
   
    
    
    
}
