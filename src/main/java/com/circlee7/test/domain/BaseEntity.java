package com.circlee7.test.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@MappedSuperclass
public class BaseEntity implements Serializable {


    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private Long createdBy;

    private LocalDateTime updatedAt;

    private Long updatedBy;

}
