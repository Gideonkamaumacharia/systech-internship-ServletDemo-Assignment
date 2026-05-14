package app.action.baseListAction;

import app.bean.ShowroomBean;
import app.model.Showroom;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@WebServlet("/showroom_list")
public class ShowroomList extends BaseListAction<Showroom> {

    @Inject
    private ShowroomBean showroomBean;


    @Override
    protected List<Showroom> fetchList(HttpServletRequest req) throws Exception {
        return showroomBean.getShowrooms();
    }
}