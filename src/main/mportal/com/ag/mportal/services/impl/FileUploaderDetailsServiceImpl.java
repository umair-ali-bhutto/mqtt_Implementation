package com.ag.mportal.services.impl;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.assertj.core.util.Arrays;
import org.springframework.stereotype.Service;

import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.FileUploader;
import com.ag.mportal.entity.FileUploaderDetails;
import com.ag.mportal.services.FileUploaderDetailsService;

@Service
public class FileUploaderDetailsServiceImpl implements FileUploaderDetailsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void insertRecords(FileUploaderDetails tcn) {
		try {
			entityManager.persist(tcn);
		} catch (NoResultException nre) {

		}
	}

	@Transactional
	@Override
	public boolean update(FileUploaderDetails fileUploader) {
		boolean flag = false;
		try {
			entityManager.merge(fileUploader);
			flag = true;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FILE UPLOADER DETAILS update: ", ex);
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileUploaderDetails> fetchListFileId(String fileId) {
		Query cb = null;
		List<FileUploaderDetails> lstFiles = null;
		try {
			cb = entityManager.createNamedQuery("FileUploaderDetails.fetchByFileId");
			cb.setParameter("fileId", fileId);
			lstFiles = (List<FileUploaderDetails>) cb.getResultList();

			if (!Objects.isNull(lstFiles) && lstFiles.size() > 0) {
				return lstFiles;
			} else {
				return null;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING FILE UPLOADER fetchListFileId: ", ex);
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileUploaderDetails> fetchListFileIdStatus(String fileId) {
		Query cb = null;
		List<FileUploaderDetails> lstFiles = null;
		try {
			cb = entityManager.createNamedQuery("FileUploaderDetails.fetchByFileIdStatus");
			cb.setParameter("fileId", fileId);
			lstFiles = (List<FileUploaderDetails>) cb.getResultList();

			if (!Objects.isNull(lstFiles) && lstFiles.size() > 0) {
				return lstFiles;
			} else {
				return null;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING FILE UPLOADER fetchListFileId: ", ex);
			return null;
		}

	}

}
