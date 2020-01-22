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

    private String phone;
    private String skype;
    private String email;
    private String linkedIn;
    private String gitHub;
    private String stackOverFlow;
    private String homePage;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public String getStackOverFlow() {
        return stackOverFlow;
    }

    public void setStackOverFlow(String stackOverFlow) {
        this.stackOverFlow = stackOverFlow;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public Map<ContactType, String> getContactContent() {
        return contactContent;
    }

    public void setContactContent(Map<ContactType, String> contactContent) {
        this.contactContent = contactContent;
    }

    public String getTitle() {
        return title;
    }

    ContactType(String title) {
        this.title = title;
    }

    Map<ContactType, String> contactContent = new HashMap<>();

    public void fillContactContent(){
        contactContent.put(PHONE, getPhone());
        contactContent.put(SKYPE, getSkype());
        contactContent.put(EMAIL, getSkype());
        contactContent.put(LINKEDINPROFIL, getLinkedIn());
        contactContent.put(GITHUBPROFIL, getGitHub());
        contactContent.put(STACKOVERFLOWPROFIL, getStackOverFlow());
        contactContent.put(HOMEPAGE, getHomePage());
    }
}
