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

    public void updateResume(Resume resume, Object object) {
        hashMap.put((String)object, resume);
    }

    @Override
    public void saveResume(Resume resume, Object object) {
        hashMap.put((String)object, resume);
    }

    @Override
    public void deleteResume(Object object) {
        for (Map.Entry<String, Resume> entry: hashMap.entrySet()){
            if (entry.getKey().equals(object)){
                hashMap.remove(entry.getKey(), entry.getValue());
            }
        }
    }

    public Resume[] getAll() {
        return hashMap.values().toArray(new Resume[getSize()]);
    }

    @Override
    public int getSize() {
        return hashMap.size();
    }

    @Override
    public Object getIndex(String uuid) {
        return uuid;
    }

    public Resume getResume(Object object) {
        for (Map.Entry<String, Resume> entry: hashMap.entrySet()){
            if (entry.getKey().equals(object)){
                return entry.getValue();
            }
        } return null;
    }

    public  boolean isExist(Object object){
        for (Map.Entry<String, Resume> entry: hashMap.entrySet()){
            if (entry.getKey().equals(object)){
                return true;
            }
        }
        return false;
    }
}
