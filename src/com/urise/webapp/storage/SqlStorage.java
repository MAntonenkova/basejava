package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword)  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume;");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement preparedStatement = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        preparedStatement.setString(1, resume.getFullName());
                        preparedStatement.setString(2, resume.getUuid());
                        if (preparedStatement.executeUpdate() != 1) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }
                    deleteContacts(conn, resume);
                    deleteSections(conn, resume);
                    insertContacts(conn, resume);
                    insertSections(conn, resume);
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
                    insertContacts(conn, resume);
                    insertSections(conn, resume);
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
        return sqlHelper.transactionExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM resume WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContacts(resultSet, resume);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(" SELECT * FROM section WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addSections(resultSet, resume);
                }
            }

            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionExecute(connection -> {
            Map<String, Resume> resumesFromDb = new LinkedHashMap<>();

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    String fullName = resultSet.getString("full_name");
                    resumesFromDb.put(uuid, new Resume(uuid, fullName));
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = resumesFromDb.get(resultSet.getString("resume_uuid"));
                    addContacts(resultSet, resume);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = resumesFromDb.get(resultSet.getString("resume_uuid"));
                    addSections(resultSet, resume);
                }
            }
            return new ArrayList<>(resumesFromDb.values());
        });
    }

    private void addContacts(ResultSet resultSet, Resume resume) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
            resume.addContact(contactType, value);
        }
    }

    private void addSections(ResultSet resultSet, Resume resume) throws SQLException {
        String content = resultSet.getString("content");
        if (content != null) {
            SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));
            resume.addSection(sectionType, JsonParser.read(content, Section.class));    // json parsers TODO !!!
        }
    }

    public void setContacts(ResultSet resultSet, Resume resume) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(resultSet.getString("type"));
            resume.addContact(type, value);
        }
    }

    @Override
    public int getSize() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume ", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                Section section = e.getValue();
                ps.setString(3, JsonParser.write(section, Section.class));   // TODO!
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        deleteAttributes(conn, r, "DELETE  FROM contact WHERE resume_uuid=?");
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        deleteAttributes(conn, r, "DELETE  FROM section WHERE resume_uuid=?");
    }

    private void deleteAttributes(Connection conn, Resume r, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
        }
    }
}
