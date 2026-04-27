package app.action;

import app.framework.ShowroomFramework;
import app.framework.ShowroomTable;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseActionList<T> extends BaseAction<T>{


    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

       Class<T> entityClass = getType(); //Car.class

        HttpSession session = req.getSession();
        List<T> dataList;
        try {
            dataList = returnData(session);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("dataList",dataList);//setAttribute("dataList",carList)

        //Go look at this class, find the @ShowroomTable annotation, and hand me the object that represents it.
        //.listJsp -> // Access the value stored inside that specific container
        String jspName = entityClass.isAnnotationPresent(ShowroomTable.class)?
                entityClass.getAnnotation(ShowroomTable.class).listJsp() : "list.jsp";

        req.getRequestDispatcher(jspName).forward(req,resp);


}
}
