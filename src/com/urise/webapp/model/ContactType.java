package com.urise.webapp.model;

import java.util.HashMap;
import java.util.Map;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    LINKEDINPROFIL("Профиль на LinkedIn"),
    GITHUBPROFIL("Профиль на GitHub"),
    STACKOVERFLOWPROFIL("Профиль на StackOverFlow"),
    HOMEPAGE("Домашняя страница");

    private String title;


    ContactType(String title) {
        this.title = title;
    }
}
