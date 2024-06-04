package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.FileUploader;

public interface FileUploaderService {

	public long insertRecords(FileUploader tcn);
	public List<FileUploader> fetchListByTypeAndStatus(String[] type, String status,String corpId);
	public List<FileUploader> fetchListByType(String[] type,String corpId);
	public FileUploader fetchById(long id,String corpId);
	public boolean update(FileUploader fileUploader);
	public FileUploader checkStatus();
	public FileUploader fetctByTypeStatus(String type, String corpId);
}
