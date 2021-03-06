package pl.java.scalatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import zipkin.server.EnableZipkinServer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableZipkinServer
@RefreshScope
public class ZipkinApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ZipkinApplication.class).web(true);
    }

    public static void main(String[] args) {
        springPIDAppRun(args, ZipkinApplication.class);
    }

    private static void springPIDAppRun(String[] args, Class<?> clazz) {
        SpringApplication springApplication = new SpringApplication(
                clazz);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

    /*
     * @Bean
     * public AlwaysSampler defaultSampler() {
     * return new AlwaysSampler();
     * }
     */
}
