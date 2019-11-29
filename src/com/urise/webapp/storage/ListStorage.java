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


    public void updateResume(Resume resume, int index) {
        arrayList.set(index, resume);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        arrayList.set(index, resume);
    }

    @Override
    public void deleteResume(int index) {
        arrayList.remove(index);
    }

    public Resume[] getAll() {
        return arrayList.toArray(new Resume[getSize()]);
    }

    @Override
    public int getSize() {
        return arrayList.size();
    }

    @Override
    public int getIndex(String uuid) {
        for (Resume r : arrayList) {
            if (r.getUuid().equals(uuid)) {
                return arrayList.indexOf(r);
            }
        }
        return -1;
    }

    public Resume getResume(int index) {
        return arrayList.get(index);
    }

}
