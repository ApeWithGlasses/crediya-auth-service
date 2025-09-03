package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {


    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM usuarios u WHERE u.correo_electronico = :emailAddress")
    Mono<Boolean> existsByEmail(String emailAddress);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM usuarios u WHERE u.tipo_identificacion = :documentType AND u.identificacion = :documentNumber")
    Mono<Boolean> existsByDocumentTypeAndNumber(String documentType, String documentNumber);
}
