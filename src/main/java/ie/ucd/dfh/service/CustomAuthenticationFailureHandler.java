package ie.ucd.dfh.service;

import ie.ucd.dfh.filters.SecurityConstant;
import ie.ucd.dfh.model.Attempts;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.AttemptsRepository;
import ie.ucd.dfh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    AttemptsRepository attemptsRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        System.out.println("IN UNSUCESSFUL AUTHENTICATION");
        String username = request.getParameter("username");
        Attempts userAttempts = attemptsRepository.findAttemptsByUsername(username).orElse(null);
        User user = userRepository.findByUsername(username).orElse(null);
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
                    throw new LockedException("Too many invalid attempts. Account is locked!!");
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }
}
