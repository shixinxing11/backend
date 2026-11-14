package lab.backend.controller;

import lab.backend.model.User;
import lab.backend.repository.UserRepository;
import lab.backend.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository users, JwtUtil jwtUtil) {
        this.users = users;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User u) {
        if (users.findByUsername(u.getUsername()).isPresent()) throw new RuntimeException("用户名已被占用");
        u.setPassword(encoder.encode(u.getPassword()));   // 只存哈希
        u.setRole("member");
        users.save(u);
        return Map.of("message", "注册成功");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User u) {
        User dbUser = users.findByUsername(u.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        if (!encoder.matches(u.getPassword(), dbUser.getPassword())) throw new RuntimeException("用户名或密码错误");
        String token = jwtUtil.generate(dbUser.getId(), dbUser.getUsername());
        return Map.of("token", token, "role", dbUser.getRole());
    }
}
