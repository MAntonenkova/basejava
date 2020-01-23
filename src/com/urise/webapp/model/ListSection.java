package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

class ListSection implements Section {
    private List<String> content;

    ListSection(List<String> content) {
        this.content = content;
    }
}
