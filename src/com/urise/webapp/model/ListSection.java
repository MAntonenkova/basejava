package com.urise.webapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section implements Serializable {
    private static final long serialVersionUID = 1L;

    static final ListSection EMPTY = new ListSection("");

    private List<String> items;

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection() {
    }

    public ListSection(List<String> items) {
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
