/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.entities;

import com.stately.modules.jpa2.UniqueEntityModel3;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Base64;

/**
 *
 * @author Edwin
 */
@Entity
@Table(name = "submitted_result_images")
public class SubmittedResultPicture extends UniqueEntityModel3 {

    public static final String _resultSubmission = "resultSubmission";
    public static final String _resultSubmission_id = _resultSubmission+"."+ResultSubmission._id;
    @JoinColumn(name = "result_submission")
    @ManyToOne
    private ResultSubmission resultSubmission;
    
    public static final String _image = "image";
    @Column(name = "image")
    private byte[] image;
    
    @Column(name = "image_format")
    private String imageFormat;

    public ResultSubmission getResultSubmission() {
        return resultSubmission;
    }

    public void setResultSubmission(ResultSubmission resultSubmission) {
        this.resultSubmission = resultSubmission;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }
    
    public String getImageSRC()
    {
        try {
            String base64 =
                getImageFormat()
                + ","
                + Base64.getEncoder().encodeToString(getImage());
            return base64;
        } catch (Exception e) {
        }
        return null;
    }
}
