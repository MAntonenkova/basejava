package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume;");
    }


    @Override
    public void update(Resume resume) {    // не меняется
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement preparedStatement = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        preparedStatement.setString(1, resume.getFullName());
                        preparedStatement.setString(2, resume.getUuid());
                        preparedStatement.execute();
                    }
                    try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                        preparedStatement.setString(1, resume.getUuid());
                        preparedStatement.execute();
                    }
                    try (PreparedStatement preparedStatement = conn.prepareStatement("UPDATE contact SET type = ?, value = ? WHERE resume_uuid = ?")) {
                        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                            preparedStatement.setString(1, e.getKey().name());
                            preparedStatement.setString(2, e.getValue());
                            preparedStatement.setString(3, resume.getUuid());
                            preparedStatement.addBatch();
                        }
                        preparedStatement.executeBatch();
                    }
                    return null;
                }
        );
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                            ps.setString(1, resume.getUuid());
                            ps.setString(2, e.getKey().name());
                            ps.setString(3, e.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>execute("DELETE FROM resume r WHERE r.uuid = (?)", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                " SELECT * FROM resume r" +
                "  LEFT JOIN contact c" +
                "    ON r.uuid = c.resume_uuid" +
                " WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                String value = resultSet.getString("value");
                if (value == null) {
                    break;
                }
                ContactType type = ContactType.valueOf(resultSet.getString("type"));
                resume.addContact(type, value);
            } while (resultSet.next());
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {      // написать
        return sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY full_name, uuid  ", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            Map<String, Resume> resumesFromDb = new HashMap<>();

            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String fullName = resultSet.getString("full_name");
                String value = resultSet.getString("value");
                if (value !=null){
                    ContactType type = ContactType.valueOf(resultSet.getString("type"));
                }


                if (resumesFromDb.containsKey(uuid)) {         // если в базе уже есть это резюме
                    if (value != null) {                         // если контакт не нулевой
                        ContactType type = ContactType.valueOf(resultSet.getString("type"));
                        resumesFromDb.get(uuid).getContacts().put(type, value);     // кидаем в резюме которое уже в базе новый контакт
                    }
                } else if (!resumesFromDb.containsKey(uuid)) {  // если в базе нет такого резюме
                    resumesFromDb.computeIfAbsent(uuid, b -> new Resume(uuid, fullName));
                  Resume resume = new Resume(uuid, fullName);   // создаем новое резюме
                    if (value != null) {                              // если контакт не нулевой
                        ContactType type = ContactType.valueOf(resultSet.getString("type"));
                        resume.getContacts().put(type, value);       // кидаем в резюме новый контакт
                    }
                    resumesFromDb.put(uuid, resume);              // в любом случае кидаем новое резюме в нашу карту маркер
                     resumes.add(resume);
                }
            }
              return resumes;
        //    return new ArrayList<>(resumesFromDb.values());
        });
    }

    @Override
    public int getSize() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume ", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }
}
