package org.xsmart.service;

import org.xsmart.system.annotation.Service;
import org.xsmart.system.core.DatabaseManager;
import org.xsmart.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    public List<User> list(){
        String sql = "select * from user";
        return DatabaseManager.queryEntityList(sql, User.class);
    }

    public boolean add(String name,String phone){
        Map<String,Object> fieldValueMap= new HashMap<>();
        fieldValueMap.put("name",name);
        fieldValueMap.put("phone",phone);
        return DatabaseManager.insertEntity(User.class,fieldValueMap);

    }

}
