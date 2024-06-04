package com.ag.fuel.services;

import java.util.List;

import com.ag.fuel.entity.MasFleetCoInfo;

public interface MasFleetCoInfoService {
	
	public Boolean insert(MasFleetCoInfo masfleetcoinfo);
	public Boolean update(MasFleetCoInfo masfleetcoinfo);
	public List<MasFleetCoInfo> findAll();
    public Boolean insertList(List<MasFleetCoInfo> lst);
    public MasFleetCoInfo findById(Integer id); 

}
