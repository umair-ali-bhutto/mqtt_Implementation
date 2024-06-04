package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.FileUploaderDetails;

public interface FileUploaderDetailsService {

	public void insertRecords(FileUploaderDetails tcn);
	public boolean update(FileUploaderDetails fileUploader);
	public List<FileUploaderDetails> fetchListFileId(String fileId);
	public List<FileUploaderDetails> fetchListFileIdStatus(String fileId);
}
