package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository {
    Mono<User> saveUser(User user);
    Mono<User> findUserByEmail(String emailAddress);
    Mono<User> findUserById(UUID id);
    Mono<User> updateUser(User user);
    Mono<Void> deleteUserById(UUID id);
    Mono<Boolean> existsByEmail(String email);
}
