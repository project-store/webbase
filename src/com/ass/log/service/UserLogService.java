package com.ass.log.service;

import com.ass.common.generated.model.TUser;

public interface UserLogService {

	public void addUser(TUser user);

	public void editUser(TUser user);
}