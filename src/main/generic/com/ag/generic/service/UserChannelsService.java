package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.UserChannel;



public interface UserChannelsService {
	public long insert(UserChannel tcn);
	public UserChannel fetchByID(UserChannel tcn);
	public UserChannel fetchByOtherParams(UserChannel tcn);
	public List<UserChannel> fetchAll();
	public List<UserChannel> fetchAllByID(int id);
	public void update(UserChannel id);
	
	public void delete(int id);


}