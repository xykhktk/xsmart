package org.xsmart.service;

import org.xsmart.core.helper.DatabaseHelper;
import org.xsmart.model.User;

import java.util.List;

public class UserService {

    public List<User> list(){
        String sql = "select * from user";
        return DatabaseHelper.queruEntityList(sql, User.class);
    }

}
