package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    public void doUpdate(Resume resume, Object searchKey) {
        list.set((Integer) searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        list.add( resume);
    }

    @Override
    public void doDelete(Object searchKey) {
        list.remove(((Integer)searchKey).intValue());
    }

    public List<Resume> getAllSorted() {
        return list.subList(0, getSize());
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    public Resume doGet(Object index) {
        return list.get((Integer) index);
    }

    public boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
