package ie.ucd.dfh.filters;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 60*60*30; //30 minutes
    public static final String SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String COOKIE_NAME = "JWT";
    public static final int LOGIN_ATTEMPT_LIMIT = 3;
}
