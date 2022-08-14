package vn.aptech.smartstudy.entity;

public class ScoreDetail {

    private String type_test;
    private String subject_name;
    private String student_email;
    private float mark;
    private int semester;
    private int year;

    public ScoreDetail( String type_test, String subject_name, String student_email,float mark,int semester , int year) {

        this.setType_test(type_test);
        this.setSubject_name(subject_name);
        this.setStudent_email(student_email);
        this.setMark(mark);
        this.setSemester(semester);
        this.setYear(year);
    }

    public ScoreDetail() {
    }



    public String getType_test() {
        return type_test;
    }

    public void setType_test(String type_test) {
        this.type_test = type_test;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
