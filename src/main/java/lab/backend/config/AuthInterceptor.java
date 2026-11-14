package lab.backend.config;

import io.jsonwebtoken.Claims;
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
            Claims claims = jwtUtil.parse(auth.substring(7)); // 去掉 "Bearer "
            req.setAttribute("username", claims.getSubject()); // 后面接口要用到
            req.setAttribute("role", claims.get("role", String.class));

            // 角色控制：删除学生只允许 admin
            if ("DELETE".equalsIgnoreCase(req.getMethod())
                    && req.getRequestURI().startsWith("/api/students/")
                    && !"admin".equals(req.getAttribute("role"))) {
                resp.setStatus(403);
                resp.getWriter().write("{\"error\":\"无权限，需要管理员\"}");
                return false;
            }
            return true;
        } catch (Exception e) {
            resp.setStatus(401); // 证是假的/过期：401
            resp.getWriter().write("{\"error\":\"登录已失效，请重新登录\"}");
            return false;
        }
    }
}
