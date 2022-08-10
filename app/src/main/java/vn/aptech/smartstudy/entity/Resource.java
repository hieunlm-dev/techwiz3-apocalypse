package vn.aptech.smartstudy.entity;

public class Resource {
    private int id;
    private String url;
    private Subject subject;

    public Resource(int id, String url , Subject subject) {
        this.setId(id);
        this.setUrl(url);
        this.setSubject(subject);
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
}
