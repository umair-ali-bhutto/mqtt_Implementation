package com.ag.mportal.services.impl;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ag.mportal.services.PreauthTerminalService;

@Service
public class PreauthTerminalServiceImpl implements PreauthTerminalService {
	@Override
	public void insert() {

		try {
		} catch (NoResultException nre) {
		}
	}

	@Override
	@Transactional
	public void update() {

		try {
		} catch (NoResultException nre) {
		}

	}
}
