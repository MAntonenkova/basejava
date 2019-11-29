package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.security.KeyStore;
import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> hashMap = new HashMap<>();

    @Override
    public void clear() {
        hashMap.clear();
    }


    public void updateResume(Resume resume, int index) {
        hashMap.put(resume.getUuid(), resume);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        hashMap.put(resume.getUuid(), resume);
    }

    @Override
    public void deleteResume(int index) {

    }

    public Resume[] getAll() {
        return hashMap.values().toArray(new Resume[getSize()]);
    }

    @Override
    public int getSize() {
        return hashMap.size();
    }

    @Override
    public int getIndex(String uuid) {
        return uuid.hashCode();
    }

    public Resume getResume(int index) {
      return null;
    }

}
