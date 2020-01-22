package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> hashMap = new HashMap<>();

    @Override
    public void clear() {
        hashMap.clear();
    }

    protected void doUpdate(Resume resume, String uuid) {
        hashMap.put( uuid, resume);
    }

    @Override
    protected void doSave(Resume resume, String uuid) {
        hashMap.put(uuid, resume);
    }

    @Override
    protected void doDelete(String uuid) {
        hashMap.remove( uuid);
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
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume doGet(String uuid) {
        return hashMap.get(uuid);
    }

    @Override
    protected boolean isExist(String uuid) {
        return hashMap.containsKey(uuid);
    }
}
