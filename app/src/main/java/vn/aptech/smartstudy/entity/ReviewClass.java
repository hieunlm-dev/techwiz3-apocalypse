package vn.aptech.smartstudy.entity;

public class ReviewClass {
    private int id;
    private String content;
    private String datetime;
    private ClassName className;

    public ReviewClass() {
    }

    public ReviewClass(int id, String content, String datetime, ClassName className) {
        this.id = id;
        this.content = content;
        this.datetime = datetime;
        this.setClassName(className);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public ClassName getClassName() {
        return className;
    }

    public void setClassName(ClassName className) {
        this.className = className;
    }
}
