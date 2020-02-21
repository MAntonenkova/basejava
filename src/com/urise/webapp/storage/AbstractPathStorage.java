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

/**
 * gkislin
 * 22.07.2016
 */
public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    private Strategy concreteStrategyObjectStreamStorage;

    AbstractPathStorage(String dir, Strategy concreteStrategyObjectStreamStorage) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or it's not writable");
        }
        this.concreteStrategyObjectStreamStorage = concreteStrategyObjectStreamStorage;
    }

   /* public void performWrite(Strategy concreteStrategyObjectStreamStorage, Resume r, OutputStream os) throws IOException {
        concreteStrategyObjectStreamStorage = new ObjectStreamStrategy();
        concreteStrategyObjectStreamStorage.doWrite(r, os);
    }

    public void performRead(Strategy concreteStrategyObjectStreamStorage, InputStream is) throws IOException {
        concreteStrategyObjectStreamStorage = new ObjectStreamStrategy();
        concreteStrategyObjectStreamStorage.doRead(is);
    }*/

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;


    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }


    @Override
    public int getSize() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            concreteStrategyObjectStreamStorage.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
            // doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
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
            //  return doRead(new BufferedInputStream(Files.newInputStream(path)));
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
        List listNew;
        try {
            listNew = Files.list(directory).collect(Collectors.toList());
            return listNew;
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }
}