package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> hashMap = new HashMap<>();

    @Override
    public void clear() {
        hashMap.clear();
    }

    protected void doUpdate(Resume resume, Object searchKey) {
        hashMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        hashMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        hashMap.remove(((Resume) searchKey).getUuid());
    }

    @Override
    public int getSize() {
        return hashMap.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return hashMap.get(uuid);
    }

    @Override
    public List<Resume> doCopy() {
        return new ArrayList<>(hashMap.values());
    }

    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
