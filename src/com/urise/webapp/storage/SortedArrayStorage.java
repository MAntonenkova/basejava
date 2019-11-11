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
    protected void remove(int index) {
        if (size + 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size + 1 - index);
    }

    @Override
    protected void insert(Resume resume, int index) {
        int sortIndex = -(index) - 1;
        //   int index = findIndex(resume);
        if (size - 1 - sortIndex >= 0)
            System.arraycopy(storage, sortIndex, storage, sortIndex + 1, size - 1 - sortIndex);
        storage[sortIndex] = resume;
    }
}
