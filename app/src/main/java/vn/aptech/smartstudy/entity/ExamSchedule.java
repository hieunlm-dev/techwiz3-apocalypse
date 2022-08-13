package vn.aptech.smartstudy.entity;

public class ExamSchedule {
    private String date;
    private String exam_content;
    private String type_test;
    private String subject;
    private String hour;
    private String class_name;

    public ExamSchedule() {
    }

    public ExamSchedule(String date, String exam_content, String type_test, String subject,String hour , String class_name) {
        this.setDate(date);
        this.setExam_content(exam_content);
        this.setType_test(type_test);
        this.setSubject(subject);
        this.setHour(hour);
        this.setClass_name(class_name);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExam_content() {
        return exam_content;
    }

    public void setExam_content(String exam_content) {
        this.exam_content = exam_content;
    }

    public String getType_test() {
        return type_test;
    }

    public void setType_test(String type_test) {
        this.type_test = type_test;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
