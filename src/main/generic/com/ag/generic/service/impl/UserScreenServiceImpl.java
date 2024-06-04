package com.ag.generic.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.ScreenDeclarModel;
import com.ag.generic.model.WsMenuModel;
import com.ag.generic.service.UserScreenService;

@Service
public class UserScreenServiceImpl implements UserScreenService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addNewScreen(UserScreen UserScreen) {
		try {
			entityManager.persist(UserScreen);
		} catch (NoResultException nre) {

		}
	}

	@Override
	public UserScreen viewUserScreen(int screenId, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserScreen.viewUserScreens").setParameter("screenId", screenId)
					.setParameter("corpId", corpId);
			return (UserScreen) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserScreen> getUserScreens(String screenDesc, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserScreen.getUserScreens")
					.setParameter("screenDesc", screenDesc).setParameter("corpId", corpId);
			return (List<UserScreen>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserScreen> getAllScreens(String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserScreen.getAllScreens");
			cb.setParameter("corpId", corpId);
			return (List<UserScreen>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@Transactional
	public UserScreen updateScreen(UserScreen UserScreen) {
		try {
			return entityManager.merge(UserScreen);
		} catch (NoResultException nre) {
			return null;
		}

	}

	public int getScreencode() {
		try {
			Query cb = entityManager.createNamedQuery("UserScreen.getMaxScreenCode");
			return Integer.parseInt(cb.getSingleResult().toString());
		} catch (NoResultException nre) {
			return (Integer) null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScreenDeclarModel> getUserScreensAllConverted(String corpId) {
		try {
			List<UserScreen> userScreen = null;
			Map<String, ScreenDeclarModel> screens = null;
			userScreen = new ArrayList<UserScreen>();
			screens = new LinkedHashMap<String, ScreenDeclarModel>();
			Query query = entityManager.createNamedQuery("UserScreen.getAllScreens");
			query.setParameter("corpId", corpId);
			userScreen = query.getResultList();
			if (userScreen != null) {
				for (UserScreen userS : userScreen) {
					if (userS.getFileName() != null) {
						if (!userS.getFileName().equals("scr_force_login.xhtml")) {
							screens.put(String.valueOf(userS.getScreenId()),
									new ScreenDeclarModel(String.valueOf(userS.getScreenId()), userS.getScreenDesc()));
						}
					}
				}
			}
			return new ArrayList<ScreenDeclarModel>(screens.values());
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WsMenuModel> screenRights(int groupCode, String corpId, int subSegment) {
		try {
			List<WsMenuModel> lst = new ArrayList<WsMenuModel>();
			Query queryParent = entityManager.createNamedQuery("UserScreen.screenRightsParent");
			queryParent.setParameter("corpId", corpId);
			queryParent.setParameter("subSegment", subSegment);

			List<UserScreen> objParent = queryParent.getResultList();

			Query query = entityManager.createNamedQuery("UserScreen.screenRights");
			query.setParameter("grpId", groupCode);
			query.setParameter("corpId", corpId);
			query.setParameter("subSegment", subSegment);
			List<UserScreen> obj = query.getResultList();

			WsMenuModel mdlSub;
			for (UserScreen um : objParent) {
				mdlSub = new WsMenuModel();
				mdlSub.setId(um.getScreenId());
				mdlSub.setParentId(um.getParentId());
				mdlSub.setMenuName(um.getScreenDesc());
				mdlSub.setRouterLink(um.getRouterLink());
				mdlSub.setIcon(um.getIcon());
				lst.add(mdlSub);
				for (UserScreen umChild : obj) {
					// AgLogger.logInfo(umChild.getParentId() + "|" + um.getScreenId());
					if (umChild.getParentId() == um.getScreenId()) {
						mdlSub = new WsMenuModel();
						mdlSub.setId(umChild.getScreenId());
						mdlSub.setParentId(umChild.getParentId());
						mdlSub.setMenuName(umChild.getScreenDesc());
						mdlSub.setRouterLink(umChild.getRouterLink());
						mdlSub.setIcon(umChild.getIcon());
						lst.add(mdlSub);
					}
				}
			}
			return lst;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserScreen getParentID(int screenId, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserScreen.getParentMenuId").setParameter("screenId", screenId)
					.setParameter("corpId", corpId);
			return (UserScreen) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	public UserScreen fetchScreenIdByRoute(String route, String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserScreen.fetchByRoute").setParameter("routerLink", route)
					.setParameter("corpId", corpId);
			return (UserScreen) cb.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}