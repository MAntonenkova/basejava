package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    private List<Resume> arrayList = new ArrayList<>();

    public void clear() {
        arrayList.clear();
    }

    @Override
    public void update(Resume resume) {
        if (arrayList.contains(resume)) {
            int index = getIndex(resume);
            arrayList.set(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume);
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            arrayList.set(index, resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return arrayList.get(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            arrayList.remove(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public List<Resume> getAll() {
        return arrayList;
    }

    @Override
    public int getSize() {
        return arrayList.size();
    }

    @Override
    protected  int getIndex(String uuid){
        return arrayList.indexOf(get(uuid));
    }

    private int getIndex(Resume resume){
        return arrayList.indexOf(resume);
    }
}
