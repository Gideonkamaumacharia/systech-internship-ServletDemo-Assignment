//package app.security;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.security.enterprise.AuthenticationException;
//import jakarta.security.enterprise.AuthenticationStatus;
//import jakarta.security.enterprise.SecurityContext;
//import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
//import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
//import jakarta.security.enterprise.credential.UsernamePasswordCredential;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@ApplicationScoped
//public class ShowroomAuthMechanism implements HttpAuthenticationMechanism {
//
//    @Inject
//    private SecurityContext securityContext;
//
//    @Override
//    public AuthenticationStatus validateRequest(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            HttpMessageContext context) throws AuthenticationException {
//
//        // Only process when credentials are explicitly submitted
//        // (your LoginServlet calls securityContext.authenticate() which triggers this)
//        if (context.isAuthenticationRequest()) {
//            UsernamePasswordCredential credential =
//                    context.getAuthParameters().getCredential(UsernamePasswordCredential.class);
//
//            if (credential != null) {
//                return context.notifyContainerAboutLogin(
//                        context.getIdentityStoreHandler().validate(credential)
//                );
//            }
//        }
//
//        // For all other requests — check if there's already an established session
//        return context.doNothing();
//    }
//
//    @Override
//    public void cleanSubject(HttpServletRequest request,
//                             HttpServletResponse response,
//                             HttpMessageContext context) {
//        // Called on logout — invalidate session
//        HttpSession session = request.getSession(false);
//        if (session != null) session.invalidate();
//    }
//}
