package co.onclass.model.technology;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Technology {
    private Integer id;
    private String name;
    private String description;
}
