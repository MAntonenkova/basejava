package com.urise.webapp.model;

import java.util.HashMap;
import java.util.Map;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

  Map<SectionType, Section> resumeContent = new HashMap<SectionType, Section>();



    public void fillResumeContent(){
        resumeContent.put(PERSONAL, new TextClass());
        resumeContent.put(OBJECTIVE, new TextClass());
        resumeContent.put(ACHIEVEMENT, new ListClass());
        resumeContent.put(QUALIFICATIONS, new ListClass());
        resumeContent.put(EXPERIENCE, new ExperienceSection());
        resumeContent.put(EDUCATION, new ExperienceSection());
    }

    }


