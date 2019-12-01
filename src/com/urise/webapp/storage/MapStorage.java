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
        String key = (String)object;
        hashMap.remove(key);

        /*for (Map.Entry<String, Resume> entry: hashMap.entrySet()){
            if (entry.getKey().equals(object)){
                hashMap.remove(entry.getKey(), entry.getValue());
            }
        }*/
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
        String key = (String)object;
       /* for (Map.Entry<String, Resume> entry: hashMap.entrySet()){
            if (entry.getKey().equals(object)){
                return entry.getValue();
            }
        }*/
       return hashMap.get(key);
    }

    public  boolean isExist(Object object){
        String key = (String)object;

        return hashMap.containsKey(key);
      /*  for (Map.Entry<String, Resume> entry: hashMap.entrySet()){
            if (entry.getKey().equals(object)){
                return true;
            }
        }*/
       
    }
}
