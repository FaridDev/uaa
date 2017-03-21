package pl.java.scalatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableDiscoveryClient
@Controller
public class DashboardHystrixApplication {
   
    @RequestMapping("/")
    public String home() {
        return "forward:/hystrix";
    }
    
    public static void main(String[] args) {
        springPIDAppRun(args, DashboardHystrixApplication.class);
    }
    private static void springPIDAppRun(String[] args,Class<?> clazz) {
        SpringApplication springApplication = new SpringApplication(clazz);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }
    @Bean
    public ServletRegistrationBean mockStreamServlet() {
        return new ServletRegistrationBean(new MockStreamServlet(), "/mock.stream");
}
}
