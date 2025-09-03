package co.com.crediya.model.user.gateways;

import reactor.core.publisher.Mono;

public interface TransactionGateway {
    <T> Mono<T> inTransaction(Mono<T> operation);
}
