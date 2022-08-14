package vn.aptech.smartstudy.entity;

public class ResourceURL {
    private String url;
    private String title;
    private String subject;

    public ResourceURL() {
    }

    public ResourceURL(String url, String title, String subject) {
        this.setUrl(url);
        this.setTitle(title);
        this.setSubject(subject);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
