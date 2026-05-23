package app.soap;

import app.bean.CarBean;
import app.bean.UserBean;
import app.model.Car;
import app.model.User;
import app.rest.ResponseStatus;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService
public class CarSoapApi {

    @EJB
    private CarBean carBean;

    @EJB
    private UserBean userBean;

    @WebMethod
    public ResponseStatus save(@WebParam(name = "car") Car car){
        User currentUser = userBean.findById(1L);

        carBean.create(car,currentUser);

        return new ResponseStatus();
    }

    @WebMethod
    @WebResult(name = "cars")
    public CarWrapper list(){
        return new CarWrapper(carBean.findAll());
    }

    @WebMethod
    public String displayMessage(
            @WebParam(name = "message") String message){
        return "The message is " + message;
    }

}