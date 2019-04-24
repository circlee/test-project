package com.circlee7.test.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Program extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7358242943473807765L;

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "string_prefix_generator")
    @GenericGenerator(name = "string_prefix_generator", strategy = "com.circlee7.test.util.StringPrefixTableGenerator", parameters = {
            @Parameter(name = "table_name", value = "new_key_numbers"),
            @Parameter(name = "value_column_name", value = "key_number"),
            @Parameter(name = "segment_column_name", value = "name"),
            @Parameter(name = "segment_value", value = "names_key"),
            @Parameter(name = "prefix_key", value = "prg")})
    private String id;

    @NotEmpty
    @Column(nullable = false)
    private String prgmName;

    @NotEmpty
    @Column(nullable = false)
    private String theme;

    @NotEmpty
    @Column(nullable = false)
    private String serviceRegion;

    @Column(length = 1000)
    private String prgmInfo;

    @Column(length = 3000)
    private String prgmDescription;

    @EqualsAndHashCode.Exclude
    @ManyToMany()
    @JoinTable(
            name = "program_mapping",
            joinColumns = @JoinColumn(name = "program_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "region_id", referencedColumnName = "id"))
    Set<Region> regionMapping;


}
