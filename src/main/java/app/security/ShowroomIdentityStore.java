package app.security;

import app.dao.UserDAO;
import app.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@ApplicationScoped
public class ShowroomIdentityStore {  // no longer implements IdentityStore

    @Inject
    private UserDAO userDAO;

    public User validate(String username, String password) {
        Optional<User> found = userDAO.findByUsername(username);

        if (found.isEmpty()) {
            System.out.println("=== User NOT found: " + username);
            return null;
        }

        User user = found.get();
        System.out.println("=== Found user: " + username);
        System.out.println("=== Hash in DB: " + user.getPasswordHash());
        System.out.println("=== BCrypt result: " + BCrypt.checkpw(password, user.getPasswordHash()));

        if (!BCrypt.checkpw(password, user.getPasswordHash())) return null;

        return user; // validated — return the full User object
    }
}