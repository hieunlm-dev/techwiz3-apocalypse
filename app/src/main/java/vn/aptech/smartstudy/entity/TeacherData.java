package vn.aptech.smartstudy.entity;

public class TeacherData {
    private String userName;
    private String subject;

    public TeacherData() {
    }

    public TeacherData(String userName, String subject) {
        this.userName = userName;
        this.subject = subject;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
