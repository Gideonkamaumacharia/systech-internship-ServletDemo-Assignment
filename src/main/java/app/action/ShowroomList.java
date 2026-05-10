package app.action;

import app.bean.ShowroomBean;
import app.framework.ShowroomTable;
import app.model.Showroom;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/showroom_list")
public class ShowroomList extends BaseAction<Showroom> {

    @Inject
    ShowroomBean showroomBean;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Showroom> dataList = showroomBean.getShowrooms();

        req.setAttribute("dataList",dataList);
//        for (Showroom s : dataList) {
//            System.out.println("Showroom: " + s.getLocationName());
//            System.out.println("ManagerId: " + s.getManagerId());
//            System.out.println("Manager Object: " + s.getManager());
//        }

        String jspName = Showroom.class.isAnnotationPresent(ShowroomTable.class) ?
                Showroom.class.getAnnotation(ShowroomTable.class).listJsp() : "list.jsp";

        req.getRequestDispatcher(jspName).forward(req, resp);

    }
}