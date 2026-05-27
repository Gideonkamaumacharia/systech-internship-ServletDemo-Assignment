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
import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import java.security.Principal;

@WebService
public class CarSoapApi {

    @EJB private CarBean carBean;
    @EJB private UserBean userBean;

    @Resource
    private WebServiceContext wsContext;  // JAX-WS caller context

    // ── Helper ────────────────────────────────────────────────

    private User resolveSoapCaller() {
        MessageContext msgContext = wsContext.getMessageContext();
        Principal principal = wsContext.getUserPrincipal();

        if (principal == null) {
            throw new RuntimeException("SOAP call not authenticated.");
        }

        User user = userBean.findByUsername(principal.getName());
        if (user == null) {
            throw new RuntimeException("No user record for: " + principal.getName());
        }
        return user;
    }

    // ── Endpoints ─────────────────────────────────────────────

    @WebMethod
    public ResponseStatus save(@WebParam(name = "car") Car car) {
        User caller = resolveSoapCaller();
        carBean.create(car, caller);
        return new ResponseStatus();
    }

    @WebMethod
    @WebResult(name = "cars")
    public CarWrapper list() {
        User caller = resolveSoapCaller();
        return new CarWrapper(carBean.findAll(caller));
    }

    @WebMethod
    public String displayMessage(@WebParam(name = "message") String message) {
        resolveSoapCaller(); // auth check only
        return "The message is " + message;
    }
}