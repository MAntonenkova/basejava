package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10_000;

    Resume[] storage = new Resume[STORAGE_LIMIT];
    int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("Resume is absent in base");
        }
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume" + resume.getUuid() + " is already in base");
        } else if (size < STORAGE_LIMIT) {
            insert(resume, findIndex(resume));
            size++;
        } else {
            System.out.println("Storage is overflow!");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            remove(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Resume is absent in base");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int getSize() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void remove(int index);

  //  protected abstract void insert(Resume resume);

    protected abstract void insert(Resume resume, int index);

    protected abstract int findIndex(Resume resume);

}
