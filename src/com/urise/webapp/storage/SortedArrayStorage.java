package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) System.arraycopy(storage, index + 1, storage, index, numMoved);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        int sortIndex = -(index) - 1;
        // @return ...  (-(<i>insertion point</i>) - 1)
        if (size - 1 - sortIndex >= 0)
            System.arraycopy(storage, sortIndex, storage, sortIndex + 1, size - 1 - sortIndex);
        storage[sortIndex] = resume;
    }
}
