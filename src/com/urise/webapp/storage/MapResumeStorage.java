package com.urise.webapp.storage;

import com.sun.org.apache.regexp.internal.RE;
import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<Resume, Resume> hashMap = new HashMap<>();

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
        hashMap.put((Resume) searchKey, resume);
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        hashMap.put((Resume) searchKey, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        Resume key = (Resume) searchKey;
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
    protected Resume getSearchKey(String uuid) {
        return hashMap.get(new Resume(uuid));
    }

    protected Resume doGet(Object searchKey) {
        Resume key = (Resume) searchKey;
        return hashMap.get(key);
    }

    protected boolean isExist(Object searchKey) {
        Resume key = (Resume) searchKey;
        return hashMap.containsKey(key);
       // return (hashMap.get(searchKey)) != null;
    }
}
