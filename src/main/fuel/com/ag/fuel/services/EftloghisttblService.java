package com.ag.fuel.services;

import java.util.List;

import com.ag.fuel.entity.Eftloghisttbl;

public interface EftloghisttblService {
	
	public Boolean insert(Eftloghisttbl eftloghisttbl);
	public Boolean update(Eftloghisttbl eftloghisttbl);
	public List<Eftloghisttbl> findAll();
    public Boolean insertList(List<Eftloghisttbl> lst);
    public Eftloghisttbl findById(Integer id); 

}
