package vn.aptech.smartstudy.entity;

public class User {

    private int id;
    private String full_name;
    private String phone_number;
    private String email;
    private String address;
    private String password;
    private String role;
    private Boolean isEnable;

    public StudentData getStudentData() {
        return studentData;
    }

    public void setStudentData(StudentData studentData) {
        this.studentData = studentData;
    }

    private StudentData studentData;
    private ParentData parentData;
    private TeacherData teacherData;

    public User() {
    }

    public User(int id, String full_name, String phone_number, String email, String address, String password, String role, Boolean isEnable, StudentData studentData) {
        this.id = id;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.password = password;
        this.role = role;
        this.isEnable = isEnable;
        this.studentData = studentData;
    }

    public User(int id, String full_name, String phone_number, String email, String address, String password, String role, Boolean isEnable, TeacherData teacherData) {
        this.id = id;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.password = password;
        this.role = role;
        this.isEnable = isEnable;
        this.teacherData = teacherData;
    }

    public User(int id, String full_name, String phone_number, String email, String address, String password, String role, Boolean isEnable, ParentData parentData) {
        this.id = id;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.email = email;
        this.address = address;
        this.password = password;
        this.role = role;
        this.isEnable = isEnable;
        this.parentData = parentData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }
}
