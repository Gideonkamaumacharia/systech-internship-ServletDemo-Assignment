package app.action;

import app.framework.ShowroomTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BaseActionList<T> extends BaseAction<T>{


    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

       Class<T> entityClass = getType();

        List<T> dataList;
        try {
            dataList = returnData(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("dataList",dataList);

        //Go look at this class, find the @ShowroomTable annotation, and hand me the object that represents it.
        //.listJsp -> // Access the value stored inside that specific container
        String jspName = entityClass.isAnnotationPresent(ShowroomTable.class)?
                entityClass.getAnnotation(ShowroomTable.class).listJsp() : "list.jsp";

        req.getRequestDispatcher(jspName).forward(req,resp);


}
}
