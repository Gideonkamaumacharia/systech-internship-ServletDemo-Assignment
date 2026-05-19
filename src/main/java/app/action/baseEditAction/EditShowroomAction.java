package app.action.baseEditAction;

import app.bean.ShowroomBean;
import app.model.Showroom;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/editShowroom")
public class EditShowroomAction extends BaseEditAction<Showroom>{

    @EJB
    ShowroomBean showroomBean;

    @Override
    protected Showroom findEntity(Long id) throws Exception {
        return showroomBean.findById(id);
    }

    @Override
    protected void updateEntity(Showroom showroom) throws Exception {
        showroomBean.update(showroom);

    }

    @Override
    protected String getAttributeName() {
        return "showroom";
    }

    @Override
    protected String getEditJsp() {
        return "editShowroom.jsp";
    }
}
