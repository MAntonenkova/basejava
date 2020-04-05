package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;

import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializerStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();

            writeCollection(contacts.entrySet(), dataOutputStream, new elementWriter<Map.Entry<ContactType, String>>() {
                @Override
                public void write(Map.Entry<ContactType, String> contactTypeStringEntry) throws IOException {
                    dataOutputStream.writeUTF(contactTypeStringEntry.getKey().name());
                    dataOutputStream.writeUTF(contactTypeStringEntry.getValue());
                }
            });
            Map<SectionType, Section> sections = resume.getSections();

            writeCollection(sections.entrySet(), dataOutputStream, new elementWriter<Map.Entry<SectionType, Section>>() {
                @Override
                public void write(Map.Entry<SectionType, Section> sectionTypeSectionEntry) throws IOException {
                    SectionType key = sectionTypeSectionEntry.getKey();
                    Section value = sectionTypeSectionEntry.getValue();

                    dataOutputStream.writeUTF(key.name());

                    switch (key) {
                        case PERSONAL:
                        case OBJECTIVE: {
                            dataOutputStream.writeUTF(((TextSection) value).getContent());
                        }
                        break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS: {
                            List<String> items = ((ListSection) value).getItems();
                            writeCollection(items, dataOutputStream, dataOutputStream::writeUTF);
                        }
                        break;
                        case EXPERIENCE:
                        case EDUCATION: {
                            List<Organization> organizations = ((OrganizationSection) value).getOrganizations();
                            writeCollection(organizations, dataOutputStream, organization -> {
                                Link homepage = organization.getHomePage();                         // получаем элементы этого класса, Link и лист <Position>
                                List<Organization.Position> positions = organization.getPositions();    // получаем лист, где содержатся элементы: класс Organization
                                String name = homepage.getName();
                                String url = homepage.getUrl();
                                dataOutputStream.writeUTF(name);                 // 3.4 пишем name из Link
                                writeCheckNullString(dataOutputStream, url);    // 3.5 пишем url

                                writeCollection(positions, dataOutputStream, position -> {

                                    int startYear = position.getStartDate().getYear();
                                    int startMonth = position.getStartDate().getMonthValue();
                                    int endYear = position.getEndDate().getYear();
                                    int endMonth = position.getEndDate().getMonthValue();
                                    String title = position.getTitle();
                                    String description = position.getDescription();

                                    writeDate(dataOutputStream, startYear, startMonth);
                                    writeDate(dataOutputStream, endYear, endMonth);   // 3.9 пишем endYear   // 3.10 пишем endMonth
                                    writeCheckNullString(dataOutputStream, title);   // 3.11 пишем title
                                    writeCheckNullString(dataOutputStream, description);   // 3.12 пишем desctiption
                                });
                            });
                        }
                    }
                }
            });
        }
    }

    private void writeCheckNullString(DataOutputStream dataOutputStream, String str) throws IOException {
        if (str != null) {
            dataOutputStream.writeUTF(str);
        } else {
            dataOutputStream.writeUTF("");
        }
    }

    private void writeDate(DataOutputStream dataOutputStream, int Year, int Month) throws IOException {
        dataOutputStream.writeInt(Year);
        dataOutputStream.writeInt(Month);
        dataOutputStream.writeInt(1);
    }

    interface elementWriter<T> {
        void write(T t) throws IOException;
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dataOutputStream, elementWriter<T> writer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readMain(dataInputStream, new ElementMain() {
                @Override
                public void read() throws IOException {
                    resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
                }
            });


            readMain(dataInputStream, () -> {
                String keyName = dataInputStream.readUTF();
                switch (keyName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        resume.addSection(SectionType.valueOf(keyName), new TextSection(dataInputStream.readUTF()));
                        break;

                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> items = new ArrayList<>();
                        resume.addSection(SectionType.valueOf(keyName), new ListSection(items));
                        readCollection(items, dataInputStream, dataInputStream::readUTF);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Organization> organizations = new ArrayList<>();

                        readCollection(organizations, dataInputStream, () -> {

                            String name = dataInputStream.readUTF();
                            String url = dataInputStream.readUTF();
                            Link homepage = new Link(name, isNotNull(url));
                            List<Organization.Position> positions = new ArrayList<>();
                            readCollection(positions, dataInputStream, () -> {
                                LocalDate startDate = readDate(inputStream);
                                LocalDate endDate = readDate(inputStream);

                                String title = dataInputStream.readUTF();
                                String description = dataInputStream.readUTF();

                                return new Organization.Position(startDate, endDate, isNotNull(title), isNotNull(description));
                            });

                            return new Organization(homepage, positions);
                        });
                        resume.addSection(SectionType.valueOf(keyName), new OrganizationSection(organizations));
                        break;
                }
            });

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
        int month = dataInputStream.readInt();
        int day = dataInputStream.readInt();
        return LocalDate.of(year, month, day);
    }

    interface ElementReader<T> {
        T read() throws IOException;
    }

    interface ElementMain {
        void read() throws IOException;
    }

    private <T> void readCollection(List<T> list, DataInputStream dataInputStream, ElementReader<T> reader) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
    }

    private void readMain(DataInputStream dataInputStream, ElementMain reader) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }
}
