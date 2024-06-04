package com.ag.fuel.services;

import java.util.List;

import com.ag.fuel.entity.DetlFleetCard;

public interface DetlFleetCardService {
	
	public Boolean insert(DetlFleetCard detlfleetcard);
	public Boolean update(DetlFleetCard detlfleetcard);
	public List<DetlFleetCard> findAll();
    public Boolean insertList(List<DetlFleetCard> lst);
    public DetlFleetCard findById(Integer id); 

}
