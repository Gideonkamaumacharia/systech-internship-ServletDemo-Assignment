//package app.action.baseEditAction;
//
//import app.action.BaseAction;
//import app.framework.ShowroomTable;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import javax.crypto.ExemptionMechanismException;
//import java.io.IOException;
//
//public abstract class BaseEditAction<T> extends BaseAction<T> {
//
//    protected abstract T findEntity(Long id) throws Exception;
//    protected abstract void updateEntity(T entity) throws Exception;
//    protected  abstract String getAttributeName();
//    protected  abstract String getEditJsp();
//
//    @Override
//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       Long id = Long.parseLong(req.getParameter("id"));
//
//       try{
//           T entity = findEntity(id);
//           req.setAttribute(getAttributeName(),entity);
//       }catch(Exception e){
//           throw new ServletException("Failed to find entity", e);
//       }
//
//       req.getRequestDispatcher(getEditJsp()).forward(req,resp);
//    }
//
//    @Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            T entity = serializeForm(req.getParameterMap());
//            updateEntity(entity);
//        } catch (Exception e) {
//            throw new ServletException("Failed to update entity", e);
//        }
//
//        String redirectUrl = getType().isAnnotationPresent(ShowroomTable.class)
//                ? getType().getAnnotation(ShowroomTable.class).tableUrl()
//                : "list";
//
//        resp.sendRedirect(redirectUrl);
//    }
//}
