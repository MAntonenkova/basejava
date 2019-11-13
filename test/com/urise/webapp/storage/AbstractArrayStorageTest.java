package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";


    @Before
    public void setUp()  {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear()  {
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1);
        storage.update(resume);
        Assert.assertEquals(3, storage.getSize());
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(new Resume("uuid4"), storage.get("uuid4"));
    }

    @Test
    public void get() {
        //  storage.get("uuid1");
        Assert.assertEquals(new Resume(UUID_1), storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist()  {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete()  {
        storage.delete(UUID_1);
        storage.get(UUID_1);
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void getSize()  {
        Assert.assertEquals(3, storage.getSize());
    }

     @Ignore
    @Test(expected = StorageException.class)
    public void storageException()  {
        storage.clear();
        try {
            for (int i = 0; i < 10_000; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume("uuidNew"));
    }
}