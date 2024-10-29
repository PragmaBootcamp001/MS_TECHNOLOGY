package co.onclass.model.technology.gateways;

import co.onclass.model.technology.Technology;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TechnologyRepositoryGateway {
    Mono<Technology> create(Technology technology);
    Mono<List<Technology>> list(Integer offset, Integer limit, Boolean isAscending);
    Mono<Boolean> existsByName(String name);
}
