package co.com.crediya.api;

import co.com.crediya.api.dto.UserRequest;
import co.com.crediya.api.exception.ValidationException;
import co.com.crediya.model.user.User;
import co.com.crediya.usecase.user.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final Validator validator;

    @PostMapping
    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario en el sistema con validación de datos", tags = "Usuarios")
    public Mono<ResponseEntity<User>> registerUser(@RequestBody UserRequest request) {
        var errors = new BeanPropertyBindingResult(request, UserRequest.class.getName());
        validator.validate(request, errors);

        if (errors.hasErrors()) {
            return Mono.error(new ValidationException(errors));
        }

        return registerUserUseCase.saveUser(request.toDomain())
                .map(user -> ResponseEntity.ok().body(user));
    }
}
