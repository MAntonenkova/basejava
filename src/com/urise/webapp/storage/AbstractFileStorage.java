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
        directoryIsNotEmpty();
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume resume, File fileSearchKey) {
        directoryIsNotEmpty();
        try {
            doWrite(resume, fileSearchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", fileSearchKey.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File fileSearchKey) {
        try {
            fileSearchKey.createNewFile();
            doUpdate(resume, fileSearchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", fileSearchKey.getName(), e);
        }
    }

    @Override
    protected void doDelete(File fileSearchKey) {
        directoryIsNotEmpty();
        try {
            fileSearchKey.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Resume doGet(File fileSearchKey) {
        directoryIsNotEmpty();
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
    protected List<Resume> doCopy() {
        directoryIsNotEmpty();
        List<Resume> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        directoryIsNotEmpty();
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                doDelete(file);
            }
        }
    }

    @Override
    public int getSize() {
        directoryIsNotEmpty();
        return directory.listFiles().length;
    }

    public void directoryIsNotEmpty() {
        if (directory.listFiles() == null) {
            throw new StorageException("directory is empty", directory.getName());
        }
    }

    abstract void doWrite(Resume resume, File file) throws IOException;

    abstract Resume doRead(File file) throws IOException;
}
