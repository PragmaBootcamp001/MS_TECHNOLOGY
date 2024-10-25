package co.onclass.usecase.technology;

import co.onclass.model.technology.Technology;
import co.onclass.model.technology.errors.DuplicatedTechnologyException;
import co.onclass.model.technology.gateways.TechnologyRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyUseCase {
    private final TechnologyRepositoryGateway technologyRepositoryGateway;

    public Mono<Technology> register(Technology technology) {
        return technologyRepositoryGateway.existsByName(technology.getName())
                .flatMap(exists ->{
                    if(Boolean.TRUE.equals(exists)) {
                        return Mono.error(new DuplicatedTechnologyException("El nombre de la tecnología ya existe"));
                    }
                    return technologyRepositoryGateway.create(technology);
                });
    }
}
