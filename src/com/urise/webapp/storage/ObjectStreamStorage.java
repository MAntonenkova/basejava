package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {

    protected ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
            objectOutputStream.writeObject(outputStream);
        }
    }

    @Override
    protected Resume doRead (InputStream inputStream) throws IOException {
        return null;
    }
}
