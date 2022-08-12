package vn.aptech.smartstudy.entity;

import java.util.List;
import java.util.Map;

public class ClassName {
    private int id;
    private String name;

    private Map<String , String> test_types;

    public ClassName(int id, String name , Map<String , String> test_types) {
        this.id = id;
        this.name = name;
        this.setTest_types(test_types);
    }

    public ClassName() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String , String> getTest_types() {
        return test_types;
    }

    public void setTest_types(Map<String , String> test_types) {
        this.test_types = test_types;
    }
}
