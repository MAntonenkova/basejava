package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        if (elementExist(resume)) {
            updateResume(resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        if (elementExist(resume)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume);
        }
    }

    @Override
    public void delete(String uuid) {
        if (elementExist(uuid)) {
            deleteResume(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (elementExist(uuid)) {
            return getElement(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract void updateResume(Resume resume);

    public abstract void saveResume(Resume resume);

    public abstract void deleteResume(String uuid);

    public abstract boolean elementExist(Resume resume);

    public abstract boolean elementExist(String uuid);

    public abstract Resume getElement(String uuid);

    protected abstract int getIndex(String uuid);

}
