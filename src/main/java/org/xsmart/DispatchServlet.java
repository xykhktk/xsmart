package org.xsmart;

import org.xsmart.bean.Data;
import org.xsmart.controller.UserController;
import org.xsmart.core.util.JsonUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatchServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        try {
            UserController userController = new UserController();
            Object result = userController.list();
            if(result instanceof Data){
                Data data = (Data) result;
                writer.write(JsonUtil.objectToJsonString(data));
            }
        }catch (Exception e){
            writer.write("load failed");
        }finally {
            writer.flush();
            writer.close();
        }

    }
}
