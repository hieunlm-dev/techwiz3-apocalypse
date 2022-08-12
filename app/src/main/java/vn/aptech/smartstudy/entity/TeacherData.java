package vn.aptech.smartstudy.entity;

public class TeacherData {
    private int id;
    private String userName;
    private String subject;

    public TeacherData() {
    }

    public TeacherData(int id, String userName, String subject) {
        this.id = id;
        this.userName = userName;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
