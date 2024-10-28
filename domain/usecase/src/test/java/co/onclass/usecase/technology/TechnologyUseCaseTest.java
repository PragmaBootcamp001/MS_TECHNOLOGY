package co.onclass.usecase.technology;

import co.onclass.model.technology.Technology;
import co.onclass.model.technology.errors.DuplicatedTechnologyException;
import co.onclass.model.technology.gateways.TechnologyRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TechnologyUseCaseTest {

    @Mock
    private TechnologyRepositoryGateway technologyRepositoryGateway;

    @InjectMocks
    private TechnologyUseCase technologyUseCase;

    private Technology technology;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        technology = Technology.builder()
                .name("Java")
                .description("Lenguaje de programación")
                .build();
    }

    @Test
    void registerTechnology_success() {
        when(technologyRepositoryGateway.existsByName(anyString())).thenReturn(Mono.just(false));
        when(technologyRepositoryGateway.create(technology)).thenReturn(Mono.just(technology));

        Mono<Technology> result = technologyUseCase.register(technology);

        StepVerifier.create(result)
                .expectNext(technology)
                .verifyComplete();

        verify(technologyRepositoryGateway).existsByName(technology.getName());
        verify(technologyRepositoryGateway).create(technology);
    }

    @Test
    void registerTechnology_duplicated() {
        when(technologyRepositoryGateway.existsByName(anyString())).thenReturn(Mono.just(true));


        Mono<Technology> result = technologyUseCase.register(technology);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof DuplicatedTechnologyException &&
                        throwable.getMessage().equals("El nombre de la tecnología ya existe"))
                .verify();

        verify(technologyRepositoryGateway).existsByName(technology.getName());
        verify(technologyRepositoryGateway, never()).create(any(Technology.class));
    }
}