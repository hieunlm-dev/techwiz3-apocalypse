package vn.aptech.smartstudy.entity;

public class StudentData {
    private int id;
    private String userName;
    private String className;
    private String enrollmentDate;

    public StudentData() {
    }

    public StudentData(int id, String userName, String className, String enrollmentDate) {
        this.id = id;
        this.userName = userName;
        this.className = className;
        this.enrollmentDate = enrollmentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
