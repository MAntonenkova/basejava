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
        directoryIsNotEmpty(directory);
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume resume, File fileSearchKey) {
        directoryIsNotEmpty(directory);
        try {
            doWrite(resume, fileSearchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", fileSearchKey.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File fileSearchKey) {
        directoryIsNotEmpty(directory);
        try {
            fileSearchKey.createNewFile();
            doUpdate(resume, fileSearchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", fileSearchKey.getName(), e);
        }
    }

    @Override
    protected void doDelete(File fileSearchKey) {
        directoryIsNotEmpty(directory);
        try {
            fileSearchKey.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Resume doGet(File fileSearchKey) {
        directoryIsNotEmpty(directory);
        try {
            return doRead(fileSearchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", fileSearchKey.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        directoryIsNotEmpty(directory);
        return file.exists();
    }

    @Override
    protected List<Resume> doCopy() {
        directoryIsNotEmpty(directory);
        List<Resume> list = new ArrayList<>();
        directory.listFiles();
        for (File file : directory.listFiles()) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        directoryIsNotEmpty(directory);
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile()) {
                doDelete(file);
            }
        }
    }

    @Override
    public int getSize() {
        directoryIsNotEmpty(directory);
        return directory.listFiles().length;
    }

    public void directoryIsNotEmpty(File directory) {
        if (directory.listFiles() == null) {
            throw new StorageException("directory is empty", directory.getName());
        }
    }

    abstract void doWrite(Resume resume, File file) throws IOException;

    abstract Resume doRead(File file) throws IOException;
}
