package com.codegym.project.role;

import com.codegym.project.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);

}
