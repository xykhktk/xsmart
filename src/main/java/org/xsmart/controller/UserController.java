package org.xsmart.controller;

import org.xsmart.system.annotation.Controller;
import org.xsmart.system.annotation.GetMapping;
import org.xsmart.system.annotation.PostMapping;
import org.xsmart.system.annotation.RequestMapping;
import org.xsmart.system.entity.Data;
import org.xsmart.model.User;
import org.xsmart.service.UserService;
import org.xsmart.system.util.RequestParams;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @PostMapping(value = "/userList")
    public Data list(RequestParams requestParams) {
        UserService userService = new UserService();
        List<User> list = userService.list();
        return Data.success("获取列表成功").putData("list",list);
    }

    @PostMapping(value = "/userInfo")
    public Data info(RequestParams requestParams) {
        long id = Long.parseLong ((String) requestParams.getParams().get("id"));
        UserService userService = new UserService();
        User userInfo = userService.info(id);
        if(userInfo == null){
            return Data.error("获取详情失败");
        }
        return Data.success("获取详情成功").putData("info",userInfo);
    }

    @PostMapping(value = "/userAdd")
    public Data add(RequestParams requestParams){
        String name = (String)requestParams.getParams().get("name");
        String phone = (String)requestParams.getParams().get("phone");

        UserService userService = new UserService();
        boolean result = userService.add(name,phone);
        if(!result){
            return Data.error("添加失败");
        }
        return Data.success("添加成功");
    }

    @PostMapping(value = "/userUpdate")
    public Data update(RequestParams requestParams) {
        long id = Long.parseLong ((String) requestParams.getParams().get("id"));
        String name = (String) requestParams.getParams().get("name");
        String phone = (String) requestParams.getParams().get("phone");

        UserService userService = new UserService();
        boolean result = userService.update(id, name, phone);
        if(!result){
            return Data.error("更新失败");
        }
        return Data.success("更新成功");
    }

    @PostMapping(value = "/userDelete")
    public Data delete(RequestParams requestParams) {
        long id = Long.parseLong ((String) requestParams.getParams().get("id"));

        UserService userService = new UserService();
        boolean result = userService.delete(id);
        if(!result){
            return Data.error("删除失败");
        }
        return Data.success("删除成功");
    }

}
