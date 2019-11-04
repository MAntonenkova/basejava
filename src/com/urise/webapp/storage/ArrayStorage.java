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
        Arrays.fill(getAll(), null);
        size = 0;
    }

    public void update(Resume resume) {
        int i = findElementInStorage(resume.getUuid());
        if (elementIsInStorage(i)){
            storage[i] = resume;
        }
        else {
            resumeAbsentMessage();
        }
    }

    public void save(Resume resume) {
        int i = findElementInStorage(resume.getUuid());
        if (elementIsInStorage(i)){
            System.out.println("Resume is already in base");
        }
        else {
            if (getSize() < storage.length) {
                storage[getSize()] = resume;
                size++;
            } else {
                System.out.println("Stack is overflow!");
            }
        }
    }

    public Resume get(String uuid) {
        int i = findElementInStorage(uuid);
        if (elementIsInStorage(i)){
            return storage[i];
        }
        else {
            resumeAbsentMessage();
        }
        return null;
    }

    public void delete(String uuid) {
        // check if resume present
        int i = findElementInStorage(uuid);
        if (elementIsInStorage(i)) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
        else {
            resumeAbsentMessage();
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int getSize() {
        return size;
    }

    public void resumeAbsentMessage() {
        System.out.println("Resume is absent in base");
    }

    public int findElementInStorage(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public boolean elementIsInStorage(int i){
        return i!= -1;
    }
}
