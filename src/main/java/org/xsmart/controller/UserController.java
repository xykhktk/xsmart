package org.xsmart.controller;

import org.xsmart.bean.Data;
import org.xsmart.model.User;
import org.xsmart.service.UserService;

import java.util.List;

public class UserController {

    public Data list(){
        UserService userService =  new UserService();
        List<User> list = userService.list();
        Data data = new Data();
        data.setModel(list);
        return data;
    }

}
