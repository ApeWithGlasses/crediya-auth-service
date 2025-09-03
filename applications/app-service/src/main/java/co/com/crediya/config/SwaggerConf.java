package co.com.crediya.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de autenticación CrediYa",
                version = "1.0.0",
                description = "API para la autenticación y gestión de usuarios en CrediYa"
        ),
        servers = {
                @Server(description = "Servidor Local", url = "http://localhost:8080")
        }
)
public class SwaggerConf {

}
