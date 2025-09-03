package co.com.crediya.r2dbc;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.enums.DocumentType;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<User, UserEntity, Long, UserReactiveRepository> implements UserRepository {
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<User> saveUser(User user) {
        return Mono.just(user)
                .map(u -> mapper.map(u, UserEntity.class))
                .flatMap(repository::save)
                .map(this::toModel);
    }

    private User toModel(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .documentNumber(entity.getDocumentNumber())
                .documentType(DocumentType.valueOf(entity.getDocumentType()))
                .name(entity.getName())
                .lastName(entity.getLastName())
                .dateOfBirth(entity.getDateOfBirth())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .emailAddress(entity.getEmailAddress())
                .baseSalary(entity.getBaseSalary())
                .build();
    }

    @Override
    public Mono<Boolean> existsByDocumentTypeAndNumber(String documentType, String documentNumber) {
        return repository.existsByDocumentTypeAndNumber(documentType, documentNumber);
    }

    @Override
    public Mono<Boolean> existsByEmail(String emailAddress) {
        return repository.existsByEmail(emailAddress);
    }
}
