package co.onclass.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe tener mas de 50 caracteres")
    private String name;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 90, message = "La descripcion no debe tener más de 90 caracteres")
    private String description;
}

