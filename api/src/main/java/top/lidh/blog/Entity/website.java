package top.lidh.blog.Entity;

public class website {
    private String name;
    private String title;
    private String cover;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "website{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
