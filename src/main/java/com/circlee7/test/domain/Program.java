package com.circlee7.test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

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
