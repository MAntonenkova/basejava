package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    private List<Resume> arrayList = new ArrayList<>();

    @Override
    public void clear() {
        arrayList.clear();
    }


    public void updateResume(Resume resume, int index) {
        // int index = getIndex(resume.getUuid());
        arrayList.set(index, resume);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        arrayList.set(index, resume);
    }

    @Override
    public void deleteResume(String uuid, int index) {
        arrayList.remove(index);
    }

    public Resume[] getAll() {
        return arrayList.toArray(new Resume[getSize()]);
    }

    @Override
    public int getSize() {
        return arrayList.size();
    }

    /* @Override
     public boolean elementExist(Resume resume){
         return getIndex(resume.getUuid()) >=0;
     }

     @Override
     public boolean elementExist(String uuid){
         return getIndex(uuid)>=0;
     }
 */
    @Override
    public int getIndex(String uuid) {
        return arrayList.indexOf(get(uuid));
    }

    public Resume getElement(int index) {
        return arrayList.get(index);
    }
}
