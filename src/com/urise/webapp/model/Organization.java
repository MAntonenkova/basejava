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

    public String geturlAddress() {
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

        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (urlAddress != null ? !urlAddress.equals(that.urlAddress) : that.urlAddress != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        return positionDescription != null ? positionDescription.equals(that.positionDescription) : that.positionDescription == null;
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
