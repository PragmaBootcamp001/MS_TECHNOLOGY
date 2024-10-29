package co.onclass.api;
import co.onclass.api.dtos.TechnologyRequest;
import co.onclass.model.technology.Technology;
import co.onclass.usecase.technology.TechnologyUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/technologies", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TechnologyApiRest {

    private final TechnologyUseCase technologyUseCase;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> createTechnology(
            @Valid @RequestBody Mono<TechnologyRequest> monoTechnologyRequest) {
        Map<String, Object> response = new HashMap<>();

        return monoTechnologyRequest.flatMap(technologyRequestModel -> technologyUseCase.register(Technology
                .builder()
                .name(technologyRequestModel.getName())
                .description(technologyRequestModel.getDescription())
                .build()).map(monoTechnology -> {
            response.put("technology", monoTechnology);
            return ResponseEntity
                    .created(URI.create("/api/technologies/".concat(monoTechnology.getId().toString())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }))
            .onErrorResume(WebExchangeBindException.class, e -> Mono.just(e.getFieldErrors())
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        response.put("errors", list);
                        return Mono.just(ResponseEntity.badRequest().body(response));
                    }))
            .onErrorResume(t -> {
                response.put("errors", List.of(t.getMessage()));
                return Mono.just(ResponseEntity.badRequest().body(response));
            });
    }

    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> listTechnologies(
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "10") int size,
            @RequestParam(name="isAscending", defaultValue = "true") boolean isAscending) {

        return technologyUseCase.list(page, size, isAscending)
                .map(technologies -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("technologies", technologies);
                    response.put("page", page);
                    response.put("size", size);
                    response.put("isAscending", isAscending);
                    return ResponseEntity.ok(response);
                });
    }
}
