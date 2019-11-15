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
    private Resume resume;
    private Resume resume4;
    Resume [] storageNew;


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp()  {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Before
    public void createNewResume() {
        resume = new Resume(UUID_1);
    }

    @Before
    public void createNewResume4() {
        resume4 = new Resume("uuid4");
    }

    @Before
    public void createNewArray(){
         storageNew = new Resume[3];
        storageNew[0] =new Resume(UUID_1);
        storageNew[1] =new Resume(UUID_2);
        storageNew[2] =new Resume(UUID_3);
    }


    @Test
    public void clearTest()  {
        storage.clear();
        Assert.assertEquals(0, storage.getSize());
    }

    @Test
    public void updateTest() {
        storage.update(resume);
        Assert.assertEquals(3, storage.getSize());
    }

    @Test
    public void saveTest() {
        storage.save(resume4);
        Assert.assertEquals(4, storage.getSize());
    }

    @Test
    public void getTest() {
        Assert.assertEquals(resume, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExistTest()  {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void getExistTest() throws Exception {
        storage.save(resume);
    }

    @Test
    public void deleteTest()  {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.getSize());
    }

    @Test
    public void getAllTest() {
        Assert.assertEquals(storageNew , storage.getAll());
    }

    @Test
    public void getSizeTest()  {
        Assert.assertEquals(3, storage.getSize());
    }

     @Ignore
    @Test(expected = StorageException.class)
    public void storageExceptionTest()  {
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