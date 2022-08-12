package vn.aptech.smartstudy.entity;

public class StudentData {
    private int id;
    private String fullName;
    private String className;
    private String enrollmentDate;
    private String email;

    public StudentData() {
    }

    public StudentData( String fullName, String className, String enrollmentDate,String email) {
        this.fullName = fullName;
        this.className = className;
        this.enrollmentDate = enrollmentDate;
        this.setEmail(email);
    }


    public String getUserName() {
        return fullName;
    }

    public void setUserName(String userName) {
        this.fullName = userName;
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
