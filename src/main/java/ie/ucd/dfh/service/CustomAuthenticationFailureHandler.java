package ie.ucd.dfh.service;

import ie.ucd.dfh.filters.SecurityConstant;
import ie.ucd.dfh.model.Attempts;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.AttemptsRepository;
import ie.ucd.dfh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Autowired
    private AttemptsRepository attemptsRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String username = request.getParameter("username");

        log.warn(String.format("Unsuccessful Authentication: [username: %s, password: %s]",
                request.getParameter("username"),
                request.getParameter("password")));
        //Attempts userAttempts = attemptsRepository.findAttemptsByUsername(username).orElse(null);
        User user = userService.findByUsername(username);

/*        if(user != null){
            if (userAttempts == null) {
                System.out.println("USER ATTEMPS NOT PRESENT");
                Attempts attempts = new Attempts();
                attempts.setUsername(username);
                attempts.setAttempts(1);
                attemptsRepository.save(attempts);
            } else {
                System.out.println("USER ATTEMPS PRESENT");
                userAttempts.setAttempts(userAttempts.getAttempts() + 1);
                attemptsRepository.save(userAttempts);

                if (userAttempts.getAttempts() + 1 >
                        SecurityConstant.LOGIN_ATTEMPT_LIMIT) {
                    System.out.println("ATTEMPTS GREATER THAN LIMIT");
                    if(user != null) {
                        user.setAccountNonLocked(false);
                        userRepository.save(user);
                        log.warn(String.format("Account Locked: [ID: %s, username: %s, password: %s]",
                                user.getId(),
                                request.getParameter("username"),
                                request.getParameter("password")));

                        throw new LockedException("Too many invalid attempts. Account is locked!!");

                    }
                }
            }
        }*/

        response.sendRedirect(request.getContextPath() + "/login");
    }
}
