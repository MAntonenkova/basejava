package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public interface StrategyReadWriteFile {

      void doWrite(Resume r, OutputStream os) throws IOException;

      Resume doRead(InputStream is) throws IOException;
}
