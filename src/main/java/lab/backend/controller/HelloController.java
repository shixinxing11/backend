package lab.backend.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController                        // 告诉 Spring：我是个「接待员」，返回 JSON
    public class HelloController {

        @GetMapping("/hello")              // 请求方式+路径：GET /hello 找这个
        public String hello() {
            return "你好, 实验室!";
        }
    }

