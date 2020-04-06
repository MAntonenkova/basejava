package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.storage.serializer.DataStreamSerializer;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest(){
        super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "root"));
    }
}

