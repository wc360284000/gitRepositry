package com.wuchang;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/1/26.
 */
@WebServlet(name = "JdbcServlet",urlPatterns = "/wuchangjdbc")
public class JdbcServlet extends HttpServlet {

    private Connection connection =jdbcTest.getConnection();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String sql ="SELECT *FROM world.city";
        List<Map<String, Object>> objext = jdbcTest.getObjext(connection, sql);
        ObjectMapper objectMapper =new ObjectMapper();
        String s = objectMapper.writeValueAsString(objext);
        System.out.println(s);
        response.getWriter().print(s);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
             doPost(request, response);



    }
}
