package com.ag.mportal.model;

public class FileDownloadModel {

	private int id;
	private String fileName;
	private String fileChecksum;
	private String entryDate;
	private int isActive;
	private String version;
	private String filePath;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileChecksum() {
		return fileChecksum;
	}
	public void setFileChecksum(String fileChecksum) {
		this.fileChecksum = fileChecksum;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
