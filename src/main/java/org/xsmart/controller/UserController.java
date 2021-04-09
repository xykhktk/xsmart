package org.xsmart.controller;

import org.apache.log4j.Logger;
import org.xsmart.enhance.annotation.Log1;
import org.xsmart.enhance.annotation.Log2;
import org.xsmart.system.annotation.*;
import org.xsmart.system.entity.Data;
import org.xsmart.model.User;
import org.xsmart.service.UserService;
import org.xsmart.system.util.RequestParams;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Controller
@Log1
@Log2
@RequestMapping(value = "/user")
public class UserController {

    @Autowire
    private UserService userService;

    private Logger logger = Logger.getLogger(UserController.class);

    @GetMapping(value = "/test")
    public void test(RequestParams requestParams) {
        logger.debug("----- test ----- ");
    }

    @PostMapping(value = "/userList")
    public Data list(RequestParams requestParams) {
        UserService userService2 = userService;
        ReentrantLock s;
        List<User> list = userService.list();
        return Data.success("获取列表成功").putData("list",list);
    }

    @PostMapping(value = "/userInfo")
    public Data info(RequestParams requestParams) {
        long id = Long.parseLong ((String) requestParams.getParams().get("id"));
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

        boolean result = userService.update(id, name, phone);
        if(!result){
            return Data.error("更新失败");
        }
        return Data.success("更新成功");
    }

    @PostMapping(value = "/userDelete")
    public Data delete(RequestParams requestParams) {
        long id = Long.parseLong ((String) requestParams.getParams().get("id"));

        boolean result = userService.delete(id);
        if(!result){
            return Data.error("删除失败");
        }
        return Data.success("删除成功");
    }

}
