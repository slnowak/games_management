package com.novy.games.management.domain.common;

/**
 * Created by novy on 07.02.15.
 */
public abstract class IdentifiedObject<ID extends DomainIdentifier> {

    private final ID id;

    public IdentifiedObject(ID id) {
        this.id = id;
    }

    public ID id() {
        return id;
    }

    public boolean matchesId(ID otherId) {
        return id().equals(otherId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentifiedObject that = (IdentifiedObject) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
