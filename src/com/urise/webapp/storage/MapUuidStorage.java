package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> hashMap = new HashMap<>();

    @Override
    public void clear() {
        hashMap.clear();
    }

    protected void doUpdate(Resume resume, Object searchKey) {
        hashMap.put((String) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        hashMap.put((String) searchKey, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        hashMap.remove((String) searchKey);
    }

    @Override
    public List<Resume> doCopy() {
        return new ArrayList<>(hashMap.values());
    }

    @Override
    public int getSize() {
        return hashMap.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    protected Resume doGet(Object searchKey) {
        String key = (String) searchKey;
        return hashMap.get(key);
    }

    protected boolean isExist(Object searchKey) {
        String key = (String) searchKey;
        return hashMap.containsKey(key);
    }
}
