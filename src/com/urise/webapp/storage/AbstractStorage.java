package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object key = getIndex(resume.getUuid());
        if (isExist(key)) {
            updateResume(resume, key);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        Object key = getIndex(resume.getUuid());
        if (isExist(key)) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, key);
        }
    }

    @Override
    public void delete(String uuid) {
        Object key = getIndex(uuid);
        if (isExist(key)) {
            deleteResume(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object key = getIndex(uuid);
        if (isExist(key)) {
            return getResume(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract void updateResume(Resume resume, Object object);

    public abstract void saveResume(Resume resume, Object object);

    public abstract void deleteResume(Object object);

    public abstract Resume getResume(Object object);

    protected abstract Object getIndex(String uuid);

    public abstract boolean isExist(Object object);

}
