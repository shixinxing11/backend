package lab.backend.config;

import lab.backend.util.JwtUtil;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    public AuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String auth = req.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            resp.setStatus(401); // 没带证：401
            resp.getWriter().write("{\"error\":\"未登录\"}");
            return false;
        }
        try {
            String username = jwtUtil.verify(auth.substring(7)); // 去掉 "Bearer "
            req.setAttribute("username", username); // 后面接口要用到
            return true;
        } catch (Exception e) {
            resp.setStatus(401); // 证是假的/过期：401
            resp.getWriter().write("{\"error\":\"登录已失效，请重新登录\"}");
            return false;
        }
    }
}
