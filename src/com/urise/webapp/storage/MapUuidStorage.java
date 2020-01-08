package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> hashMap = new HashMap<>();

    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            if (!(o1.getFullName().equals(o2.getFullName()))){
                return o1.getFullName().compareTo(o2.getFullName());
            }
            else return o1.getUuid().compareTo(o2.getUuid());
        }
    };

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
        String key = (String) searchKey;
        hashMap.remove(key);
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(hashMap.values());
        list.sort(RESUME_COMPARATOR);
        return list;
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
