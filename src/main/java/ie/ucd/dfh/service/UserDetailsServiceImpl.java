package ie.ucd.dfh.service;

import ie.ucd.dfh.model.User;
import ie.ucd.dfh.repository.UserRepository;
import jdk.internal.net.http.common.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        String ip = getClientIP();

        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException(username));
        return new ACUserDetails(user);
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if(xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}