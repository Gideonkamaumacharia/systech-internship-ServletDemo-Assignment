package app.framework;


import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/app/*")
public class ActionDispatcherServlet extends HttpServlet {

    @Inject
    private ShowroomFramework showroomFramework;

    @Override
    public void init() {
        // Scans app.action package on startup and registers all mappings
        ActionRegistry.scanAndRegister("app.action");

    }

    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userAuthenticated") == null) {
            String dest = req.getRequestURI()//showroom/app/car/list
                    .substring(req.getContextPath().length());
                    //req.getContextPath() returns the root folder name ie /showroom
                    //.length of /showroom   -> result is 9
                    //.substring(9) -> cuts of the first 9 xters from the URI - Result -> /app/car/list
            resp.sendRedirect(req.getContextPath() + "/login?dest=" + dest);
            // /showroom/login?dest=/app/car/list
            return;
        }


        String requestPath = req.getPathInfo();   // eg /car/list
        String httpMethod  = req.getMethod();      // GET or POST

        ActionMapMatch actionMapMatch =
                ActionRegistry.findMatch(requestPath, httpMethod);

        if (actionMapMatch == null) {
            resp.sendError(404, "No action found for: " + requestPath);
            return;
        }

        try {
            // CDI creates the action instance — so @EJB injection still works
            Object actionInstance = CDI.current()
                    .select(actionMapMatch.getActionMap().getAction())//CarAction.class
                    .get();

            // Bind method parameters automatically
            Object[] args = ActionParamBinder.bind(
                    actionMapMatch.getActionMap(),
                    req,
                    resp,
                    actionMapMatch.getPathVariables()
            );

            // Call the method
            ActionResponse actionResponse = (ActionResponse)
                    actionMapMatch.getActionMap()
                            .getMethod()
                            .invoke(actionInstance, args);


            // Handle the response
            if (actionResponse.getResponseText() != null) {
                String text = actionResponse.getResponseText();

                if (text.startsWith("redirect:")) {
                    String redirectPath = text.substring("redirect:".length());

                    resp.sendRedirect(req.getContextPath() + redirectPath);
                } else {
                    resp.setContentType("text/html;charset=UTF-8");
                    resp.getWriter().write(text);
                }

            } else {
                // Framework renders the list as HTML table
                String html = showroomFramework.htmlTable(
                        actionResponse.getResponseClazz(),//Car.class
                        actionResponse.getResponseDataList(), //list of cars
                        req.getContextPath()//showroom
                );
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().write(html);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
