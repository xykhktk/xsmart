package org.xsmart;

import org.xsmart.system.core.BeanContainerBuilder;
import org.xsmart.system.core.ControllerContainerBuilder;
import org.xsmart.system.core.FrameworkInitializer;
import org.xsmart.system.entity.Data;
import org.xsmart.system.entity.Handler;
import org.xsmart.system.entity.Request;
import org.xsmart.system.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        FrameworkInitializer.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        Request request = new Request(requestMethod,requestPath);
        Handler handler = ControllerContainerBuilder.getHandler(request);
        if(handler == null){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            Data data = new Data();
            Map<String,String> error = new HashMap<>();
            error.put("message","handler not found");
            data.setModel(error);
            writer.write(JsonUtil.objectToJsonString(data));
            return;
        }

        Class<?> clz = handler.getControllerClass();
        Object handleBean = BeanContainerBuilder.getBean(clz);

        Map<String,Object> params = new HashMap<>();
        Enumeration<String> requestParameterNames =  req.getParameterNames();
        while(requestParameterNames.hasMoreElements()){
            String key = requestParameterNames.nextElement();
            String value = req.getParameter(key);
            params.put(key,value);
        }

        String body = CodeUtil.UrlDecode(StreamUtil.getString(req.getInputStream()));
        if(!body.equals("")){
            String[] keyValues =  body.split("&");
            if(keyValues.length > 1){
                for(String keuValue : keyValues){
                    String[] array = keuValue.split("=");
                    if(array.length == 2){
                        params.put(array[0],array[1]);
                    }
                }
            }
        }

        RequestParams requestParams = new RequestParams(params);
        Method method = handler.getActionMethod();
        Object result = new Object();
        result = ReflectionUtil.invokeMethod(handleBean,method,requestParams);

        if (result instanceof Data) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            Data data = (Data) result;
            writer.write(JsonUtil.objectToJsonString(data));
        }

    }
}
