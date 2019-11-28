package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            updateResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveResume(resume, index);
        }
    }

 /*   @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (elementExist(uuid)) {
            deleteResume(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }*/

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(uuid, index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return getElement(index);      // getElement - убрать uuid?
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public abstract void updateResume(Resume resume, int index);

    public abstract void saveResume(Resume resume, int index);

    public abstract void deleteResume(String uuid, int index);

    //  public abstract boolean elementExist(Resume resume);

    //   public abstract boolean elementExist(String uuid);

    public abstract Resume getElement(int index);

    protected abstract int getIndex(String uuid);

}
