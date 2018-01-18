package org.m2m.api.mapping;

public interface Direction {

    Class<?> getFrom();

    Class<?> getTo();

    boolean inverse(Direction direction);
}
