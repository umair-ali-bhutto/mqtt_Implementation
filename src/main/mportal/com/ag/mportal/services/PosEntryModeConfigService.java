package com.ag.mportal.services;

import java.util.ArrayList;

import com.ag.mportal.entity.PosEntryModeConfig;

public interface PosEntryModeConfigService {

		public PosEntryModeConfig fetchByMode(String posEntryMode);
		public ArrayList<PosEntryModeConfig> fetchAll();
}
