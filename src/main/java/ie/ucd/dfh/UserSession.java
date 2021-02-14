package ie.ucd.dfh;

import ie.ucd.dfh.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {

    private User user;
    private boolean loginFailed;
    private boolean isEmailTaken;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoginFailed() {
        return loginFailed;
    }

    public void setLoginFailed(boolean loginFailed) {
        this.loginFailed = loginFailed;
    }

    public boolean isEmailTaken() {
        return isEmailTaken;
    }

    public void setEmailTaken(boolean emailTaken) {
        isEmailTaken = emailTaken;
    }
}
