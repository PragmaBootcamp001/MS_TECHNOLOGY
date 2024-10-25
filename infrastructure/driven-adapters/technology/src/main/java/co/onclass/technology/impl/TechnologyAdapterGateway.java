package co.onclass.technology.impl;

import co.onclass.model.technology.Technology;
import co.onclass.model.technology.gateways.TechnologyRepositoryGateway;
import co.onclass.technology.entity.TechnologyEntity;
import co.onclass.technology.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TechnologyAdapterGateway implements TechnologyRepositoryGateway {

    private final TechnologyRepository technologyRepository;

    @Override
    public Mono<Technology> create(Technology technology) {
        return technologyRepository.save(TechnologyEntity
                .builder()
                        .name(technology.getName())
                        .description(technology.getDescription())
                .build()).map(e->
                Technology
                        .builder()
                        .id(e.getId())
                        .name(e.getName())
                        .description(e.getDescription())
                        .build());
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return technologyRepository.existsByName(name);
    }
}
