package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exceptions.DocumentAlreadyExistsException;
import co.com.crediya.model.user.exceptions.EmailAlreadyExistsException;
import co.com.crediya.model.user.gateways.TransactionGateway;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final TransactionGateway transactionGateway;

    public Mono<User> saveUser(User user) {
        return transactionGateway.inTransaction(
            Mono.zip(
                    userRepository.existsByDocumentTypeAndNumber(user.getDocumentType().toString(), user.getDocumentNumber()),
                    userRepository.existsByEmail(user.getEmailAddress())
            )
            .flatMap(tuple -> {
                boolean userExists = tuple.getT1();
                boolean emailExists = tuple.getT2();
                if (userExists) {
                    return Mono.error(new DocumentAlreadyExistsException());
                }
                if (emailExists) {
                    return Mono.error(new EmailAlreadyExistsException());
                }
                return userRepository.saveUser(user);
            })
        );
    }
}
