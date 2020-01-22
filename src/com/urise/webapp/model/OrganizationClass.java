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
}
