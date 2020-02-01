package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

class ListSection extends Section {
    private final List<String> items;

    ListSection(List<String> items) {
        Objects.requireNonNull(items, "content must be not empty");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "content=" + items +
                '}';
    }
}
