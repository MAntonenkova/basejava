package com.urise.webapp.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ConcreteStrategyObjectStreamStorage(STORAGE_DIR));
    }
}