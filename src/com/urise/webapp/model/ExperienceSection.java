package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

class ExperienceSection implements Section {
    private final List<Organization> organizations;

    public ExperienceSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must be not empty");
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations != null ? organizations.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ExperienceSection{" +
                "organizations=" + organizations +
                '}';
    }
}
