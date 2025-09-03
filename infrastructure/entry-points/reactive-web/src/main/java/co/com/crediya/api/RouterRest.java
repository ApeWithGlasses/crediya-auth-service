package co.com.crediya.api;

import co.com.crediya.api.config.UserPath;
import co.com.crediya.api.dto.UserRequest;
import co.com.crediya.api.exception.ValidationException;
import co.com.crediya.model.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {
    private final UserPath userPath;
    private final Handler userHandler;

    @RouterOperations({
        @RouterOperation(
                path = "/api/v1/usuarios",
                produces = MediaType.APPLICATION_JSON_VALUE,
                method = RequestMethod.POST,
                beanClass = Handler.class,
                beanMethod = "listenSaveUser",
                operation = @Operation(
                        operationId = "registerUser",
                        summary = "Registrar nuevo usuario",
                        description = "Crea un nuevo usuario en el sistema con validación de datos",
                        tags = {"Usuarios"},
                        requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = UserRequest.class))),
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente", content = @Content(schema = @Schema(implementation = User.class))),
                                @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o usuario ya existe", content = @Content(schema = @Schema(implementation = ValidationException.class))),
                                @ApiResponse(responseCode = "409", description = "Conflicto: documento o email ya existe", content = @Content(schema = @Schema(implementation = ValidationException.class)))
                        }

                )
        )
    })
    public RouterFunction<ServerResponse> routerFunction() {
        return route(POST(userPath.getSaveUser()), userHandler::listenSaveUser);
    }
}
