package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {

    private final Link homePage;

    private List<Position> positionList = new ArrayList<>();

    Organization(Link homePage, List<Position> positionList) {
        Objects.requireNonNull(positionList, "position must not be null");
        this.homePage = homePage;
        this.positionList = positionList;
    }

    Organization(String companyName, String ulr, Position... positions) {
        this(new Link(companyName, ulr), Arrays.asList(positions));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        return positionList != null ? positionList.equals(that.positionList) : that.positionList == null;
    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (positionList != null ? positionList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positionList=" + positionList +
                '}';
    }
}
