package com.urise.webapp.model;

import java.time.LocalDate;

public class OrganizationClass {
    private String companyName;
    private String urlAdress;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String positionDescription;

    OrganizationClass(String companyName, String urlAdress, LocalDate startDate, LocalDate endDate, String position, String positionDescription) {
        this.companyName = companyName;
        this.urlAdress = urlAdress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.positionDescription = positionDescription;
    }

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
