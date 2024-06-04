package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DevAdvTerminal;

public interface DevAdvTerminalService {
	public List<DevAdvTerminal> fetchAll();

	public boolean insert(DevAdvTerminal DevAdvTerminal);

	public void update(DevAdvTerminal DevAdvTerminal);

}
