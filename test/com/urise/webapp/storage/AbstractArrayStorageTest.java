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

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1);
        RESUME_2 = new Resume(UUID_2);
        RESUME_3 = new Resume(UUID_3);
        RESUME_4 = new Resume(UUID_4);
    }

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clearTest() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void updateTest() {
        storage.update(RESUME_1);
        assertSize(3);
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateTestNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void saveTest() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.getSize());
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveTestExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowTest() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTest() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteTestNotExistException() {
        storage.delete("dummy");
    }

    @Test
    public void getTest() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest() {
        storage.get("dummy");
    }

    @Test
    public void getAllTest() {
        Resume[] storageNew = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(storageNew, storage.getAll());
    }

    @Test
    public void getSizeTest() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.getSize());
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}