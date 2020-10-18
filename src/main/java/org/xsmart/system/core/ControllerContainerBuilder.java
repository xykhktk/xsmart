package org.xsmart.system.core;

import org.xsmart.system.annotation.Action;
import org.xsmart.system.entity.Handler;
import org.xsmart.system.entity.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ControllerContainerBuilder {

    private static Map<Request, Handler> REQUEST_HANDLER_MAP = new HashMap<>();

    static {
        HashSet<Class<?>> classSet = ClassContainerBuilder.getControllerClass();
        if(!classSet.isEmpty()){
            for(Class<?> clz : classSet){
                Method[] classMethods = clz.getMethods();
                if(classMethods.length == 0) continue;;

                for (Method method : classMethods){

                    if (!method.isAnnotationPresent(Action.class)){
                        continue;
                    }

                    Action action = method.getAnnotation(Action.class);
                    String actionValue = action.value();
//                    if (!actionValue.matches("\\w+:/\\w/*")) {
//                        continue;
//                    }

                    String[] array = actionValue.split(":");
                    if (!(array.length == 2)) {
                        continue;
                    }

                    String requestMethod = array[0].toLowerCase();
                    String requestPath = array[1];
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
