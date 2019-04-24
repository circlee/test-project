package com.circlee7.test.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegion is a Querydsl query type for Region
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRegion extends EntityPathBase<Region> {

    private static final long serialVersionUID = 263139425L;

    public static final QRegion region = new QRegion("region");

    public final StringPath id = createString("id");

    public final SetPath<Program, QProgram> programs = this.<Program, QProgram>createSet("programs", Program.class, QProgram.class, PathInits.DIRECT2);

    public final StringPath regionName = createString("regionName");

    public final StringPath rootName = createString("rootName");

    public QRegion(String variable) {
        super(Region.class, forVariable(variable));
    }

    public QRegion(Path<? extends Region> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegion(PathMetadata metadata) {
        super(Region.class, metadata);
    }

}

