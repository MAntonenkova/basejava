package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ExperienceSection implements Section {

    private List<OrganizationClass> content;

    ExperienceSection(List<OrganizationClass> content) {
        this.content = content;
    }
}
