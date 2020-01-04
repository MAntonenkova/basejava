package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

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
    public void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    public void doSave(Resume resume, Object index) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, (Integer) index);
            size++;
        } else {
            throw new StorageException("Storage is overflow!", resume.getUuid());
        }
    }

    @Override
    public void doDelete(Object index) {
        fillDeletedElement((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    public List<Resume> getAllSorted() {
        return new ArrayList<>(Arrays.asList(storage));
    }

    @Override
    public int getSize() {
        return size;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume resume, int index);

    public boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract Integer getSearchKey(String uuid);

}
