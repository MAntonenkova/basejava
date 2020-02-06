package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> hashMap = new HashMap<>();

    @Override
    public void clear() {
        hashMap.clear();
    }

    protected void doUpdate(Resume r, Resume resume) {
        hashMap.put(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        hashMap.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Resume resume) {
        hashMap.remove((resume).getUuid());
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
    public List<Resume> doCopyAll() {
        return new ArrayList<>(hashMap.values());
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }
}
