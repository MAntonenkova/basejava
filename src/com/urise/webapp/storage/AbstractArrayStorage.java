package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

 /*   private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            if (!(o1.getFullName().equals(o2.getFullName()))){
                return o1.getFullName().compareTo(o2.getFullName());
            }
            else return o1.getUuid().compareTo(o2.getUuid());
        }
    };*/

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, (Integer) index);
            size++;
        } else {
            throw new StorageException("Storage is overflow!", resume.getUuid());
        }
    }

    @Override
    protected void doDelete(Object index) {
        fillDeletedElement((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Object index) {
        return storage[(Integer) index];
    }


    @Override
    public List<Resume> getAllSorted() {
         Resume[] storageB = Arrays.copyOfRange(storage, 0, getSize());
        List<Resume> listNewA = Arrays.asList(storageB);
         listNewA.sort(RESUME_COMPARATOR);
         return listNewA;
    }

    @Override
    public int getSize() {
        return size;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume resume, int index);

    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

}
