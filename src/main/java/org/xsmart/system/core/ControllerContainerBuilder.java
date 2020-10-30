package org.xsmart.system.core;

import org.xsmart.system.annotation.GetMapping;
import org.xsmart.system.annotation.PostMapping;
import org.xsmart.system.annotation.RequestMapping;
import org.xsmart.system.entity.Handler;
import org.xsmart.system.entity.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ControllerContainerBuilder {

    private static Map<Request, Handler> REQUEST_HANDLER_MAP = new HashMap<>();

    public void init(){
        HashSet<Class<?>> classSet = ClassContainerBuilder.getControllerClass();
        if (!classSet.isEmpty()) {
            for (Class<?> clz : classSet) {
                Method[] classMethods = clz.getMethods();
                if (classMethods.length == 0) continue;

                String classRequestPath = "";
                if (clz.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = clz.getAnnotation(RequestMapping.class);
                    classRequestPath = requestMapping.value();
                }

                for (Method method : classMethods) {

                    String requestMethod = "";
                    String requestPath = "";

                    if (method.isAnnotationPresent(PostMapping.class)) {
                        requestMethod = "post";
                        PostMapping methodMapping = method.getAnnotation(PostMapping.class);
                        requestPath = methodMapping.value();
                    } else if (method.isAnnotationPresent(GetMapping.class)) {
                        requestMethod = "get";
                        GetMapping methodMapping = method.getAnnotation(GetMapping.class);
                        requestPath = methodMapping.value();
                    } else {
                        continue;
                    }

                    requestPath = classRequestPath + requestPath;

                    Request request = new Request(requestMethod, requestPath);
                    Handler handler = new Handler(clz, method);
                    REQUEST_HANDLER_MAP.put(request, handler);
                }

            }

        }
    }

    public static Map<Request, Handler> getRequestHandlerMap(){
        return REQUEST_HANDLER_MAP;
    }

    public static Handler getHandler(Request request){
        return REQUEST_HANDLER_MAP.get(request);
    }

}
