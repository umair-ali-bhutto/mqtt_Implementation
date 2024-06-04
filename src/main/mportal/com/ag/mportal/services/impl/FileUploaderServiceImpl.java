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
import com.ag.mportal.services.FileUploaderService;

@Service
public class FileUploaderServiceImpl implements FileUploaderService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insertRecords(FileUploader tcn) {
		try {
			entityManager.persist(tcn);
			return tcn.getId();
		} catch (NoResultException nre) {
			return 0;
		}
	}

	@Override
	public List<FileUploader> fetchListByType(String[] type,String corpId) {
		// TODO Auto-generated method stub
		Query cb = null;
		List<FileUploader> lstFiles = null;
		try {
			cb = entityManager.createNamedQuery("FileUploader.fetchByType");
			cb.setParameter("type", Arrays.asList(type));
			cb.setParameter("corpId", corpId);
			lstFiles = (List<FileUploader>) cb.getResultList();

			if (!Objects.isNull(lstFiles) && lstFiles.size() > 0) {
				return lstFiles;
			} else {
				return null;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING FILE UPLOADER fetchListByType: ", ex);
			return null;
		}

	}

	@Override
	public List<FileUploader> fetchListByTypeAndStatus(String[] type, String status,String corpId) {
		// TODO Auto-generated method stub
		Query cb = null;
		List<FileUploader> lstFiles = null;
		try {
			cb = entityManager.createNamedQuery("FileUploader.fetchByTypeAndStatus");
			cb.setParameter("type", Arrays.asList(type));
			cb.setParameter("status", status);
			cb.setParameter("corpId", corpId);
			
			lstFiles = (List<FileUploader>) cb.getResultList();

			if (!Objects.isNull(lstFiles) && lstFiles.size() > 0) {
				return lstFiles;
			} else {
				return null;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING FILE UPLOADER fetchListByTypeAndStatus: ", ex);
			return null;
		}

	}
	
	@Override
	public FileUploader fetctByTypeStatus(String type, String corpId) {
		// TODO Auto-generated method stub
		Query cb = null;
		FileUploader lstFiles = null;
		try {
			cb = entityManager.createNamedQuery("FileUploader.fetchByTypeStatus");
			cb.setParameter("type", type);
			cb.setParameter("corpId", corpId);
			
			lstFiles = (FileUploader) cb.getSingleResult();

			if (!Objects.isNull(lstFiles)) {
				return lstFiles;
			} else {
				return null;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING FILE UPLOADER fetchListByTypeAndStatus: ", ex);
			return null;
		}

	}

	@Override
	public FileUploader fetchById(long id,String corpId) {
		// TODO Auto-generated method stub
		Query cb = null;
		List<FileUploader> lstFiles = null;
		try {
			cb = entityManager.createNamedQuery("FileUploader.fetchById");
			cb.setParameter("id", id);
			cb.setParameter("corpId", corpId);

			lstFiles = (List<FileUploader>) cb.getResultList();

			if (!Objects.isNull(lstFiles) && lstFiles.size() > 0) {
				return lstFiles.get(0);
			} else {
				return null;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING FILE UPLOADER fetchById: ", ex);
			return null;
		}
	}
	
	@Override
	public FileUploader checkStatus() {
		// TODO Auto-generated method stub
		Query cb = null;
		List<FileUploader> lstFiles = null;
		try {
			cb = entityManager.createNamedQuery("FileUploader.fetchByStatus");

			lstFiles = (List<FileUploader>) cb.getResultList();

			if (!Objects.isNull(lstFiles) && lstFiles.size() > 0) {
				return lstFiles.get(0);
			} else {
				return null;
			}
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FECTCHING FILE UPLOADER fetchById: ", ex);
			return null;
		}
	}

	@Transactional
	@Override
	public boolean update(FileUploader fileUploader) {
		// TODO Auto-generated method stub
		boolean flag  = false;
		try {
			entityManager.merge(fileUploader);
			flag = true;
		}catch(Exception ex) {
			AgLogger.logerror(getClass(), "EXCEPTION WHILE FILE UPLOADER update: ", ex);
		}
		return flag;
	}
	
	

}
