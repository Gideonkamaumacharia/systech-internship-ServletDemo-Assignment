package app.action.baseListAction;


import app.bean.CarBean;
import app.model.Car;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;


@WebServlet("/list")
public class CarList extends BaseListAction<Car> {

    @EJB
    private CarBean carBean;


    @Override
    protected List<Car> fetchList(HttpServletRequest req) throws Exception {
        String showroomId = req.getParameter("showroom_Id");
        return carBean.getCars(showroomId);
    }
}
