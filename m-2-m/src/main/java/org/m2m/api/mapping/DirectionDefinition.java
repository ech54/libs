package org.m2m.api.mapping;

import java.util.Objects;

public class DirectionDefinition implements Direction {

    private Class<?> from;
    private Class<?> to;

    public DirectionDefinition(final Class<?> from, final Class<?> to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(final Object object) {
        if (Objects.isNull(object)||!Direction.class.isInstance(object)) {
            return false;
        }
        final Direction direction = (Direction) object;
        return direction.getFrom().equals(this.getFrom())
                &&direction.getTo().equals(this.getTo());
    }
    @Override
    public int hashCode() {
        return getTo().hashCode()+16*getFrom().hashCode();
    }


    @Override
    public Class<?> getFrom() {
        return this.from;
    }

    @Override
    public Class<?> getTo() {
        return this.to;
    }

    @Override
    public boolean inverse(final Direction direction) {
        if (Objects.isNull(direction)||!Direction.class.isInstance(direction)) {
            return false;
        }
        return direction.getFrom().equals(this.getTo())
                &&direction.getTo().equals(this.getFrom());
    }
}
