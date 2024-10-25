package co.onclass.model.technology.gateways;

import co.onclass.model.technology.Technology;
import reactor.core.publisher.Mono;

public interface TechnologyRepositoryGateway {
    Mono<Technology> create(Technology technology);

    Mono<Boolean> existsByName(String name);
}
