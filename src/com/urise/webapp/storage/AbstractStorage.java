package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractStorage implements Storage {

    public abstract void clear();

    public abstract void update(Resume resume);

    public abstract void save(Resume resume);

    public abstract Resume get(String uuid);

    public abstract void delete(String uuid);

  //  public abstract Resume[] getAll();

    public abstract int getSize();

    protected abstract int getIndex(String uuid);

}
