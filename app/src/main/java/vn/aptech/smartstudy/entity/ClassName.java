package vn.aptech.smartstudy.entity;

import java.util.List;
import java.util.Map;

public class ClassName {
    private int id;
    private String name;

    //private Map<String , String> test_types;

    public ClassName(int id, String name ) {
        this.id = id;
        this.name = name;
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


}
