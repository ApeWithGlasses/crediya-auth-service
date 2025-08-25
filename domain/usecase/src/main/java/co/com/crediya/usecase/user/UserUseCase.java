package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.usecase.user.port.UserUseCasePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class UserUseCase implements UserUseCasePort {
    private final UserRepository userRepository;

    public Mono<User> saveUser(User user) {
        return userRepository.saveUser(user);
    }

    public Mono<User> findUserByEmail(String emailAddress) {
        return userRepository.findUserByEmail(emailAddress);
    }

    public Mono<User> findUserById(UUID id) {
        return userRepository.findUserById(id);
    }

    public Mono<User> updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public Mono<Void> deleteUserById(UUID id) {
        return userRepository.deleteUserById(id);
    }

    public Mono<Boolean> existsByEmail(String emailAddress) {
        return userRepository.existsByEmail(emailAddress);
    }
}
