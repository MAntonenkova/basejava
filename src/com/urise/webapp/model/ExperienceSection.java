package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ExperienceSection extends OrganizationClass implements Section {

    private List<OrganizationClass> organizations = new ArrayList<>();

    private OrganizationClass organization = new OrganizationClass();

    private void fillOrganization(){
      organizations.add(organization);
    }


}
