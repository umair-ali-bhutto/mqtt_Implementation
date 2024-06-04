package com.ag.fuel.services;

import java.util.List;

import com.ag.fuel.entity.MasFleetBill;

public interface MasFleetBillService {
	
	public Boolean insert(MasFleetBill masfleetbill);
	public Boolean update(MasFleetBill masfleetbill);
	public List<MasFleetBill> findAll();
    public Boolean insertList(List<MasFleetBill> lst);
    public MasFleetBill findById(Integer id); 

}
