package com.ag.generic.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.Complaint;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;

@Service
public class ComplaintsServiceImpl implements ComplaintsService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public long insertComplaint(Complaint rxn) throws Exception {
		try {
			entityManager.persist(rxn);
			return rxn.getId();
		} catch (NoResultException nre) {
			return 0l;
		}
	}

	@Override
	@Transactional
	public void updateComplaint(Complaint rxn) {
		try {
			entityManager.merge(rxn);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public Complaint fetchComplaintById(int complaintId) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchComplaintById");
			Query cb = entityManager.createNamedQuery("Complaint.fetchComplaintById").setParameter("id", complaintId);
			return (Complaint) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public Complaint fetchComplaintById(int complaintId, String mid, String tid) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchComplaintById");
			Query cb = entityManager.createNamedQuery("Complaint.fetchComplaintByIdUpd").setParameter("id", complaintId)
					.setParameter("tid", tid).setParameter("mid", mid);
			return (Complaint) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<Complaint> fetchAllComplaint(String merchant, String terminal, Date from, Date to, String category,
			String region, String city, int assignedTo, int groupCode) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllComplaint");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			List<Complaint> fileUploadProp = null;
			String mQUery = "";
			if (from != null && to != null) {

				mQUery += " AND (" + DBUtil.getDateQueryParam("C.ENTRY_DATE") + " between '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}
			if (merchant != null) {
				mQUery += " AND C.MID = '" + merchant + "' ";
			}

			if (terminal != null) {
				mQUery += " AND C.TID = '" + terminal + "' ";
			}

			if (region != null) {
				mQUery += " AND  U.REGION = '" + region + "' ";
			}

			if (city != null) {
				mQUery += " AND  U.CITY = '" + city + "' ";
			}

			int launchedBy = 0;
			if (groupCode == Integer.parseInt(AppProp.getProperty("super.admin.group.code"))) {
				assignedTo = 0;
			} else {
				boolean b = UtilAccess.isAllAdminGp(groupCode + "");
				if (!b) {
					assignedTo = 0;
					launchedBy = assignedTo;
				}
			}

			if (assignedTo != 0) {
				mQUery += " AND  C.ASSIGNED_TO = " + assignedTo + " ";
			}

			if (launchedBy != 0) {
				mQUery += " AND  C.ENTRY_BY = " + launchedBy + " ";
			}

			// NEE TO CREATE QUERY SELECT * FROM COMPLAINTS WHERE CATEGORY=@PARAMCAT
			String sql = AppProp.getProperty("select.complaints");
			sql = sql.replaceAll("@PARAMCAT", category);
			sql = sql + "" + mQUery;
			sql = sql + " ORDER BY C.ID";

			AgLogger.logDebug(getClass(), sql);
			Query cb = entityManager.createNativeQuery(sql);

			@SuppressWarnings("unchecked")
			List<Object[]> objs = (List<Object[]>) cb.getResultList();
			if (objs.size() != 0) {
				fileUploadProp = new ArrayList<Complaint>();
				for (Object[] o : objs) {
					Complaint c = new Complaint();

					try {
						c.setId(Integer.parseInt(o[0].toString()));
					} catch (Exception e) {
					}
					try {
						c.setMid(o[1].toString());
					} catch (Exception e) {
					}
					try {
						c.setTid(o[2].toString());
					} catch (Exception e) {
					}
					try {
						c.setComplaintType(o[3].toString());
					} catch (Exception e) {
					}
					try {
						c.setComplaintSubType(o[4].toString());
					} catch (Exception e) {
					}
					try {
						c.setCategory(o[5].toString());
					} catch (Exception e) {
					}
					try {
						c.setType(o[6].toString());
					} catch (Exception e) {
					}
					try {
						c.setSubType(o[7].toString());
					} catch (Exception e) {
					}
					try {
						c.setDescription(o[8].toString());
					} catch (Exception e) {
					}
					try {
						c.setModel(o[9].toString());
					} catch (Exception e) {
					}
					try {
						c.setSerialNumber(o[10].toString());
					} catch (Exception e) {
					}
					try {
						c.setMaker(o[11].toString());
					} catch (Exception e) {
					}
					try {
						c.setStatus(o[12].toString());
					} catch (Exception e) {
					}
					try {
						c.setEntryDate(new Timestamp(
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(o[13].toString()).getTime()));
					} catch (Exception e) {
					}
					try {
						c.setEntryBy(o[14].toString());
					} catch (Exception e) {
					}
					try {
						c.setLastUpdated(new Timestamp(
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(o[15].toString()).getTime()));
					} catch (Exception e) {
					}
					try {
						c.setClosureDate(new Timestamp(
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(o[16].toString()).getTime()));
					} catch (Exception e) {
					}
					try {
						c.setClosedBy(o[17].toString());
					} catch (Exception e) {
					}
					try {
						c.setAssignedDate(new Timestamp(
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(o[18].toString()).getTime()));
					} catch (Exception e) {
					}
					try {
						c.setAssignedTo(o[19].toString());
					} catch (Exception e) {
					}
					try {
						c.setAssignedAddress(o[20].toString());
					} catch (Exception e) {
					}
					try {
						c.setAssignedChannel(o[21].toString());
					} catch (Exception e) {
					}
					try {
						c.setAssignedSelfFlag(o[22].toString());
					} catch (Exception e) {
					}
					try {
						c.setResolution(o[23].toString());
					} catch (Exception e) {
					}
					try {
						c.setIssueAddressed(o[24].toString());
					} catch (Exception e) {
					}
					try {
						c.setReasonFailure(o[25].toString());
					} catch (Exception e) {
					}
					try {
						c.setComplaintDesc(o[26].toString());
					} catch (Exception e) {
					}

					fileUploadProp.add(c);
				}

			}

			return fileUploadProp;
		} catch (NoResultException nre) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Complaint> fetchAllComplaintOnlyNew() {
		try {
//		AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllComplaintOnlyNew");
			// ADD OEDER BY CLAUSE ID ASC AND WHERE CLAUSE assignedTo IS NULL
			Query cb = entityManager.createNamedQuery("Complaint.fetchAllComplaintOnlyNew").setParameter("status",
					"NEW");
			return (List<Complaint>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Complaint> fetchAllComplaintNotClosed() {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllComplaintNotClosed");
			// ADD OEDER BY CLAUSE id ASC AND WHERE CLAUSE closureDate IS NULL AND
			// assignedDate IS NOT NULL
			Query cb = entityManager.createNamedQuery("Complaint.fetchAllComplaintNotClosed").setParameter("status",
					"ASSIGNED");
			return (List<Complaint>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Complaint> fetchAllComplaints(String merchant, String terminal, Date from, Date to, String category,
			String assignedTo, String launchedBy) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllComplaint");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String dateClause = "";
			String merchantClause = "";
			String terminalClause = "";
			String assignedLunchedByClause = "";
			if (from != null && to != null) {
				dateClause += " AND (" + DBUtil.getDateQueryParam("c.ENTRY_DATE") + "BETWEEN '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}

			if (merchant != null) {

				merchantClause += " AND C.MID = '" + merchant + "' ";

			}

			if (terminal != null) {

				terminalClause += " AND C.TID = '" + terminal + "' ";

			}

			if (assignedTo != null && launchedBy != null) {
				assignedLunchedByClause += " AND ( ASSIGNED_TO = '" + assignedTo + "' or C.ENTRY_BY = '" + launchedBy
						+ "' )";
			}

			String query = AppProp.getProperty("select.complaints");
			query = query.replaceAll("@PARAMCAT", category);

			if (dateClause.length() != 0) {
				query = query + " " + dateClause;
			}
			if (terminalClause.length() != 0) {
				query = query + " " + terminalClause;
			}
			if (merchantClause.length() != 0) {
				query = query + " " + merchantClause;
			}

			if (assignedLunchedByClause.length() != 0) {
				query = query + " " + assignedLunchedByClause;
			}

			query = query + " ORDER BY c.id DESC";

			AgLogger.logDebug("Complaint Assignment Query :", query);
			Query cb = entityManager.createNativeQuery(query, Complaint.class);
			return (List<Complaint>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Complaint> fetchAllFuelComplaints(String userCode, String complType, String status, int complId,
			Date from, Date to, String category, String assignedTo, String launchedBy) {
		try {
			AgLogger.logDebug(getClass(), "Fetching Rec From fetchAllComplaint");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String dateClause = "";
			String assignedLunchedByClause = "";
			if (from != null && to != null) {
				dateClause += " AND (" + DBUtil.getDateQueryParam("c.ENTRY_DATE") + "BETWEEN '" + sdf.format(from)
						+ "' AND '" + sdf.format(to) + "') ";
			}

			if (assignedTo != null && launchedBy != null) {
				assignedLunchedByClause += " AND ( ASSIGNED_TO = '" + assignedTo + "' or C.ENTRY_BY = '" + launchedBy
						+ "' )";
			}

			String query = AppProp.getProperty("select.complaints");
			query = query.replaceAll("@PARAMCAT", category);

			if (dateClause.length() != 0) {
				query = query + " " + dateClause;
			}
			if (assignedLunchedByClause.length() != 0) {
				query = query + " " + assignedLunchedByClause;
			}
			if (userCode != null) {
				query = query + " AND u.USER_CODE = '" + userCode + "'";
			}
			if (complType != null) {
				query = query + " AND c.COMPLAINT_TYPE = '" + complType + "'";
			}
			if (status != null) {
				query = query + " AND c.STATUS = '" + status + "'";
			}
			if (complId != 0) {
				query = query + " AND c.ID = " + complId;
			}

			query = query + " ORDER BY c.id DESC";

			AgLogger.logDebug("Fuel Complaint Query :", query);
			Query cb = entityManager.createNativeQuery(query, Complaint.class);
			return (List<Complaint>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
