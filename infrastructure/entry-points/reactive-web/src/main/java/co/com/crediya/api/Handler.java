package co.com.crediya.api;

import co.com.crediya.api.dto.UserRequest;
import co.com.crediya.api.exception.ValidationException;
import co.com.crediya.usecase.user.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RegisterUserUseCase registerUserUseCase;
    private final Validator validator;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequest.class)
                .flatMap(request -> {
                    var errors = new BeanPropertyBindingResult(request, UserRequest.class.getName());
                    validator.validate(request, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationException(errors));
                    }
                    return Mono.just(request);
                })
                .map(UserRequest::toDomain)
                .flatMap(registerUserUseCase::saveUser)
                .flatMap(savedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser));
    }
}
