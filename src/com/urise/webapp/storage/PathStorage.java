package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * gkislin
 * 22.07.2016
 */
public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    private Strategy concreteStrategyObjectStreamStorage;

    PathStorage(String dir, Strategy concreteStrategyObjectStreamStorage) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or it's not writable");
        }
        this.concreteStrategyObjectStreamStorage = concreteStrategyObjectStreamStorage;
    }
/*
    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;*/

    @Override
    public void clear() {
        getDirectoryList().forEach(this::doDelete);
    }

    @Override
    public int getSize() {
        return (int) getDirectoryList().count();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            concreteStrategyObjectStreamStorage.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write error!!! I am ", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return concreteStrategyObjectStreamStorage.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> listNew;
        listNew = getDirectoryList().map(this::doGet).collect(Collectors.toList());
        return listNew;
    }

    public Stream<Path> getDirectoryList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

}