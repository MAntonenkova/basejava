package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
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

    private static final Comparator<Resume> GETALLSORTED_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            if (o1.getFullName().equals(o2.getFullName())) {
                return o1.getUuid().compareTo(o2.getUuid());
            } else return o1.getFullName().compareTo(o2.getFullName());
        }
    };

    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList(hashMap.entrySet());
        Collections.sort(list, GETALLSORTED_COMPARATOR);

        return list;
    }

    @Override
    public int getSize() {
        return hashMap.size();
    }

    @Override
    public Object getSearchKey(String fullName) {
        return fullName;
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
