package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage,0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index!= -1){
            storage[index] = resume;
        } else {
            resumeAbsentMessage();
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index!= -1){
            System.out.println("Resume is already in base");
        } else if (size < storage.length) {
                storage[size] = resume;
                size++;
        } else {
                System.out.println("Stack is overflow!");
            }
        }


    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index!= -1){
            return storage[index];
        } else {
            resumeAbsentMessage();
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index!= -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            resumeAbsentMessage();
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int getSize() {
        return size;
    }

    private void resumeAbsentMessage() {
        System.out.println("Resume is absent in base");
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
