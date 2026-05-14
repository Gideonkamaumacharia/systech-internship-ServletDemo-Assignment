package app.action.baseFormAction;

import app.bean.BrandBean;
import app.model.Brand;
import app.model.User;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/brand")
public class BrandAction extends BaseFormAction<Brand> {

    @EJB
    private BrandBean brandBean;


    @Override
    protected void persistEntity(Brand brand, User currentUser) throws Exception {
        brandBean.create(brand,currentUser);
    }
}

