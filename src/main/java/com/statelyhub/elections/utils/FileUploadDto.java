/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template fileString, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statelyhub.elections.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @author Edwin
 */
public class FileUploadDto {

  public FileUploadDto() {}

  public FileUploadDto(String fileString, String fileName) {
    this.fileString = fileString;
    this.fileName = fileName;
  }

  private String fileString;
  private String fileFormat;
  private String fileName;

  public File createFile() 
  {
    try 
    {
        
        
//      String before = fileString.substring(0, fileString.indexOf(","));
      
        String after = fileString;
        
    
       
       if(fileString.contains(","))
       {
              after = fileString.substring(fileString.indexOf(",") + 1);
       }

      byte[] bytes = Base64.getDecoder().decode(after);
      File file = new File(Astra.TEMP_DIR, fileName);
      
        System.out.println("full file ..... " + file);

        // Starts writing the bytes in it
        try (OutputStream os = new FileOutputStream(file)) {
            // Starts writing the bytes in it
            os.write(bytes);
            System.out.println("Successfully byte inserted");
            // Close the file
        }

        
        

      return file;
    } catch (Exception exception) 
    {
        exception.printStackTrace();
    }
    return null;
  }

  public static File createFile(String fileBase64, String fileName) {
    return new FileUploadDto(fileBase64, fileName).createFile();
  }

  public String getFileString() {
    return fileString;
  }

  public void setFileString(String fileString) {
    this.fileString = fileString;
  }

  public String getFileFormat() {
    return fileFormat;
  }

  public void setFileFormat(String fileFormat) {
    this.fileFormat = fileFormat;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
