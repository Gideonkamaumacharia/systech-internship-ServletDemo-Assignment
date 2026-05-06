//package app.bean;
//
//import app.dao.GenericDao;
//import app.model.Brand;
//import app.model.Car;
//import jakarta.ejb.Stateless;
//import jakarta.inject.Inject;
//
//import java.sql.SQLException;
//import java.util.List;
//
//@Stateless
//public class BrandBean {
//
//    @Inject
//    GenericDao dao;
//
//    public void create(Brand brand){
//        dao.insert(Brand.class,brand);
//        System.out.println("BrandBean: createBean() called");
//    }
//
//    public List<Brand> getbrands(String showroomId)  {
//        List<Brand> data;
//
//        try {
//            if (showroomId != null && !showroomId.isEmpty()) {
//                System.out.println("EJB filtering: showroomId = " + showroomId);
//                data = dao.selectWhere(Brand.class, "showroomId", Long.parseLong(showroomId));
//            } else {
//                data = dao.selectAll(Brand.class);
//            }
//
//            for (Brand brand : data) {
//                dao.populateRelationships(brand);
//            }
//
//            return data;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
