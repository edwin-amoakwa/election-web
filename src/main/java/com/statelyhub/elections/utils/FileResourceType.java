/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.statelyhub.elections.utils;

import com.stately.common.api.MessageResolvable;

/**
 *
 * @author Edwin Kwame Amoakwa
 * @email edwin.amoakwa@gmail.com
 * @contact 0277115150
 */
public enum FileResourceType implements MessageResolvable
{
    IMAGE("Image","Image"),
    VIDEO("Video","Video"),
    PDF("PDf","PDF");

    private final String code;
    private final String label;
    
    private FileResourceType(String code, String label)
    {
        this.code = code;
        this.label = label;
    }

    public String getCode()
    {
        return code;
    }

    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return label;
    }
    
    
    
    

}
