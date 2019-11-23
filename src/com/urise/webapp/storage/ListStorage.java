package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    private List<Resume> arrayList = new ArrayList<>();

    @Override
    public void clear() {
        arrayList.clear();
    }

    @Override
    public void updateResume(int index, Resume resume) {
        arrayList.set(index, resume);
    }

    @Override
    public void saveResume(int index, Resume resume) {
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            arrayList.set(index, resume);
        }
    }

    @Override
    public void deleteResume(int index) {
        arrayList.remove(index);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return arrayList.get(index);
    }

    public Resume[] getAll() {
        return null;
    }

    @Override
    public int getSize() {
        return arrayList.size();
    }

    @Override
    protected int getIndex(String uuid) {
        return arrayList.indexOf(get(uuid));
    }

    private int getIndex(Resume resume) {
        return arrayList.indexOf(resume);
    }
}
