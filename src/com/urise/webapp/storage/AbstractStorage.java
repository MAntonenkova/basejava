package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void doUpdate(Resume resume, Object object);

    public abstract void doSave(Resume resume, Object object);

    public abstract void doDelete(Object object);

    public abstract Resume doGet(Object object);

    protected abstract Object getSearchKey(String uuid);

    public abstract boolean isExist(Object object);

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistedSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
