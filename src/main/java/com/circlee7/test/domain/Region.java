package com.circlee7.test.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Region implements Serializable {



    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.circlee7.test.util.StringPrefixTableGenerator", parameters = {
            @Parameter(name = "table_name", value = "new_key_numbers2"),
            @Parameter(name = "value_column_name", value = "key_number"),
            @Parameter(name = "segment_column_name", value = "name"),
            @Parameter(name = "segment_value", value = "names_key"),
            @Parameter(name = "prefix_key", value = "reg")})
    private String id;

    @NotEmpty
    @Column(nullable = false)
    private String regionName;

    private String rootName;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "regionMapping")
    Set<Program> programs;


    public String getFullRegionName() {
        return Optional.ofNullable(getRootName())
                .map(s -> s + " ")
                .orElse("")
                + getRegionName();
    }

}
