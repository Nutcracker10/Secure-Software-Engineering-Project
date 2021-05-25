package ie.ucd.dfh.filters;

import com.auth0.jwt.JWT;
import ie.ucd.dfh.model.Attempts;
import ie.ucd.dfh.model.User;
import ie.ucd.dfh.service.ACUserDetails;
import ie.ucd.dfh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import static ie.ucd.dfh.filters.SecurityConstant.*;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        if(userDetailsService==null)
            userDetailsService = FilterUtil.loadUserDetailsService(request);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getParameter("username"));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, request.getParameter("password"), userDetails.getAuthorities());
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        log.info(String.format("Successful Login: [username: %s]",
            request.getParameter("username")));

        String token = JWT.create()
                .withSubject(((ACUserDetails) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        //res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        Attempts userAttempts = userService.findAttemptsByUsername(request.getParameter("username"));
        userAttempts.setAttempts(0);
        userService.save(userAttempts);
        addCookie(token, response);

        new DefaultRedirectStrategy().sendRedirect(request, response, "/");

        //authenticationSuccessHandler.onAuthenticationSuccess(request, response, getAuthentication(token));
        //getAuthentication(token).
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //super.unsuccessfulAuthentication(request, response, failed);

        String target = "error";
        String username = request.getParameter("username");
        Attempts userAttempts = userService.findAttemptsByUsername(username);
        User user = userService.findByUsername(username);

       if(user != null) {
           if (userAttempts == null) {
               Attempts attempts = new Attempts();
               attempts.setUsername(username);
               attempts.setAttempts(1);
               userService.save(attempts);
           } else {
               System.out.println("USER ATTEMPTS PRESENT");
               userAttempts.setAttempts(userAttempts.getAttempts() + 1);
               userService.save(userAttempts);

               if (userAttempts.getAttempts() + 1 > SecurityConstant.LOGIN_ATTEMPT_LIMIT) {
                   System.out.println("ATTEMPTS GREATER THAN LIMIT");
                   user.setAccountNonLocked(false);
                   userService.save(user);

                   log.warn(String.format("Account Locked: [ID: %s, username: %s, password: %s]",
                           user.getId(),
                           request.getParameter("username"),
                           request.getParameter("password")));
                   target = "locked";
                   //throw new LockedException("Too many invalid attempts. Account is locked!!");
               }
           }
       }

        log.warn(String.format("Unsuccessful Login: [username: %s, password: %s]",
                request.getParameter("username"),
                request.getParameter("password")));
        response.sendRedirect(request.getContextPath() + "/login?"+target);
    }

    private void addCookie(String token, HttpServletResponse response){
        Cookie cookie = new Cookie(COOKIE_NAME, token);

        //25 minutes
        cookie.setMaxAge( 10* 60 * 60);

        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
