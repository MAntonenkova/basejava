package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

import static com.sun.xml.internal.ws.util.VersionUtil.compare;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void remove (int index) {
        if (size + 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size + 1 - index);
    }

    @Override
    protected void insert(Resume resume, int index) {
     //   int index = findIndex(resume);
        if (size - 1 - index >= 0) System.arraycopy(storage, index, storage, index + 1, size - 1 - index);
        storage[index] = resume;
    }

    @Override
    protected int findIndex(Resume resume) {
        int mid;
        int left = 0;
        int right = size;
        while (left < right) {
            mid = (left + right) / 2;
            int result = storage[mid].getUuid().compareTo(resume.getUuid());
            if (result > 0) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
