package co.onclass.technology.repository;

import co.onclass.technology.entity.TechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Integer> {
    Mono<Boolean> existsByName(String name);
}
