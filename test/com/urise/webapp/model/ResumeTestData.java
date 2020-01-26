package com.urise.webapp.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов." +
                " Более 1000 выпускников. ");

        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.  ");

        achievementsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера. ");

        achievementsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), " +
                "Commet, HTML5, Highstock для алгоритмического трейдинга. ");

        achievementsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, " +
                "JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента" +
                " для администрирования и мониторинга системы по JMX (Jython/ Django). ");

        achievementsList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк)," +
                " Белоруcсии(Erip, Osmp) и Никарагуа.");

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce  ");
        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qualificationsList.add("MySQL, SQLite, MS SQL, HSQLDB  ");

        List<Organization> experienceList = new ArrayList<>();

        SimpleDateFormat DateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        Organization experience1 = new Organization("Alcatel", "www.alcatel.ru", LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1), "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");

        Organization experience2 = new Organization("Siemens AG", "https://new.siemens.com/ru/ru.html",
                LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе" +
                        " Siemens @vantage (Java, Unix).");

        experienceList.add(experience1);
        experienceList.add(experience2);

        List<Organization> educationList = new ArrayList<>();

        Organization education1 = new Organization("Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/", LocalDate.of(1984, 9, 1),
                LocalDate.of(1987, 6, 1), "Закончил с отличием",
                "");

        Organization education2 = new Organization("Санкт-Петербургский национальный исследовательский университет" +
                "информационных технологий, механики и оптики",
                "http://www.ifmo.ru/ru/", LocalDate.of(1993, 9, 1),
                LocalDate.of(1996, 7, 1), "Закончил с отличием",
                "");

        educationList.add(education1);
        educationList.add(education2);

        Map<SectionType, Section> sections = resume.getSections();
        Section textClassPersonal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры. ");
        Section textClassObjective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        Section listClassAchievement = new ListSection(achievementsList);
        Section listClassQualifications = new ListSection(qualificationsList);
        Section listClassExperience = new ExperienceSection(experienceList);
        Section listClassEducation = new ExperienceSection(educationList);

        sections.put(SectionType.PERSONAL, textClassPersonal);
        sections.put(SectionType.OBJECTIVE, textClassObjective);
        sections.put(SectionType.ACHIEVEMENT, listClassAchievement);
        sections.put(SectionType.QUALIFICATIONS, listClassQualifications);
        sections.put(SectionType.EXPERIENCE, listClassExperience);
        sections.put(SectionType.EDUCATION, listClassEducation);

        Map<ContactType, String> contacts = resume.getContacts();

        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDINPROFIL, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUBPROFIL, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOWPROFIL, "https://stackoverflow.com/users/548473/grigory-kislin");
        contacts.put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        System.out.println(resume);
    }
}