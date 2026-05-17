package app.action.baseListAction;

import app.action.BaseAction;
import app.framework.ShowroomTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public abstract class BaseListAction<T> extends BaseAction<T> {

    protected abstract List<T> fetchList(HttpServletRequest req) throws Exception;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        try {
            List<T> dataList = fetchList(req);
            req.setAttribute("dataList", dataList);
        } catch (Exception e) {
            throw new ServletException("Failed to fetch list", e);
        }

        String jspName = getType().isAnnotationPresent(ShowroomTable.class)
                ? getType().getAnnotation(ShowroomTable.class).listJsp()
                : "list.jsp";

        req.getRequestDispatcher(jspName).forward(req, resp);
    }
}
