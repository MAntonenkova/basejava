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
    public void updateResume(Resume resume, int index) {
        //  int index = getIndex(resume.getUuid());
        storage[index] = resume;
    }

    @Override
    public void saveResume(Resume resume, int index) {
        //  int index = getIndex(resume.getUuid());
        if (size < STORAGE_LIMIT) {
            insertElement(resume, index);
            size++;
        } else {
            throw new StorageException("Storage is overflow!", resume.getUuid());
        }
    }

    @Override
    public void deleteResume(String uuid, int index) {
        // int index = getIndex(uuid);
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume getElement(int index) {
        return storage[index];
    }

/*    @Override
    public boolean elementExist(Resume resume) {
        int index = getIndex(resume.getUuid());
        return index >= 0;
    }*/

    /*@Override
    public boolean elementExist(String uuid) {
        int index = getIndex(uuid);
        return index >= 0;
    }*/

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int getSize() {
        return size;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume resume, int index);
}
