撸一个超轻量的java MVC框架,支持IOC和AOP。

### 注解
#### MVC
目前支持的注解有：`Controller` `GetMapping` `PostMapping` `RequestMapping` `Autowire` `Aspect`  `Service`   
注解命名和用法上基本抄Spring MVC。
```
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowire
    private UserService userService;

    @PostMapping(value = "/userList")
    public Data list(RequestParams requestParams) {
        UserService userService2 = userService;

        List<User> list = userService.list();
        return Data.success("获取列表成功").putData("list",list);
    }
}

@Service
public class UserService {
}
```

#### AOP
如果有某类使用了某注解(例如@Controller)
```
@Controller
public class UserController {
}
```
则可用@Aspect(某注解)对此类进行织入
```
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    //然后可重写 before() after() error() end() 方法
}
```