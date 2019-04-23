package com.circlee7.test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String prgmName;

    @NotEmpty
    private String theme;

    @NotEmpty
    private String serviceRegion;

    @NotEmpty
    private String prgmInfo;

    @NotEmpty
    private String prgmDescription;


}
