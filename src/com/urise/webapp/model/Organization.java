package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final String companyName;
    private final String urlAddress;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String position;
    private final String positionDescription;

    public Organization(String companyName, String urlAddress, LocalDate startDate, LocalDate endDate, String position, String positionDescription) {
        Objects.requireNonNull(companyName, "companyName must be not empty");
        Objects.requireNonNull(startDate, "startDate must be not empty");
        Objects.requireNonNull(endDate, "endDate must be not empty");
        Objects.requireNonNull(position, "position must be not empty");

        this.companyName = companyName;
        this.urlAddress = urlAddress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.positionDescription = positionDescription;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getUrlAddress() {
        return urlAddress;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!Objects.equals(companyName, that.companyName)) return false;
        if (!Objects.equals(urlAddress, that.urlAddress)) return false;
        if (!Objects.equals(startDate, that.startDate)) return false;
        if (!Objects.equals(endDate, that.endDate)) return false;
        if (!Objects.equals(position, that.position)) return false;
        return Objects.equals(positionDescription, that.positionDescription);
    }

    @Override
    public int hashCode() {
        int result = companyName != null ? companyName.hashCode() : 0;
        result = 31 * result + (urlAddress != null ? urlAddress.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (positionDescription != null ? positionDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "companyName='" + companyName + '\'' +
                ", urlAddress='" + urlAddress + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", position='" + position + '\'' +
                ", positionDescription='" + positionDescription + '\'' +
                '}';
    }
}
