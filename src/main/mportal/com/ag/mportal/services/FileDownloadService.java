package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.FileDownload;

public interface FileDownloadService {

	public Long insert(FileDownload fileDownload);
	
	public List<FileDownload> fetchAll();
	
	public FileDownload fetchByVersion(String version);
	
	public List<FileDownload> fetchAll(String releaseType,String status,String version,String terminalId, String dateFrom,String dateTo);
	
	public FileDownload fetchById(Long id);
	
	public boolean update(FileDownload fileDownload);
	
	public boolean deactivePrevApk(Long id,String releaseType);
	
	public Integer getCountReleaseTypeAndStatusWise(String releaseType,String status);
}
