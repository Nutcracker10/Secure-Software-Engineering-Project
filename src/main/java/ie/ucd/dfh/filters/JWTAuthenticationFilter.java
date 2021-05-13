package ie.ucd.dfh.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ie.ucd.dfh.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest servletRequest, HttpServletResponse httpServletResponse)
    {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(servletRequest.getParameter("username"),
                servletRequest.getParameter("password"),
                        new ArrayList<>()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {
        String token  = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstant.SECRET.getBytes()));

        addCookie(token,servletResponse);
        new DefaultRedirectStrategy().sendRedirect(servletRequest,servletResponse,"/");
    }

    private void addCookie(String token, HttpServletResponse response){
        Cookie cookie = new Cookie(SecurityConstant.COOKIE_NAME, token);

        //10 days
        cookie.setMaxAge(10 * 24 * 60 * 60);

        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
