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
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private Resume resume1 = new Resume(UUID_1);
    private Resume resume2 = new Resume(UUID_2);
    private Resume resume3 = new Resume(UUID_3);
    private Resume resume4 =new Resume(UUID_4);

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void clearTest() {
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void updateTest() {
        storage.update(resume1);
        Assert.assertEquals(3, storage.getSize());
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateTestNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void saveTest() {
        storage.save(resume4);
        Assert.assertEquals(4, storage.getSize());
        Assert.assertEquals(resume4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveTestExist() {
        storage.save(resume1);
    }

    @Ignore
    @Test(expected = StorageException.class)
    public void saveOverflowTest() {
        storage.clear();
        try {
            for (int i = 0; i < 10_000; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume("dummy"));
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.getSize());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTestNotExistException() {
        storage.delete("dummy");
    }

    @Test
    public void getTest() {
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get("dummy");
    }

    @Test
    public void getAllTest() {
        Resume [] storageNew = {resume1, resume2, resume3};
        Assert.assertArrayEquals(storageNew, storage.getAll());
    }

    @Test
    public void getSizeTest() {
        Assert.assertEquals(3, storage.getSize());
    }
}