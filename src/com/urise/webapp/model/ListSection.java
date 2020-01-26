package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

class ListSection implements Section {
    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "content must be not empty");
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "content=" + items +
                '}';
    }
}
