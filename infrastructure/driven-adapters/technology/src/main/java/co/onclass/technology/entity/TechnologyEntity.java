package co.onclass.technology.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tecnologias")
public class TechnologyEntity {

    @Id
    private Integer id;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}

