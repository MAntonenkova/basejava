package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;

import java.io.*;

public interface StreamSerializerStrategy {

    void doWrite(Resume r, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
