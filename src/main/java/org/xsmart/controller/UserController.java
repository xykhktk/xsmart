package org.xsmart.controller;

import org.xsmart.system.annotation.Action;
import org.xsmart.system.annotation.Controller;
import org.xsmart.system.entity.Data;
import org.xsmart.model.User;
import org.xsmart.service.UserService;
import org.xsmart.system.util.RequestParams;

import java.util.List;

@Controller
public class UserController {

    @Action(value = "get:/userList")
    public Data list(RequestParams requestParams) {
        UserService userService = new UserService();
        List<User> list = userService.list();
        Data data = new Data();
        data.setModel(list);
        return data;
    }

    @Action(value = "post:/userAdd")
    public Data add(RequestParams requestParams){
        String name = (String)requestParams.getParams().get("name");
        String phone = (String)requestParams.getParams().get("phone");

        UserService userService = new UserService();
        boolean result = userService.add(name,phone);
        Data data = new Data();
        return data;
    }

}
