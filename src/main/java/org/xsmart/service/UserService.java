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

    public User info(long id){
        String sql = "select * from user where id = " + id;
        return DatabaseManager.queryEntity(sql, User.class);
    }

    public boolean add(String name,String phone){
        Map<String,Object> fieldValueMap= new HashMap<>();
        fieldValueMap.put("name",name);
        fieldValueMap.put("phone",phone);
        return DatabaseManager.insertEntity(User.class,fieldValueMap);

    }

    public boolean update(long id, String name, String phone) {
        Map<String, Object> fieldValueMap = new HashMap<>();
        fieldValueMap.put("name", name);
        fieldValueMap.put("phone", phone);
        return DatabaseManager.updateEntityById(User.class, fieldValueMap, id);
    }

    public boolean delete(long id) {
        return DatabaseManager.deleteEntityById(User.class, id);
    }

}
