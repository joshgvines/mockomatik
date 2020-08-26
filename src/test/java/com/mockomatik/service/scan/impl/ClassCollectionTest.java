package com.mockomatik.service.scan.impl;

import com.mockomatik.model.TestClassModel;
import com.mockomatik.service.create.TestClassModelBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ClassCollectionTest {

    private final String CLASS_NAME= "test_class_name";
    private final String IMPORT_STATEMENT = "test_import";
    private final String CONSTRUCTOR = "test_constructor";
    private final String VARIABLE = "test_variable";
    private final String MOCKED_VARIABLE = "test_m_variable";
    private final String METHOD = "test_method";

    private TestClassModel testClassModel;
    private ClassCollection classCollection;

    @Before
    public void setUp() {
        testClassModel = buildTestClassModel();
    }

    @After
    public void tearDown() {
        testClassModel = null;
        classCollection = null;
    }

    @Test
    public void testPushAndPop() {
        int index = 5;
        classCollection = given_ClassCollection(5);
        assertTrue(classCollection.isEmpty());
        for (int i = 0; i < index; i++) {
            classCollection.push(testClassModel);
        }
        assertFalse(classCollection.isEmpty());
        for (int i = index; i > 0; i--) {
            classCollection.pop();
        }
        assertTrue(classCollection.isEmpty());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testPushOnFull_Exception() {
        classCollection = given_emptyLockedClassCollection();
        classCollection.push(testClassModel);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testPopOnEmpty_Exception() {
        classCollection = given_emptyLockedClassCollection();
        classCollection.pop();
    }

    private ClassCollection given_ClassCollection(int size) {
        return new ClassCollection(size);
    }

    private ClassCollection given_emptyLockedClassCollection() {
        return new ClassCollection(0);
    }

    private TestClassModel buildTestClassModel() {
        return new TestClassModelBuilder()
            .className(CLASS_NAME)
            .importList(makeList(IMPORT_STATEMENT))
            .constructorList(makeList(CONSTRUCTOR))
            .variableList(makeList(VARIABLE))
            .mockedVariableList(makeList(MOCKED_VARIABLE))
            .methodList(makeList(METHOD))
            .build();
    }

    private List<String> makeList(String item) {
        return Arrays.asList(IMPORT_STATEMENT);
    }

}