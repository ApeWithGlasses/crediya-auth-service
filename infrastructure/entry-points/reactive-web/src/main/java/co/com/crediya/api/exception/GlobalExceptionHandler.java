package co.com.crediya.api.exception;

import co.com.crediya.model.user.exceptions.DocumentAlreadyExistsException;
import co.com.crediya.model.user.exceptions.EmailAlreadyExistsException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.result.view.ViewResolver;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class GlobalExceptionHandler {

    @Bean
    public ErrorWebExceptionHandler exceptionHandler(ErrorAttributes errorAttributes,
                                                     WebProperties webProperties,
                                                     ObjectProvider<ViewResolver> viewResolvers,
                                                     ServerCodecConfigurer serverCodecConfigurer,
                                                     ApplicationContext applicationContext) {


        DefaultErrorWebExceptionHandler exceptionHandler = new DefaultErrorWebExceptionHandler(
                errorAttributes,
                webProperties.getResources(),
                new ErrorProperties(),
                applicationContext) {

            @Override
            protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
                return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
            }

            @Override
            protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
                Throwable error = getError(request);

                if (error instanceof DocumentAlreadyExistsException) {
                    return ServerResponse
                            .status(HttpStatus.CONFLICT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(ErrorResponse.from(
                                    error.getMessage(),
                                    "USER_DOCUMENT_EXISTS",
                                    HttpStatus.CONFLICT
                            ));
                }

                if (error instanceof EmailAlreadyExistsException) {
                    return ServerResponse
                            .status(HttpStatus.CONFLICT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(ErrorResponse.from(
                                    error.getMessage(),
                                    "USER_EMAIL_EXISTS",
                                    HttpStatus.CONFLICT
                            ));
                }

                if (error instanceof ValidationException ex) {
                    List<String> errors = ex.getBindingResult()
                            .getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.toList());

                    return ServerResponse
                            .status(HttpStatus.BAD_REQUEST)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(ErrorResponse.from(
                                    "Error de validación",
                                    "VALIDATION_ERROR",
                                    HttpStatus.BAD_REQUEST,
                                    errors
                            ));
                }

                return ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ErrorResponse.from(
                                "Error interno del servidor",
                                "INTERNAL_ERROR",
                                HttpStatus.INTERNAL_SERVER_ERROR
                        ));
            }
        };

        exceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }
}