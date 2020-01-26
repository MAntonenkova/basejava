package com.urise.webapp.model;

import java.util.Objects;

class TextSection implements Section {
    final private String content;

    public TextSection(String content) {
        Objects.requireNonNull(content, "content must be not empty");
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TextSection{" +
                "content='" + content + '\'' +
                '}';
    }
}
