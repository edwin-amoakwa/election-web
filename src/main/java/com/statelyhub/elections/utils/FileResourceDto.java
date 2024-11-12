package com.statelyhub.elections.utils;

import com.statelyhub.elections.dto.BaseDto;
import jakarta.persistence.Transient;

/**
 * @author Edwin
 */
public class FileResourceDto extends BaseDto {

  private FileUploadDto fileUpload;

  private String fileName;

  private String extension;

  private String fileUrl;

  private String folder;

  private String relatedEntityId;

  private String description;
  private EntityType entityType;

  private String fileLocation;
  
  private String fileNameOnDisk;
  private FileResourceType resourceType;
  
  private FileType fileType;
  private String base64;

  @Transient private String tempFileLocation;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getRelatedEntityId() {
    return relatedEntityId;
  }

  public void setRelatedEntityId(String relatedEntityId) {
    this.relatedEntityId = relatedEntityId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public void setEntityType(EntityType entityType) {
    this.entityType = entityType;
  }

  public String getFileLocation() {
    return fileLocation;
  }

  public void setFileLocation(String fileLocation) {
    this.fileLocation = fileLocation;
  }

  public String getFileNameOnDisk() {
    return fileNameOnDisk;
  }

  public void setFileNameOnDisk(String fileNameOnDisk) {
    this.fileNameOnDisk = fileNameOnDisk;
  }

  public FileResourceType getResourceType() {
    return resourceType;
  }

  public void setResourceType(FileResourceType resourceType) {
    this.resourceType = resourceType;
  }

  public String getTempFileLocation() {
    return tempFileLocation;
  }

  public void setTempFileLocation(String tempFileLocation) {
    this.tempFileLocation = tempFileLocation;
  }

  public String getFolder() {
    return folder;
  }

  public void setFolder(String folder) {
    this.folder = folder;
  }

  public FileUploadDto getFileUpload() {
    return fileUpload;
  }

  public void setFileUpload(FileUploadDto fileUpload) {
    this.fileUpload = fileUpload;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
  
  
}
