package com.ag.generic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.service.LovService;
import com.ag.mportal.model.DisplayModel;

@Service
public class LovServiceImpl implements LovService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<LovMaster> fetchLovs(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("Lov.fetchLovs").setParameter("isActive", 1)
					.setParameter("corpId", corpId);
			return (List<LovMaster>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LovDetail> fetchLovsDetailsAll(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("LovDetail.findAll").setParameter("isActive", 1)
					.setParameter("corpId", corpId);
			return (List<LovDetail>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public LovMaster fetchLovsbyId(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("LovMaster.fetchLovsbyId").setParameter("id", id)
					.setParameter("isActive", 1).setParameter("corpId", corpId);
			return (LovMaster) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LovDetail> fetchLovsDetails(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("LovDetail.fetchLovsDetails").setParameter("lovId", id)
					.setParameter("isActive", 1).setParameter("corpId", corpId);
			return (List<LovDetail>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public LovDetail fetchLovsDetailsbyId(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("LovDetail.fetchLovsDetailsbyId").setParameter("lovId", id)
					.setParameter("isActive", 1).setParameter("corpId", corpId);
			return (LovDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public LovDetail fetchLovsDetailsByLovIDandLovDetailID(String id, String lovId, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("LovDetail.fetchLovsDetailsByLovIDandLovDetailID")
					.setParameter("id", id).setParameter("isActive", 1).setParameter("id", lovId)
					.setParameter("corpId", corpId);
			return (LovDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public LovDetail fetchLovsDetailsbyLovId(String id, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("LovDetail.fetchLovsDetailsbyLovId").setParameter("id", id)
					.setParameter("isActive", 1).setParameter("corpId", corpId);
			return (LovDetail) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<DisplayModel> fetchLov(String tableName, String columName) {
		try {
			List<DisplayModel> dslIst = new ArrayList<DisplayModel>();
			String sql = "Select d.ret_code RetValue, d.dsp_value DisplayName from LOVS_MASTER m, LOVS_DETAIL d where d.LOV_ID=m.LOV_ID and m.TABLE_NM='"
					+ tableName + "' and m.COLMN_NM='" + columName
					+ "' and nvl(m.ACTIVE,'N')='Y' and nvl(d.ACTIVE,'N')='Y'";
			Query cb = entityManager.createNativeQuery(sql);
			List<Object[]> obj = (List<Object[]>) cb.getResultList();
			if (obj.size() != 0) {
				dslIst = new ArrayList<DisplayModel>();
				for (Object[] ok : obj) {
					DisplayModel dms = new DisplayModel();
					dms.setKey((String) ok[0]);
					dms.setValue((String) ok[1]);
					dslIst.add(dms);
				}
			}
//			DisplayModel d = new DisplayModel();
//			d.setKey("000000");
//			d.setValue("TEMP VALUE");
//			
//			dslIst.add(d);
			
			return dslIst;

		} catch (NoResultException nre) {
			return null;
		}
	}

}
