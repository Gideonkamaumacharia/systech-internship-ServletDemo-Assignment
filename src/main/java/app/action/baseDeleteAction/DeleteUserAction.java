//package app.action.baseDeleteAction;
//
//import app.bean.UserBean;
//import app.model.User;
//import jakarta.ejb.EJB;
//import jakarta.servlet.annotation.WebServlet;
//
//@WebServlet("/deleteUser")
//public class DeleteUserAction extends BaseDeleteAction<User>{
//
//    @EJB
//    private UserBean userBean;
//
//    @Override
//    protected void removeEntity(Long id) throws Exception {
//        userBean.remove(id);
//    }
//}
