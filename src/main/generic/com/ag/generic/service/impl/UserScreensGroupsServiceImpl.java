package com.ag.generic.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.generic.entity.UserScreensGroup;
import com.ag.generic.service.UserScreensGroupsService;

@Service
public class UserScreensGroupsServiceImpl implements UserScreensGroupsService {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void insertScreenGroup(UserScreensGroup userScreensGroup) {
		try {
		entityManager.persist(userScreensGroup);
		} catch (NoResultException nre) {
	
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserScreensGroup> getAllScreensGroups(String corpId) {
		try {
		Query cb = entityManager.createNamedQuery("UserScreensGroup.findAll");
		cb.setParameter("corpId", corpId);
		return  (List<UserScreensGroup>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserScreensGroup> viewScrnGroup(int groupId, String corpId) {
		try {
		Query cb = entityManager.createNamedQuery("UserScreensGroup.showScreenGroup").setParameter("grpId", groupId);
		cb.setParameter("corpId", corpId);
		return (List<UserScreensGroup>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserScreensGroup> viewScreenGroupByScreen(int screenId, String corpId) {
		try {
		Query cb = entityManager.createNamedQuery("UserScreensGroup.viewScreenGroupByScreen").setParameter("screenId", screenId);
		cb.setParameter("corpId", corpId);
		return (List<UserScreensGroup>) cb.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	@Transactional
	public UserScreensGroup updateScreenGroup(UserScreensGroup UserScreensGroup) {
		try {
		return entityManager.merge(UserScreensGroup);
		} catch (NoResultException nre) {
			return null;
		}
	}
	
	
	
	
	
	@Transactional
	public String deleteRecord(int groupId,String corpId) {
		try {
			Query cb = entityManager.createNamedQuery("UserScreensGroup.deleteRecord").setParameter("grpId", groupId);
			cb.setParameter("corpId", corpId);
			cb.executeUpdate();
			return "Success";
		} catch (NoResultException nre) {
			return "";
		}
	}
	
	

}