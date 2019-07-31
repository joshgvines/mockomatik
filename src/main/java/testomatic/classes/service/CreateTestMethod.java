package testomatic.classes.service;

import java.util.List;

public class CreateTestMethod {

    public void buildMethod(List<List<String>> primaryTestMethodList) {

        for(List<String> index : primaryTestMethodList) {
            System.out.println("__Get/Set_____________________________________________");
            for(String read : index) {
                System.out.println(read + "\n");
            }
        }



    }

}
