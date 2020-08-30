package com.mockomatik.service.scan.impl;

import com.mockomatik.model.TestClassModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;

public class ClassCollection implements Closeable {

    private static final Logger log = LogManager.getLogger(ClassCollection.class);

    private final static int MAX_SIZE = 1000;
    private int capacity;
    private int top = 0;

    private TestClassModel[] classCollection;

    public ClassCollection(int capacity) {
        if (capacity <= MAX_SIZE) {
            this.capacity = capacity;
            classCollection = new TestClassModel[capacity];
        } else {
            log.error("Class stack collection has failed to initialize.");
            throw new IllegalStateException(
                    "Class stack collection has failed to initialize.");
        }
    }

    public boolean push(TestClassModel entry) {
        isFull();
        classCollection[top++] = entry;
        return true;
    }

    public TestClassModel pop() {
        if (!this.isEmpty()) {
            TestClassModel temp = classCollection[--top];
            classCollection[top] = null;
            return temp;
        }
        log.error("Class stack collection has failed to pop.");
        throw new ArrayIndexOutOfBoundsException(
                "Out of bounds, collection is empty, cannot pop.");
    }

    public int size() {
        return top;
    }

    public boolean isEmpty() {
        if (top <= 0) {
            return true;
        }
        return false;
    }

    private void isFull() {
        if (top >= capacity) {
            log.error("Class stack collection was full.");
            throw new ArrayIndexOutOfBoundsException(
                    "Out of bounds, collection is full, cannot push.");
        }
    }

    @Override
    public void close() throws IOException {
        classCollection = null;
        System.gc();
    }
}