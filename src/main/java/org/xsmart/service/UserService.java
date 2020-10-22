package org.xsmart.service;

import org.xsmart.system.annotation.Service;
import org.xsmart.system.core.DatabaseManager;
import org.xsmart.model.User;

import java.util.List;

@Service
public class UserService {

    public List<User> list(){
        String sql = "select * from user";
        return DatabaseManager.queryEntityList(sql, User.class);
    }

}
