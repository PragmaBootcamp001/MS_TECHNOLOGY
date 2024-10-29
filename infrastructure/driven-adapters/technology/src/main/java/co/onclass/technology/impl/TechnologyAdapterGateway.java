package co.onclass.technology.impl;

import co.onclass.model.technology.Technology;
import co.onclass.model.technology.gateways.TechnologyRepositoryGateway;
import co.onclass.technology.entity.TechnologyEntity;
import co.onclass.technology.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
                Technology.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .description(e.getDescription())
                        .build());
    }

    @Override
    public Mono<List<Technology>> list(Integer offset, Integer limit, Boolean isAscending) {
        Flux<TechnologyEntity> query = technologyRepository.findAll();

        query = Boolean.TRUE.equals(isAscending)
                ? query.sort(Comparator.comparing(TechnologyEntity::getName))
                : query.sort(Comparator.comparing(TechnologyEntity::getName).reversed());

        return query
                .skip(offset)
                .take(limit)
                .collectList()
                .map(entityList -> entityList.stream()
                        .map(entity -> Technology.builder()
                                .id(entity.getId())
                                .name(entity.getName())
                                .description(entity.getDescription())
                                .build())
                        .collect(Collectors.toList()));
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return technologyRepository.existsByName(name);
    }
}
