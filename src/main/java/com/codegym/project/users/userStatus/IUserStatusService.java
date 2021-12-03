package com.codegym.project.users.userStatus;

import com.codegym.project.IGeneralService;

public interface IUserStatusService extends IGeneralService<UserStatus> {
    UserStatus findByName(String name);

}
