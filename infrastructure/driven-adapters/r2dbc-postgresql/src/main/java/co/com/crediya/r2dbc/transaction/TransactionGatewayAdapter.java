package co.com.crediya.r2dbc.transaction;

import co.com.crediya.model.user.gateways.TransactionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransactionGatewayAdapter implements TransactionGateway {
    private final TransactionalOperator transactionalOperator;

    @Override
    public <T> Mono<T> inTransaction(Mono<T> operation) {
        return transactionalOperator.transactional(operation);
    }
}
