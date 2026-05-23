//package app.action.baseDeleteAction;
//
//import app.bean.ShowroomBean;
//import app.model.Showroom;
//import jakarta.inject.Inject;
//import jakarta.servlet.annotation.WebServlet;
//
//@WebServlet("/deleteShowroom")
//public class DeleteShowroomAction extends BaseDeleteAction<Showroom>{
//
//    @Inject
//    private ShowroomBean showroomBean;
//
//    @Override
//    protected void removeEntity( Long id) throws Exception {
//        showroomBean.remove(id);
//    }
//}
