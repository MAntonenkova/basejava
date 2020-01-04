package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> hashMap = new HashMap<>();

    @Override
    public void clear() {
        hashMap.clear();
    }

    public void doUpdate(Resume resume, Object searchKey) {
        hashMap.put((String) searchKey, resume);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        hashMap.put((String) searchKey, resume);
    }

    @Override
    public void doDelete(Object searchKey) {
        String key = (String) searchKey;
        hashMap.remove(key);
    }

    public List<Resume> getAllSorted() {
        return new ArrayList<>(hashMap.values());
    }

    @Override
    public int getSize() {
        return hashMap.size();
    }

    @Override
    public Object getSearchKey(String uuid) {
        return uuid;
    }

    public Resume doGet(Object searchKey) {
        String key = (String) searchKey;
        return hashMap.get(key);
    }

    public boolean isExist(Object searchKey) {
        String key = (String) searchKey;

        return hashMap.containsKey(key);
    }
}
