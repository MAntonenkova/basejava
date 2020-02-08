package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    MAIL("E-mail"),
    LINKEDINPROFIL("Профиль на LinkedIn"),
    GITHUBPROFIL("Профиль на GitHub"),
    STACKOVERFLOWPROFIL("Профиль на StackOverFlow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
