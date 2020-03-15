package com.urise.webapp.storage.serializer;

import com.google.gson.internal.bind.util.ISO8601Utils;
import com.urise.webapp.model.*;

import java.beans.Customizer;
import java.io.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Consumer;

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

            Map<SectionType, Section> sections = resume.getSections();

            dataOutputStream.writeInt(sections.size());   // 1. пишем размер мапы Map<SectionType, Section> sections (для read)

            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {  // начало основного цикла, проходим по всем эл-там мапы Map<SectionType, Secton> sections

                SectionType key = entry.getKey();                 // определяем значение Key, т.е. Section Type
                Section value = entry.getValue();                 // определяем value, т.е. в самом сложном случае лист Organization Sections

                dataOutputStream.writeUTF(key.name());  // пишем key, имя этой секции, например EDUCATION = "Образование"

                switch (key) {
                    case PERSONAL:                                              // случай с value = TextSection
                    case OBJECTIVE: {

                        dataOutputStream.writeUTF(((TextSection) value).getContent());             // 3.1 пишем значение value
                    }
                    break;

                    case ACHIEVEMENT:                                            // случай с value = ListSection
                    case QUALIFICATIONS: {

                        List<String> items = ((ListSection) value).getItems();
                        int itemSize = items.size();
                        dataOutputStream.writeInt(itemSize);            // 2.3 пишем значение size для List для read
                        for (String item : items) {
                            dataOutputStream.writeUTF(item);                     // 3.2 пишем значение value
                        }
                    }
                    break;

                    case EXPERIENCE:                                   // самый сложный случай с value = OrganizationSection
                    case EDUCATION: {
                        // т.е. value = List<Organization> organizations
                        List<Organization> organizations = ((OrganizationSection) value).getOrganizations(); // получаем лист, где содержатся элементы: класс Organization


                        int sizeOrganizations = organizations.size();          // получаем размер этого листа (класс Organization) для read
                        dataOutputStream.writeInt(sizeOrganizations);         // 3.3 пишем размер этого листа (класс Organization) для read

                        for (Organization organization : organizations) {   //  для каждого элемента List<Organization> organizations

                            Link homepage = organization.getHomePage();                         // получаем элементы этого класса, Link и лист <Position>
                            List<Organization.Position> positions = organization.getPositions();    // получаем лист, где содержатся элементы: класс Organization

                            String name = homepage.getName();

                            String url = homepage.getUrl();

                            dataOutputStream.writeUTF(name);                 // 3.4 пишем name из Link

                            writeCheckNullString(outputStream, url);    // 3.5 пишем url

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

                                writeDate(outputStream, startYear, startMonth);    // 3.7 пишем startYear   // 3.8 пишем startMonth

                                writeDate(outputStream, endYear, endMonth);   // 3.9 пишем endYear   // 3.10 пишем endMonth

                                writeCheckNullString(outputStream, title);   // 3.11 пишем title

                                writeCheckNullString(outputStream, description);   // 3.12 пишем desctiption

                            }
                        }
                    }
                }
            }
        }
    }

    private void writeCheckNullString(OutputStream outputStream, String str) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        if (str != null) {
            dataOutputStream.writeUTF(str);  // 3.12 пишем description
        } else {
            dataOutputStream.writeUTF("");
        }
    }

    private void writeDate(OutputStream outputStream, int Year, int Month) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(Year);
        dataOutputStream.writeInt(Month);
        dataOutputStream.writeInt(1);
    }

    @FunctionalInterface
    interface myCustomer extends Consumer {
        default void accept() throws Exception {
        }
    }

    public void writeWithException(Collection collection, myCustomer action, OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        Objects.requireNonNull(action);
        for (Object element : collection) {
            dataOutputStream.writeInt((Integer) element);
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

            int sizeSections = dataInputStream.readInt();                // 1. читаем размер мапы с секциями

            for (int i = 0; i < sizeSections; i++) {
                String keyName = dataInputStream.readUTF();                 // 2.1 читаем значение Key
                switch (keyName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        resume.addSection(SectionType.valueOf(keyName), new TextSection(dataInputStream.readUTF()));   // 3.1 читаем значение value
                        break;

                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        int itemSize = dataInputStream.readInt();               // 2.3 читаем значение itemSize

                        List<String> items = new ArrayList<>();
                        for (int j = 0; j < itemSize; j++) {
                            items.add(dataInputStream.readUTF());     // 3.2 читаем значение value
                        }
                        resume.addSection(SectionType.valueOf(keyName), new ListSection(items));    // 3.2 читаем значение value
                        //   resume.addSection(SectionType.valueOf(keyName), new ListSection(dataInputStream.readUTF()));

                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":

                        int organizationsSize = dataInputStream.readInt();                   // 3.3 читаем размер значения value листа с <class Organization>

                        List<Organization> organizations = new ArrayList<>();
                        for (int j = 0; j < organizationsSize; j++) {                   // для каждого элемента листа <Organization>

                            String name = dataInputStream.readUTF();                         //3.4 читаем name (String)
                            String url = dataInputStream.readUTF();                           //  3.5 читаем url (String)

                            Link homepage = new Link(name, isNotNull(url));                             // создаем  Link, содержащийся в каждом элементе листа <Organization>

                            List<Organization.Position> positions = new ArrayList<>();            // создаем  лист <Position>, содержащийся в каждом элементе листа <Organization>
                            int positionsSize = dataInputStream.readInt();      //  3.6 читаем размер листа positions <Position>

                            for (int k = 0; k < positionsSize; k++) {           // для каждого элемента Position листа positions читаем

                                LocalDate startDate = readDate(inputStream);
                                LocalDate endDate = readDate(inputStream);

                                String title = dataInputStream.readUTF();     // 3.11 пишем title
                                String description = dataInputStream.readUTF();  // 3.12 пишем description

                                Organization.Position position = new Organization.Position(startDate, endDate, isNotNull(title), isNotNull(description));

                                positions.add(position);
                            }
                            // для каждого элемента Position листа positions закрываем, лист positions собран
                            Organization organization = new Organization(homepage, positions);
                            organizations.add(organization);     //  лист organizatins <Organization> собран
                        }
                        resume.addSection(SectionType.valueOf(keyName), new OrganizationSection(organizations));
                        break;
                }
            }
            return resume;
        }
    }

    private String isNotNull(String str) {
        if (str.equals("")) {
            return null;
        }
        return str;
    }

    private LocalDate readDate(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        int year = dataInputStream.readInt();
        int month = dataInputStream.readInt();           // читаем Month
        int day = dataInputStream.readInt();
        return LocalDate.of(year, month, day);
    }
}
