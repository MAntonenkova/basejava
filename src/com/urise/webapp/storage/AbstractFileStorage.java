package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
            throw new StorageException("IO error", fileSearchKey.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File fileSearchKey) {
        try {
            fileSearchKey.createNewFile();
            doWrite(resume, fileSearchKey);
        } catch (IOException e) {
            throw new StorageException("IO error", fileSearchKey.getName(), e);
        }
    }

    @Override
    protected void doDelete(File fileSearchKey) {
        fileSearchKey.delete();
    }

    @Override
    protected Resume doGet(File fileSearchKey) {
        return doRead(fileSearchKey);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doCopy() {
        List<Resume> list = new ArrayList<>();
        directory.listFiles();
        for (File file : directory.listFiles()) {
            list.add(doRead(file));
        }
        return list;
    }

    @Override
    public void clear() {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    @Override
    public int getSize() {
        return directory.listFiles().length;
    }

    abstract void doWrite(Resume resume, File file) throws IOException;

    abstract Resume doRead(File file);
}
