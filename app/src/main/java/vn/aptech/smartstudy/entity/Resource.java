package vn.aptech.smartstudy.entity;

public class Resource {
    private int id;
    private String url;
    private String content;
    private String teacher_name;
    private Subject subject;

    public Resource(int id, String url , Subject subject,String content,String teacher_name) {
        this.setId(id);
        this.setUrl(url);
        this.setSubject(subject);
        this.setContent(content);
        this.setTeacher_name(teacher_name);
    }

    public Resource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", subject=" + subject +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }
}
