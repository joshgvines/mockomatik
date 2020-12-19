package com.mockomatik.enums;

public enum ClassComponent {

    REQUIRED_IMPORTS("import org.junit.After;\n"
            + "import org.junit.Before;\n"
            + "import org.junit.Test;\n"
            + "import org.junit.runner.RunWith;\n"
            + "import org.mockito.Mock;\n"
            + "import org.mockito.junit.MockitoJUnitRunner;\n\n"),

    MOCKITO_RUNNER("@RunWith(MockitoJUnitRunner.class)"),

    JUNIT_TEST_HEADER("\t@Test\n"),

    JUNIT_BEFORE_HEADER("\t@Before\n"
            + "\tpublic void setUp() {\n"),

    JUNIT_SINGLE_AFTER_HEADER("\t@After\n"
            + "\tpublic void tearDown() {\n"
            + "\t\tcut = null;\n"
            + "\t}\n"),

    JUNIT_MULTI_AFTER_HEADER("\t@After\n"
            + "\tpublic void tearDown() {");

    private final String classComponent;

    ClassComponent(String classComponent) {
        this.classComponent = classComponent;
    }

    public String getComponent() {
        return classComponent;
    }

}
