/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.utils;

import com.stately.common.api.MessageResolvable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Edwin
 */
public enum FileType implements MessageResolvable
{
    PDF("PDF",".pdf"),
    IMAGE_PNG("PNG",".png"),
    IMAGE_JPG("JPG",".jpg"),
    IMAGE_JPEG("JPEG",".jpeg"),
    WORD_DOC("WORD",".docx"),
    CSV("CSV",".csv");
    
    
    public static final List<FileType> IMAGES = Arrays.asList(IMAGE_JPEG, IMAGE_JPG, IMAGE_PNG);
    
    private final String label;
    private final String code;

    private FileType(String code,String label)
    {
        this.label = label;
        this.code = code;
    }

    public static boolean isImage(FileType fileType)
    {
        return IMAGES.contains(fileType);
    }
    
    
    public static FileType getType(String extension)
    {
       for(FileType fileType : FileType.values())
       {
           if(fileType.getLabel().toLowerCase().contains(extension.toLowerCase()))
           {
               return fileType;
           }
       }
       
       return null;
    }
    
    
    
    
    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public String getCode()
    {
        return code;
    }
    
    @Override
    public String toString()
    {
        return label;
    }
    
}
