package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DataStreamSerializer implements StreamSerializerStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dataOutputStream.writeUTF(entry.getKey().name());
                dataOutputStream.writeUTF(entry.getValue());
            }

            // TODO implements section

            Map<SectionType, Section> sections = resume.getSections();

            dataOutputStream.writeInt(sections.size());   // 1. пишем размер мапы Map<SectionType, Section> sections (для read)

            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {  // начало основного цикла, проходим по всем эл-там мапы Map<SectionType, Secton> sections

                SectionType key = entry.getKey();                 // определяем значение Key, т.е. Section Type
                Section value = entry.getValue();                 // определяем value, т.е. в самом сложном случае лист Organization Sections
                if ((key.equals(SectionType.PERSONAL)) || (key.equals(SectionType.OBJECTIVE))) {   // случай с value = TextSection
                    dataOutputStream.writeUTF(key.name());                  // 2.1 пишем значение Key
                    dataOutputStream.writeUTF(((TextSection) value).getContent());             // 3.1 пишем значение value
                } else if (key.equals(SectionType.ACHIEVEMENT) || (key.equals(SectionType.QUALIFICATIONS))) {    // случай с value = ListSection
                    dataOutputStream.writeUTF(key.name());                  // 2.2 пишем значение Key
                    for (String item : ((ListSection) value).getItems()) {
                        dataOutputStream.writeUTF(item);                     // 3.2 пишем значение value
                    }
                } else if (key.equals(SectionType.EXPERIENCE) || key.equals(SectionType.EDUCATION)) {         // самый сложный случай с value = OrganizationSection
                    // т.е. value = List<Organization> organizations

                    dataOutputStream.writeUTF(key.name());  // 2.3 пишем key, имя этой секции, например EDUCATION = "Образование"

                    List<Organization> organizations = ((OrganizationSection) value).getOrganizations(); // получаем лист, где содержатся элементы: класс Organization

                    int sizeOrganizations = organizations.size();          // получаем размер этого листа (класс Organization) для read
                    dataOutputStream.writeInt(sizeOrganizations);         // 3.3 пишем размер этого листа (класс Organization) для read

                    for (Organization organization : organizations) {   //  для каждого элемента List<Organization> organizations

                        Link homepage = organization.getHomePage();                         // получаем элементы этого класса, Link и лист <Position>
                        List<Organization.Position> positions = organization.getPositions();    // получаем лист, где содержатся элементы: класс Organization

                        String name = homepage.getName();


                        String url = homepage.getUrl();

                        dataOutputStream.writeUTF(name);                 // 3.4 пишем name из Link

                        if (url != null) {
                            dataOutputStream.writeUTF(url);                 // 3.5 пишем url из Link
                        } else {
                            dataOutputStream.writeUTF("");
                        }

                        // для каждого элемента List <Position> positions

                        int positionsSize = positions.size();

                        dataOutputStream.writeInt(positionsSize);     //3.6 пишем размер листа Positions

                        for (Organization.Position position : positions) {           // конструктор public Position(LocalDate startDate, LocalDate endDate, String title, String description)


                            int startYear = position.getStartDate().getYear();
                            int startMonth = position.getStartDate().getMonthValue();
                            int endYear = position.getEndDate().getYear();
                            int endMonth = position.getEndDate().getMonthValue();
                            String title = position.getTitle();
                            String description = position.getDescription();

                            dataOutputStream.writeInt(startYear);    // 3.7 пишем startYear
                            dataOutputStream.writeInt(startMonth);   // 3.8 пишем startMonth
                            dataOutputStream.writeInt(endYear);      // 3.9 пишем endYear
                            dataOutputStream.writeInt(endMonth);     // 3.10 пишем endMonth

                            if (title != null) {
                                dataOutputStream.writeUTF(title);        // 3.11 пишем title
                            } else {
                                dataOutputStream.writeUTF("");
                            }

                            if (description != null) {
                                dataOutputStream.writeUTF(description);  // 3.12 пишем description
                            } else {
                                dataOutputStream.writeUTF("");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }
            // TODO implements section

            int sizeSections = dataInputStream.readInt();                // 1. читаем размер мапы с секциями
            String keyName = dataInputStream.readUTF();                 // 2.1 читаем значение Key

            for (int i = 0; i < sizeSections; i++) {
                if (keyName.equals("PERSONAL") || keyName.equals("OBJECTIVE")) {
                    resume.addSection(SectionType.valueOf(keyName), new TextSection(dataInputStream.readUTF()));   // 3.1 читаем значение value
                } else if (keyName.equals("ACHIEVEMENT") || keyName.equals("QUALIFICATIONS")) {
                    resume.addSection(SectionType.valueOf(keyName), new ListSection(dataInputStream.readUTF()));    // 3.2 читаем значение value
                } else if (keyName.equals("EXPERIENCE") || keyName.equals("EDUCATION")) {

                    int organizationsSize = dataInputStream.readInt();                   // 3.3 читаем размер значения value листа с <class Organization>

                    List<Organization> organizations = new ArrayList<>();
                    for (int j = 0; j < organizationsSize; j++) {                   // для каждого элемента листа <Organization>

                        String name = dataInputStream.readUTF();                         //3.4 читаем name (String)
                        String url = dataInputStream.readUTF();                           //  3.5 читаем url (String)

                        if (url.equals("")) {
                            url = null;
                        }
                        Link homepage = new Link(name, url);                             // создаем  Link, содержащийся в каждом элементе листа <Organization>

                        List<Organization.Position> positions = new ArrayList<>();            // создаем  лист <Position>, содержащийся в каждом элементе листа <Organization>
                        int positionsSize = dataInputStream.readInt();      //  3.6 читаем размер листа positions <Position>

                        for (int k = 0; k < positionsSize; k++) {           // для каждого элемента Position листа positions читаем
                            int startYear = dataInputStream.readInt();         // // 3.7 читаем startYear
                            int startMonthInt = dataInputStream.readInt();        // 3.8 читаем startMonth
                            Month startMonth = Month.of(startMonthInt);

                            int endYear = dataInputStream.readInt();             // 3.9 читаем endYear
                            int endMonthInt = dataInputStream.readInt();           // 3.10 читаем endMonth
                            Month endMonth = Month.of(endMonthInt);

                            String title = dataInputStream.readUTF();     // 3.11 пишем title
                            if (title.equals("")) {
                                title = null;
                            }
                            String description = dataInputStream.readUTF();  // 3.12 пишем description
                            if (description.equals("")) {
                                description = null;
                            }

                            Organization.Position position = new Organization.Position(startYear, startMonth, endYear, endMonth, title, description);

                            positions.add(position);
                        }                   // для каждого элемента Position листа positions закрываем, лист positions собран
                        Organization organization = new Organization(homepage, positions);
                        organizations.add(organization);     //  лист organizatins <Organization> собран
                    }
                    resume.addSection(SectionType.valueOf(keyName), new OrganizationSection(organizations));
                }

            }
            return resume;
        }
    }
}
