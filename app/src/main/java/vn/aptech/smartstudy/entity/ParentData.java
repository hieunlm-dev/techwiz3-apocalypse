package vn.aptech.smartstudy.entity;

public class ParentData {
    private String fullName;
    private String className;
    private String enrollmentDate;
    private String email;

    public ParentData() {
    }

    public ParentData(String fullName, String className, String enrollmentDate, String email) {
        this.fullName = fullName;
        this.className = className;
        this.enrollmentDate = enrollmentDate;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
