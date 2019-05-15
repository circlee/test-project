package com.circlee7.test.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProgram is a Querydsl query type for Program
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProgram extends EntityPathBase<Program> {

    private static final long serialVersionUID = -1828109641L;

    public static final QProgram program = new QProgram("program");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final StringPath id = createString("id");

    public final StringPath prgmDescription = createString("prgmDescription");

    public final StringPath prgmInfo = createString("prgmInfo");

    public final StringPath prgmName = createString("prgmName");

    public final SetPath<Region, QRegion> regionMapping = this.<Region, QRegion>createSet("regionMapping", Region.class, QRegion.class, PathInits.DIRECT2);

    public final StringPath serviceRegion = createString("serviceRegion");

    public final StringPath theme = createString("theme");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QProgram(String variable) {
        super(Program.class, forVariable(variable));
    }

    public QProgram(Path<? extends Program> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProgram(PathMetadata metadata) {
        super(Program.class, metadata);
    }

}

