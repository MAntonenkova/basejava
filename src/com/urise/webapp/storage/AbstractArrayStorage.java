package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10_000;

    Resume[] storage = new Resume[STORAGE_LIMIT];
    int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void updateResume(Resume resume, Object object) {
        storage[(int)object] = resume;
    }

    @Override
    public void saveResume(Resume resume, Object object) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, (int)object);
            size++;
        } else {
            throw new StorageException("Storage is overflow!", resume.getUuid());
        }
    }

    @Override
    public void deleteResume(Object obj) {
            fillDeletedElement((int)obj);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume getResume(Object object) {
        return storage[(int)object];
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int getSize() {
        return size;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume resume, int index);

    public  boolean isExist(Object object){
        return (int)object >= 0;
    }



}
