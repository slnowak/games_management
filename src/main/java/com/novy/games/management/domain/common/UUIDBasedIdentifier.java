package com.novy.games.management.domain.common;

import java.util.UUID;

/**
 * Created by novy on 07.02.15.
 */
public abstract class UUIDBasedIdentifier implements DomainIdentifier {

    private final String id;

    public UUIDBasedIdentifier(String id) {
        this.id = id;
    }

    public UUIDBasedIdentifier() {
        this.id = randomUUIDString();
    }

    private String randomUUIDString() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UUIDBasedIdentifier that = (UUIDBasedIdentifier) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
