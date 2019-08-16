package testomatic.a.classestotest;

public class DemoClassF_MockableObjects {

    private String testString = "test str";
    private Object obj;

    // Using common java objects which could be mocked with @Mock annotation
    DemoClassF_MockableObjects(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
