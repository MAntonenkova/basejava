package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() && !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writeable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume resume, File fileSearchKey) {
        try {
            doWrite(resume, fileSearchKey);
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File fileSearchKey) {
        try {
            fileSearchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + fileSearchKey.getAbsolutePath(), fileSearchKey.getName(), e);
        }
        doUpdate(resume, fileSearchKey);
    }

    @Override
    protected void doDelete(File fileSearchKey) {
        if (!fileSearchKey.delete()) {
            throw new StorageException("Delete error", fileSearchKey.getName());
        }
    }

    @Override
    protected Resume doGet(File fileSearchKey) {
        try {
            return doRead(fileSearchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", fileSearchKey.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doCopyAll() {
        directoryIsNotEmpty();
        List<Resume> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int getSize() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    public void directoryIsNotEmpty() {
        if (directory.listFiles() == null) {
            throw new StorageException("directory is empty", directory.getName());
        }
    }

    abstract void doWrite(Resume resume, File file) throws IOException;

    abstract Resume doRead(File file) throws IOException;
}
