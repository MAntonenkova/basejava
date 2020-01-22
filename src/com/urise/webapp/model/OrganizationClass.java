package com.urise.webapp.model;

import java.time.LocalDate;

public class OrganizationClass {
    public String companyName;
    public String urlAdress;
    public LocalDate startDate;
    public LocalDate endDate;
    public String position;
    public String positionDescription;

    public String getCompanyName() {
        return companyName;
    }

    public String getUrlAdress() {
        return urlAdress;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPosition() {
        return position;
    }

    public String getPositionDescription() {
        return positionDescription;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setUrlAdress(String urlAdress) {
        this.urlAdress = urlAdress;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }
}
