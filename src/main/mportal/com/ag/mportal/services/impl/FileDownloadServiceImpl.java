package com.ag.mportal.services.impl;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.prop.AppProp;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.FileDownload;
import com.ag.mportal.services.FileDownloadService;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public FileDownload fetchById(Long id) {
		FileDownload fileDownload = null;

		try {
			Query query = entityManager.createNamedQuery("FileDownload.fetchById").setParameter("id", id);

			fileDownload = (FileDownload) query.getSingleResult();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		}

		if (!Objects.isNull(fileDownload)) {
			return fileDownload;
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public Long insert(FileDownload fileDownload) {
		try {
			entityManager.persist(fileDownload);
			return fileDownload.getId();
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Exception", ex);
		}
		return null;
	}

	@Override
	public List<FileDownload> fetchAll() {

		List<FileDownload> lstFileDownload = null;

		try {
			Query query = entityManager.createNamedQuery("FileDownload.fetchAll");

			lstFileDownload = (List<FileDownload>) query.getResultList();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		}

		if (!lstFileDownload.isEmpty()) {
			return lstFileDownload;
		} else {
			return null;
		}
	}

	@Override
	public FileDownload fetchByVersion(String version) {

		List<FileDownload> lstFileDownload = null;

		try {
			Query query = entityManager.createNamedQuery("FileDownload.fetchByVersion").setParameter("version",
					version);

			lstFileDownload = (List<FileDownload>) query.getResultList();
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		}

		if (!lstFileDownload.isEmpty()) {
			return lstFileDownload.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<FileDownload> fetchAll(String releaseType, String status, String version, String terminalId,
			String dateFrom, String dateTo) {
		// TODO Auto-generated method stub
		List<FileDownload> lstFileDownload = null;

		try {
			String whereClause = "";
			String queryString = AppProp.getProperty("select.file.download");

			if (!Objects.isNull(version)) {
				whereClause += " VERSION='" + version + "' ";
			}

			if (!Objects.isNull(terminalId)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " TERMINAL_IDS like '%" + terminalId + "%' ";
			}

			if (!Objects.isNull(dateTo) && !Objects.isNull(dateFrom)) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}

				whereClause += " ENTRY_DATE BETWEEN '" + UtilAccess.dateFormatter(dateFrom) + " 00:00:00.0000000"
						+ "' AND '" + UtilAccess.dateFormatter(dateTo) + " 00:00:00.0000000" + "'";
			}

			if (!Objects.isNull(releaseType) && !releaseType.equalsIgnoreCase("all")) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " RELEASE_TYPE='" + releaseType + "'";
			}

			if (!Objects.isNull(status) && !status.equalsIgnoreCase("all")) {
				if (!whereClause.isEmpty()) {
					whereClause += " AND ";
				}
				whereClause += " STATUS='" + status + "'";
			}

			if (!whereClause.isEmpty()) {
				String st = " WHERE " + whereClause;
				queryString = queryString.replace("@whereClause", st);
			} else {
				queryString = queryString.replace("@whereClause", "");
			}

			AgLogger.logInfo(FileDownloadServiceImpl.class, queryString);

			Query query = entityManager.createNativeQuery(queryString, FileDownload.class);

			lstFileDownload = (List<FileDownload>) query.getResultList();

			AgLogger.logInfo(getClass(), "FETCHED LIST FILE DOWNLOAD SIZE: " + lstFileDownload.size());

			if (!Objects.isNull(lstFileDownload) && !lstFileDownload.isEmpty()) {
				return lstFileDownload;
			}

		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
			e.printStackTrace();
		}

		return null;
	}

	@Transactional
	@Override
	public boolean update(FileDownload fileDownload) {
		boolean flag = false;
		try {
			entityManager.merge(fileDownload);
			flag = true;
		} catch (Exception ex) {
			AgLogger.logerror(getClass(), "Exception", ex);
			ex.printStackTrace();
		}

		return flag;
	}

	@Transactional
	@Override
	public boolean deactivePrevApk(Long id, String releaseType) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			Query query = entityManager.createNamedQuery("FileDownload.deactivePrevApk").setParameter("id", id).setParameter("releaseType", releaseType);
			query.executeUpdate();
			flag = true;
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		}
		
		return flag;

	}

	@Override
	public Integer getCountReleaseTypeAndStatusWise(String releaseType, String status) {
		Object obj = null;
		try {
			Query query = entityManager.createNamedQuery("FileDownload.countReleaseTypeAndStatusWise").setParameter("status", status).setParameter("releaseType", releaseType);
			obj = query.getSingleResult();
			return Integer.valueOf(obj.toString());
		} catch (Exception e) {
			AgLogger.logerror(getClass(), "Exception", e);
		}
		return null;
	}

}
